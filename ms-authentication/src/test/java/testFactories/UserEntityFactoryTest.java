package testFactories;

import com.ahicode.dto.TemporaryUserDto;
import com.ahicode.factories.UserEntityFactory;
import com.ahicode.storage.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static com.ahicode.enums.AppRole.USER;
import static org.junit.jupiter.api.Assertions.*;

public class UserEntityFactoryTest {
    @InjectMocks
    private UserEntityFactory factory;

    private TemporaryUserDto userDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        userDto = TemporaryUserDto.builder()
                .email("example@mail.com")
                .nickname("nickname")
                .firstname("Firstname")
                .lastname("Lastname")
                .password("1")
                .confirmationCode("123456")
                .build();
    }

    @Test
    public void testMakeUserEntity() {
        UserEntity user = factory.makeUserEntity(userDto);

        assertNotNull(user);
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getNickname(), userDto.getNickname());
        assertEquals(user.getFirstname(), userDto.getFirstname());
        assertEquals(user.getLastname(), userDto.getLastname());
        assertEquals(user.getRole(), USER);
    }
}
