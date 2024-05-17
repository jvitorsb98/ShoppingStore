package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
}
