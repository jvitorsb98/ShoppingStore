package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRatingRepository extends JpaRepository<ProductRating,Long> {
}
