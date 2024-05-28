package br.com.cepedi.ShoppingStore.security.controller;


import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRequestResetPassword;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataResetPassword;
import br.com.cepedi.ShoppingStore.security.service.EmailService;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import br.com.cepedi.ShoppingStore.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
@Tag(name = "Password Recovery User", description = "Password Recovery User messages")
public class PasswordRecoveryController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @PutMapping("/reset-password/request")
    @Transactional
    @Operation(summary = "Request password reset",
            method = "PUT",
            description = "Sends a password reset email to the user with instructions on how to reset their password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Email not found",
                    content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "500", description = "Failed to send email",
                    content = {@Content(mediaType = "text/plain")})
    })
    public ResponseEntity<String> resetPasswordRequest(@RequestBody @Validated DataRequestResetPassword dataResetPassword) {
        User user = userService.getUserActivatedByEmail(dataResetPassword.email());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail not found");
        }

        String token = tokenService.generateTokenRecoverPassword(user);

        try {
            emailService.sendResetPasswordEmail(user.getName(), dataResetPassword.email(), token);
            String responseMessage = "A password reset email has been sent to " + dataResetPassword.email();
            return ResponseEntity.ok(responseMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    @PutMapping("/reset-password/reset")
    @Transactional
    @Operation(summary = "Reset password",
            method = "PUT",
            description = "Resets the user's password based on the token provided in the reset password email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token",
                    content = {@Content(mediaType = "text/plain")})
    })
    public ResponseEntity<String> resetPassword(@RequestBody @Validated DataResetPassword dataResetPassword) {
        if (tokenService.isValidToken(dataResetPassword.token())) {
            String email = tokenService.getEmailByToken(dataResetPassword.token());
            userService.updatePassword(email, dataResetPassword.password());
            tokenService.revokeToken(dataResetPassword.token());  // Invalida o token após redefinição da senha
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }
}