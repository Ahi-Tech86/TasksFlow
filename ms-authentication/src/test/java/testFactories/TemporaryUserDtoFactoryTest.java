package testFactories;

import com.ahicode.dto.SignUpRequest;
import com.ahicode.dto.TemporaryUserDto;
import com.ahicode.factories.TemporaryUserDtoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class TemporaryUserDtoFactoryTest {
    @InjectMocks
    private TemporaryUserDtoFactory factory;

    private String confirmationCode;
    private SignUpRequest signUpRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        confirmationCode = "123456";

        signUpRequest = SignUpRequest.builder()
                .email("example@mail.com")
                .nickname("nickname")
                .firstname("Firstname")
                .lastname("Lastname")
                .password("1")
                .build();
    }

    @Test
    public void testMakeTemporaryUserDto() {
        TemporaryUserDto userDto = factory.makeTemporaryUserDto(signUpRequest, confirmationCode);

        assertNotNull(userDto);
        assertEquals(userDto.getEmail(), signUpRequest.getEmail());
        assertEquals(userDto.getNickname(), signUpRequest.getNickname());
        assertEquals(userDto.getFirstname(), signUpRequest.getFirstname());
        assertEquals(userDto.getLastname(), signUpRequest.getLastname());
        assertEquals(userDto.getPassword(), signUpRequest.getPassword());
    }
}
