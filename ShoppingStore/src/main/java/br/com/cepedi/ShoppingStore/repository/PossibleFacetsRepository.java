package br.com.cepedi.ShoppingStore.repository;




import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PossibleFacetsRepository extends JpaRepository<PossibleFacets, Long> {

    @Query("SELECT p FROM PossibleFacets p WHERE p.category = :category")
    Page<PossibleFacets> findByCategory(@Param("category") Category category, Pageable pageable);

    @Query("SELECT p FROM PossibleFacets p WHERE p.category = :category")
    List<PossibleFacets> findByCategoryCustomQuery(@Param("category") Category category);

    @Query("SELECT p FROM PossibleFacets p WHERE p.name LIKE %:name%")
    Page<PossibleFacets> findByNameContainingCustomQuery(@Param("name") String name, Pageable pageable);
    
    @Modifying
    @Query("UPDATE PossibleFacets p SET p.name = :name, p.category = :category WHERE p.id = :id")
    void update(@Param("id") Long id, @Param("name") String name, @Param("category") Category category);
    
    
}

