package testFactories;

import com.ahicode.dto.UserDto;
import com.ahicode.factories.UserDtoFactory;
import com.ahicode.storage.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static com.ahicode.enums.AppRole.USER;
import static org.junit.jupiter.api.Assertions.*;

public class UserDtoFactoryTest {
    @InjectMocks
    private UserDtoFactory factory;

    private UserEntity user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = UserEntity.builder()
                .id(1L)
                .email("example@mail.com")
                .nickname("nickname")
                .firstname("Firstname")
                .lastname("Lastname")
                .password("1")
                .role(USER)
                .createAt(Instant.now())
                .build();
    }

    @Test
    public void testMakeUserDto() {
        UserDto userDto = factory.makeUserDto(user);

        assertNotNull(userDto);
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getNickname(), user.getNickname());
        assertEquals(userDto.getFirstname(), user.getFirstname());
        assertEquals(userDto.getLastname(), user.getLastname());
    }
}
