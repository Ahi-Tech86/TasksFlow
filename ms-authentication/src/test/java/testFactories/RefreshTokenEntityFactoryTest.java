package testFactories;

import com.ahicode.factories.RefreshTokenEntityFactory;
import com.ahicode.storage.entities.RefreshTokenEntity;
import com.ahicode.storage.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;

import static com.ahicode.enums.AppRole.USER;
import static org.junit.jupiter.api.Assertions.*;

public class RefreshTokenEntityFactoryTest {
    @InjectMocks
    private RefreshTokenEntityFactory factory;

    private String token;
    private UserEntity user;
    private Date expirationTime;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        token = "justjwttoken";

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

        expirationTime = new Date(System.currentTimeMillis() + 35000000L);
    }

    @Test
    public void testMakeRefreshTokenEntity() {
        RefreshTokenEntity refreshToken = factory.makeRefreshTokenEntity(user, token, expirationTime);

        assertNotNull(refreshToken);
        assertEquals(refreshToken.getUser(), user);
        assertEquals(refreshToken.getToken(), token);
        assertEquals(refreshToken.getExpiresAt(), expirationTime);
    }
}
