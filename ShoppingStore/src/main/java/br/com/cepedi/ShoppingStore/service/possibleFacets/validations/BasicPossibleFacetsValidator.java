package br.com.cepedi.ShoppingStore.service.possibleFacets.validations;



import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import org.springframework.stereotype.Component;

@Component
public class BasicPossibleFacetsValidator implements PossibleFacetsValidator {

	@Override
	public void validateRegister(PossibleFacets possibleFacet) {
		if (possibleFacet.getName() == null || possibleFacet.getName().isBlank()) {
			throw new IllegalArgumentException("Name cannot be blank");
		}
	}
	
	@Override
    public void validateUpdate(PossibleFacets possibleFacet) {
        if (possibleFacet.getName() == null || possibleFacet.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (possibleFacet.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
    }
	
	@Override
	public void validateDisable(PossibleFacets possibleFacets) {
		if (possibleFacets.getCategory() == null) {
			throw new RuntimeException("Category cannot be null");
		}
	}
	
}


