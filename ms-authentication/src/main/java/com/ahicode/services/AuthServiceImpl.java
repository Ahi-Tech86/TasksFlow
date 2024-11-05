package com.ahicode.services;

import com.ahicode.dto.*;
import com.ahicode.exceptions.AppException;
import com.ahicode.factories.TemporaryUserDtoFactory;
import com.ahicode.factories.UserDtoFactory;
import com.ahicode.factories.UserEntityFactory;
import com.ahicode.storage.entities.UserEntity;
import com.ahicode.storage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDtoFactory dtoFactory;
    private final UserRepository repository;
    private final EmailServiceImpl emailService;
    private final UserEntityFactory entityFactory;
    private final PasswordEncoder passwordEncoder;
    private final TemporaryUserDtoFactory temporaryUserDtoFactory;

    private final RedisTemplate<String, TemporaryUserDto> redisTemplate;

    @Override
    public String register(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        String nickname = signUpRequest.getNickname();

        if (!isEmailValid(email)) {
            log.error("Attempt register with invalid email {}", email);
            throw new AppException("Email is invalid", HttpStatus.BAD_REQUEST);
        }

        isEmailUniqueness(email);
        isNicknameUniqueness(nickname);

        String confirmationCode = generateCode();

        TemporaryUserDto temporaryUserDto = temporaryUserDtoFactory.makeTemporaryUserDto(signUpRequest, confirmationCode);
        redisTemplate.opsForValue().set(email, temporaryUserDto, 20, TimeUnit.MINUTES);
        log.info("User information with email {} is temporarily saved", email);

        try {
            emailService.sendConfirmationCode(email, confirmationCode);
            log.info("Message with activation code was send to email {}", email);
        } catch (RuntimeException exception) {
            log.error("Attempt to send message was unsuccessful", exception);
            throw new AppException("There was an error sending the message", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "An activation code has been sent to your email, please send the activation code before it expires. " +
                "The activation code expires in 20 minutes.";
    }

    @Override
    @Transactional
    public UserDto confirm(ConfirmationRegisterRequest confirmationRegisterRequest) {
        String email = confirmationRegisterRequest.getEmail();
        String confirmationCode = confirmationRegisterRequest.getConfirmationCode();

        TemporaryUserDto temporaryUserDto = redisTemplate.opsForValue().get(email);

        if (!confirmationCode.equals(temporaryUserDto.getConfirmationCode())) {
            log.error("Attempting to activate an account with an incorrect confirmation code to email {}", email);
            throw new AppException("The confirmation code doesn't match what the server generated", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = entityFactory.makeUserEntity(temporaryUserDto);
        user.setPassword(passwordEncoder.encode(temporaryUserDto.getPassword()));

        UserEntity savedUser = repository.saveAndFlush(user);
        log.info("User with email {} was successfully saved", email);

        return dtoFactory.makeUserDto(savedUser);
    }

    @Override
    public List<Object> login(SignInRequest signInRequest) {
        return List.of();
    }

    public String generateCode() {
        Random random = new Random();

        int number = 1 + random.nextInt(1000000);

        return String.format("%6d", number);
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null) {
            return false;
        }

        return pattern.matcher(email).matches();
    }

    private void isEmailUniqueness(String email) {
        Optional<UserEntity> optionalUser = repository.findByEmail(email);

        if (optionalUser.isPresent()) {
            log.error("Attempt to register with an existing email {}", email);
            throw new AppException(String.format("User with email %s is already exists", email), HttpStatus.BAD_REQUEST);
        }
    }

    private void isNicknameUniqueness(String nickname) {
        Optional<UserEntity> optionalUser = repository.findByNickname(nickname);

        if (optionalUser.isPresent()) {
            log.error("Attempt to with an existing nickname {}", nickname);
            throw new AppException(String.format("User with nickname %s is already exists", nickname), HttpStatus.BAD_REQUEST);
        }
    }
}
