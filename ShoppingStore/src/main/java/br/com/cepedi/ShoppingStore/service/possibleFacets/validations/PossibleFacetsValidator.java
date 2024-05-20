package br.com.cepedi.ShoppingStore.service.possibleFacets.validations;

import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;

public interface PossibleFacetsValidator {
	void validateRegister(PossibleFacets possibleFacet);
	void validateDisable(PossibleFacets possibleFacet);
	void validateUpdate(PossibleFacets possibleFacet);
}
