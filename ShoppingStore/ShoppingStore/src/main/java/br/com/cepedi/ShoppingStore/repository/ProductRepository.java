package br.com.cepedi.ShoppingStore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findAll(Pageable pageable);
    
    
    @Query("""
            SELECT p.name from Product p
                WHERE p.id = :id
            """)
    Boolean findActivatedById(Long id);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
 
    
    
    @Query("""
            SELECT p.name FROM Product p
            WHERE p.category.id = :id
            """)
    Boolean findProductNamesByCategoryId(Long id);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteById(Long id);
}
