package testServices;

import com.ahicode.services.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailServiceTest {
    @InjectMocks
    private EmailServiceImpl service;

    @Mock
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSendConfirmationCodeToEmail() {
        String email = "test@mail.com";
        String confirmationCode = "123456";

        service.sendConfirmationCode(email, confirmationCode);

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom(fromEmail);
        expectedMessage.setTo(email);
        expectedMessage.setSubject("Account confirmation code");
        expectedMessage.setText(
                String.format(
                        "Here is your confirmation code for your registration on our website. Confirmation code: %s",
                        confirmationCode
                )
        );

        verify(mailSender, times(1)).send(expectedMessage);
    }
}
