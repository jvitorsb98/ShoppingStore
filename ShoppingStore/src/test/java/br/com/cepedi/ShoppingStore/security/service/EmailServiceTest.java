package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.mail.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("EmailService Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Mock
    private MailService mailService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        when(freeMarkerConfigurer.getConfiguration()).thenReturn(configuration);
        when(configuration.getTemplate(anyString())).thenReturn(template);
    }

    @Test
    @DisplayName("Test sendResetPasswordEmail with valid parameters")
    @Order(1)
    void sendResetPasswordEmail_ValidParameters_EmailSent() throws Exception {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String token = "testToken";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailService.sendResetPasswordEmail(name, email, token);

        // Assert
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("Test sendResetPasswordEmail with template processing exception")
    @Order(2)
    void sendResetPasswordEmail_TemplateProcessingException_ExceptionThrown() throws Exception {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String token = "testToken";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new TemplateException("Failed to process email template", null)).when(template).process(any(), any());

        // Act & Assert
        assertThrows(MessagingException.class, () -> emailService.sendResetPasswordEmail(name, email, token));
    }


}
