package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.model.records.details.DataDetailsRegisterUser;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.service.AuthService;
import br.com.cepedi.ShoppingStore.security.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



public class RegisterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    @WithMockUser
    void testRegisterUser_SuccessfulRegistration() throws Exception {
        // Mocking the expected behavior of AuthService and EmailService
        DataRegisterUser testData = new DataRegisterUser("Jon", "john.doe@example.com", "Jhon Doe" , "Jhon1234*");
        DataDetailsRegisterUser expectedData = new DataDetailsRegisterUser("Jon", "Jhon Doe", "john.doe@example.com");
        when(authService.register(testData)).thenReturn(expectedData);
        when(emailService.sendActivationEmail("John Doe", "john.doe@example.com")).thenReturn("testToken");

        // Calling the method directly
        ResponseEntity<String> response = registerController.registerUser(testData);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully. Activation email sent.", response.getBody());

        // Verifying the interactions with AuthService and EmailService
        verify(authService, times(1)).register(testData);
        verify(emailService, times(1)).sendActivationEmail("Jhon Doe", "john.doe@example.com");
    }





}
