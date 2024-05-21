package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("EmailService Tests")
@TestMethodOrder(MethodOrderer.Random.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Mock
    private Configuration configuration;

    @Mock
    private Template template;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    @DisplayName("Test sendResetPasswordEmail with valid parameters")
    void sendResetPasswordEmail_ValidParameters_EmailSent() throws Exception {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String token = "testToken";

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        when(freeMarkerConfigurer.getConfiguration()).thenReturn(configuration);
        when(configuration.getTemplate(anyString())).thenReturn(template);

        // Act
        emailService.sendResetPasswordEmail(name, email, token);

        // Assert
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("Test sendResetPasswordEmail with template processing exception")
    void sendResetPasswordEmail_TemplateProcessingException_ExceptionThrown() throws Exception {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String token = "testToken";

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        when(freeMarkerConfigurer.getConfiguration()).thenReturn(configuration);
        when(configuration.getTemplate(anyString())).thenReturn(template);
        doThrow(new TemplateException("Failed to process email template", null)).when(template).process(any(), any());

        // Act & Assert
        assertThrows(MessagingException.class, () -> {
            emailService.sendResetPasswordEmail(name, email, token);
        });
    }

    @Test
    @DisplayName("Test sendActivationEmail with valid parameters")
    void sendActivationEmail_ValidParameters_EmailSent() throws Exception {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String token = "testToken";

        User user = new User();
        user.setEmail(email);

        when(userRepository.findUserByEmail(email)).thenReturn(user);

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        when(tokenService.generateTokenForActivatedEmail(user)).thenReturn(token);
        when(freeMarkerConfigurer.getConfiguration()).thenReturn(configuration);
        when(configuration.getTemplate(anyString())).thenReturn(template);

        // Act
        emailService.sendActivationEmail(name, email);

        // Assert
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

}