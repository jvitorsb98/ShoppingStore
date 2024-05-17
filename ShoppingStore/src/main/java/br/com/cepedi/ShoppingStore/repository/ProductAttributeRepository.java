package br.com.cepedi.ShoppingStore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}

