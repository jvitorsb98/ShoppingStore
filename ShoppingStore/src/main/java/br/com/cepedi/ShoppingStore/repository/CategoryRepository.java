package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Método para buscar categorias por nome, com paginação
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name%")
    Page<Category> findByNameContaining(@Param("name") String name, Pageable pageable);

    // Método para buscar categorias por descrição, com paginação
    @Query("SELECT c FROM Category c WHERE c.description LIKE %:description%")
    Page<Category> findByDescriptionContaining(@Param("description") String description, Pageable pageable);

    // Método para buscar categorias que estão desativadas
    @Cacheable
    Page<Category> findAllByDisabledTrue(Pageable pageable);
}
