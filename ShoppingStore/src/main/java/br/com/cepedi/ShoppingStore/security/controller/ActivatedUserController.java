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
                return ResponseEntity.badRequest().body("Token inválido");
            }
            authService.activateUser(token);
            tokenService.revokeToken(token);  // Invalida o token após ativação
            return ResponseEntity.ok("Conta de usuário ativada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao ativar a conta do usuário.");
        }
    }
}