package br.com.cepedi.ShoppingStore.security.controller;



import br.com.cepedi.ShoppingStore.security.model.records.details.DataDetailsRegisterUser;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.service.AuthService;
import br.com.cepedi.ShoppingStore.security.service.EmailService;
import com.auth0.jwt.JWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Register User", description = "Register User messages")
public class RegisterController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    @Transactional
    @Operation(summary = "Register a new user", method = "POST", description = "Registers a new user and sends an activation email.")
    @ApiResponse(responseCode = "200", description = "User registered successfully. Activation email sent.",
            content = @Content(mediaType = "string"))
    @ApiResponse(responseCode = "400", description = "Invalid input data.",
            content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error.",
            content = @Content)
    public ResponseEntity<String> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User data to be registered", required = true)
            @RequestBody @Valid DataRegisterUser data) throws MessagingException {
        DataDetailsRegisterUser dataDetailsRegisterUser = authService.register(data);
        String tokenForActivate = emailService.sendActivationEmail(data.name(), data.email());
        return ResponseEntity.ok("User registered successfully. Activation email sent.");
    }
}