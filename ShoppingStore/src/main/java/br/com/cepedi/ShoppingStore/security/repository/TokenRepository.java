package br.com.cepedi.ShoppingStore.security.repository;

import br.com.cepedi.ShoppingStore.security.model.entitys.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
}
