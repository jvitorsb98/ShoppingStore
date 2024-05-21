package br.com.cepedi.ShoppingStore.security.repository;

import br.com.cepedi.ShoppingStore.security.model.entitys.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
}
