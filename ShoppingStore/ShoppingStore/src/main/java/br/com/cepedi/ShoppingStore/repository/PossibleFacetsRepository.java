package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.PossibleFacets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibleFacetsRepository extends JpaRepository<PossibleFacets, Long> {
 
}
