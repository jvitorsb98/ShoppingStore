package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.service.AuthService;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Activate User", description = "Activate User messages")
public class ActivatedUserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/activate-account")
    @Transactional
    @Operation(summary = "Activate user account", method = "GET", description = "Activates the user account using the provided activation token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account activated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid token",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<String> activateAccount(
            @Parameter(description = "Activation token received by email", required = true)
            @RequestParam("token") String token
    ) {
        try {
            if (!tokenService.isValidToken(token)) {
                return ResponseEntity.badRequest().body("Token invalid");
            }
            authService.activateUser(token);
            tokenService.revokeToken(token);
            return ResponseEntity.ok("User account activated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to activate user account.");
        }
    }
}