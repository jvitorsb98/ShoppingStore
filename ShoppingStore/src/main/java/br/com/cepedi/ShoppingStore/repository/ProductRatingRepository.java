package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRatingRepository extends JpaRepository<ProductRating,Long> {
	
    Page<ProductRating> findAll(Pageable pageable);

    @Query("""
            SELECT p.ratingStars, p.review, p.user, p.product FROM ProductRating p
                WHERE p.id = :id
            """)
    Boolean findActivatedById(Long id);

    @Query(value = "SELECT * FROM product_rating WHERE user_id = ?1", nativeQuery = true)
    Page<ProductRating> findAllByUserId(Long userId, Pageable pageable);

    @Query("""
            SELECT p FROM ProductRating p
            WHERE p.product.id = :productId
            """)
    Page<ProductRating> findAllByProductId(Long productId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
            UPDATE ProductRating p SET p.disabled = true
            WHERE p.id = :id
            """)
    void disableById(Long id);
	
}
