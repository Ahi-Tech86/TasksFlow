package testEndpoints;

import com.ahicode.AuthServiceRunner;
import com.ahicode.dto.*;
import com.ahicode.factories.UserEntityFactory;
import com.ahicode.storage.entities.UserEntity;
import com.ahicode.storage.repositories.UserRepository;
import com.redis.testcontainers.RedisContainer;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuthServiceRunner.class)
public class AuthServiceApplicationTest {

    @Autowired
    private UserEntityFactory factory;
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String, TemporaryUserDto> redisTemplate;

    @LocalServerPort
    private Integer port;
    @ServiceConnection
    static RedisContainer redisContainer = new RedisContainer("redis:latest");
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:15");

    @BeforeAll
    static void init() {
        String envFilePath = Paths.get("..", ".env").toAbsolutePath().toString();
        Dotenv dotenv = Dotenv.configure().directory(envFilePath).load();
        dotenv.entries().forEach(
                entry -> System.setProperty(entry.getKey(), entry.getValue())
        );
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String testEmail = "example@mail.com";

        TemporaryUserDto userDto = TemporaryUserDto.builder()
                .email(testEmail)
                .nickname("nickname")
                .firstname("Firstname")
                .lastname("Lastname")
                .password("1")
                .confirmationCode("123456")
                .build();

        redisTemplate.opsForValue().set(testEmail, userDto);
    }

    static {
        redisContainer.start();
        postgreSQLContainer.start();
    }

    @Test
    void shouldSaveTemporaryUserData() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("example@mail.com")
                .nickname("nickname")
                .firstname("Firstname")
                .lastname("Lastname")
                .password("1")
                .build();

        String responseBody = RestAssured.given()
                .contentType("application/json")
                .body(signUpRequest)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .log().all()
                .statusCode(200)
                .extract().asString();

        assertThat(responseBody, Matchers.equalTo("An activation code has been sent to your email, please send the activation code before it expires. " +
                "The activation code expires in 20 minutes."));
    }

    @Test
    void shouldConfirmRegister() {
        ConfirmationRegisterRequest confirmationRegisterRequest = ConfirmationRegisterRequest.builder()
                .email("example@mail.com")
                .confirmationCode("123456")
                .build();

        UserDto user = RestAssured.given()
                .contentType("application/json")
                .body(confirmationRegisterRequest)
                .when()
                .post("/api/v1/auth/confirmRegister")
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(UserDto.class);

        assertNotNull(user);
        assertEquals(user.getEmail(), "example@mail.com");
        assertEquals(user.getNickname(), "nickname");
        assertEquals(user.getFirstname(), "Firstname");
        assertEquals(user.getLastname(), "Lastname");
    }

    @Test
    void shouldLoginSuccessful() {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("example@mail.com")
                .password("1")
                .build();

        Response response = given()
                .contentType("application/json")
                .body(signInRequest)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        UserDto user = response.as(UserDto.class);

        assertNotNull(user);
        assertEquals(user.getEmail(), "example@mail.com");
        assertEquals(user.getNickname(), "nickname");
        assertEquals(user.getFirstname(), "Firstname");
        assertEquals(user.getLastname(), "Lastname");

        String accessToken = response.getCookie("accessToken");
        String refreshToken = response.getCookie("refreshToken");

        assertNotNull(accessToken, "Access token should not be null");
        assertNotNull(refreshToken, "Refresh token should not be null");
        assertFalse(accessToken.isEmpty(), "Access token should not be empty");
        assertFalse(refreshToken.isEmpty(), "Refresh token should not be empty");
    }
}
