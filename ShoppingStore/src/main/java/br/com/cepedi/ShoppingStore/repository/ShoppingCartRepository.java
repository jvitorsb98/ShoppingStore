package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
     ShoppingCart findByUserId(Long userId);


     @Query("""
            SELECT s FROM ShoppingCart s WHERE s.user.id = :userId
            """)
     Page<ShoppingCart> findAllByUser(Pageable pageable, Long userId);
}
