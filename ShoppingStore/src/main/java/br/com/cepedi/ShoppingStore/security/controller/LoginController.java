package br.com.cepedi.ShoppingStore.security.controller;


import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.details.DadosTokenJWT;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataAuth;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DataAuth data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        Authentication authentication = manager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        if (!user.getActivated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User is not activated");
        }

        var tokenJWT = tokenService.generateToken(user);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
