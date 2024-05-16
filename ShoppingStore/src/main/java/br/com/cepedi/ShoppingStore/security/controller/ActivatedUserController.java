package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.service.AuthService;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class ActivatedUserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PutMapping("/activate-account")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        try {
            if (!tokenService.isValidToken(token)) {
                return ResponseEntity.badRequest().body("Token invalid");
            }
            authService.activateUser(token);
            return ResponseEntity.ok("User account activated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to activate user account.");
        }
    }
}