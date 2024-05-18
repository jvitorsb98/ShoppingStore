package br.com.cepedi.ShoppingStore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cepedi.ShoppingStore.model.entitys.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
