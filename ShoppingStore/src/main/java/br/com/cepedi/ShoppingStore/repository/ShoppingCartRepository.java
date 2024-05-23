package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
     ShoppingCart findByUserId(Long userId);

     @Cacheable
     Page<ShoppingCart> findAllByDisabledTrue(Pageable pageable);

     @Query("""
            SELECT s FROM ShoppingCart s WHERE s.user.id = :userId
            """)
     Page<ShoppingCart> findAllByUser(Pageable pageable, Long userId);

     @Cacheable
     @Query("""
            SELECT s FROM ShoppingCart s WHERE s.user.id = :userId AND s.disabled = true
            """)
     Page<ShoppingCart> findAllByUserAndDisabledIsTrue(Pageable pageable, Long userId);

     @Query("""
          SELECT sci FROM ShoppingCartItem sci WHERE sci.shoppingCart.id = :shoppingCartId
     """)
     List<ShoppingCartItem> findAllByShoppingCartId(Long shoppingCartId);
}
