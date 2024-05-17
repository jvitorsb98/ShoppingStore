package br.com.cepedi.ShoppingStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
