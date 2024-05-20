package br.com.cepedi.ShoppingStore.repository;


import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    @Cacheable
    Page<ProductAttribute> findAll(Pageable pageable);

    List<ProductAttribute> findAllByProductId(Long id);

}

