package br.com.cepedi.ShoppingStore.security.controller;


import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRequestResetPassword;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataResetPassword;
import br.com.cepedi.ShoppingStore.security.service.EmailService;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import br.com.cepedi.ShoppingStore.security.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
public class PasswordRecoveryController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @PutMapping("/reset-password/request")
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