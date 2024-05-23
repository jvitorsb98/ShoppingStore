package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.Payment;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Payment p WHERE p.shoppingCart.id = :shoppingCartId AND p.disabled = false")
    boolean existsEnabledPaymentByShoppingCartId(@Param("shoppingCartId") Long shoppingCartId);

    @Query("SELECT p FROM Payment p")
    Page<Payment> findAllPayments(Pageable pageable);

    @Cacheable
    @Query("SELECT p FROM Payment p WHERE p.disabled = true")
    Page<Payment> findAllPaymentsAndDisabledTrue(Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.shoppingCart.user.id = :userId")
    Page<Payment> findAllPaymentsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Cacheable
    @Query("SELECT p FROM Payment p WHERE p.shoppingCart.user.id = :userId AND p.disabled = true")
    Page<Payment> findAllPaymentsByUserIdAndDisabledTrue(@Param("userId") Long userId, Pageable pageable);
}
