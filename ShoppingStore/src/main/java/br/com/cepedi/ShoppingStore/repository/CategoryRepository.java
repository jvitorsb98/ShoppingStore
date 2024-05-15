package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
