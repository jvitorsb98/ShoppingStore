package br.com.cepedi.ShoppingStore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findAll(Pageable pageable);
    
    
    @Query("""
            SELECT p.name from Product p
                WHERE p.id = :id
            """)
    Boolean findActivatedById(Long id);
 
    
    
    @Query("""
            SELECT p.name FROM Product p
            WHERE p.category.id = :id
            """)
    Boolean findProductNamesByCategoryId(Long id);
}
