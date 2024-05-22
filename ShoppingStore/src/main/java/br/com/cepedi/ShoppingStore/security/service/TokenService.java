package br.com.cepedi.ShoppingStore.security.service;


import br.com.cepedi.ShoppingStore.security.model.entitys.Token;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterToken;
import br.com.cepedi.ShoppingStore.security.repository.TokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "API Voll.med";

    public void revokeToken(String token) {
        Optional<Token> tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity.isPresent()) {
            Token tokenToUpdate = tokenEntity.get();
            tokenToUpdate.disabled();
            tokenRepository.save(tokenToUpdate);
        }
    }

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);

            registerToken(token, user);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    private void registerToken(String tokenValue, User user) {
        Instant expiresAt = JWT.decode(tokenValue).getExpiresAtAsInstant();
        DataRegisterToken dataRegisterToken = new DataRegisterToken(tokenValue, user.getId(), expiresAt);
        Token tokenEntity = new Token(dataRegisterToken, user);
        tokenRepository.save(tokenEntity);
    }

    public String generateTokenRecoverPassword(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(expirationDateRecoverPassword())
                    .sign(algorithm);
            registerToken(token, user);  // Register the token in the database
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    public boolean isValidToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            Optional<Token> tokenEntity = tokenRepository.findByToken(token);
            return tokenEntity.isPresent() && tokenEntity.get().getDisabled() != null && !tokenEntity.get().getDisabled();
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant expirationDateRecoverPassword() {
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getEmailByToken(String token) {
        return JWT.decode(token).getClaim("email").asString();
    }

    public String getSubject(String tokenJWT) throws JWTVerificationException {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token JWT inválido ou expirado!");
        }
    }

    public String generateTokenForActivatedEmail(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .sign(algorithm);
            registerToken(token, user);  // Register the token in the database
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }
}