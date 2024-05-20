package br.com.cepedi.ShoppingStore.repository;


import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface PossibleFacetsRepository extends JpaRepository<PossibleFacets, Long> {

    @Query("SELECT p FROM PossibleFacets p WHERE p.category = :category")
    Page<PossibleFacets> findByCategory(@Param("category") Category category, Pageable pageable);



    Page<PossibleFacets> findByNameContaining(String name, Pageable pageable);

    
    Page<PossibleFacets> findByDescriptionContaining(String description, Pageable pageable);
    
    
}

