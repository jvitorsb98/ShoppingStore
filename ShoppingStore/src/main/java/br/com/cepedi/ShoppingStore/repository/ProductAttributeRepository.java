package br.com.cepedi.ShoppingStore.repository;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    @Cacheable
    Page<ProductAttribute> findAllByDisabledFalse(Pageable pageable);

    @Cacheable
    Page<ProductAttribute> findAllByDisabledTrue(Pageable pageable);

    List<ProductAttribute> findAllByProductIdAndDisabledIsFalse(Long id);

    List<ProductAttribute> findAllByProductIdAndDisabledIsTrue(Long id);

    @Query("""
            SELECT p_a.disabled from ProductAttribute p_a
                WHERE p_a.id = :id
            """)
    Boolean findDisabledById(Long id);

}

