package br.com.cepedi.ShoppingStore.service.possibleFacets.validations;



import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import org.springframework.stereotype.Component;

@Component
public class BasicPossibleFacetsValidator implements PossibleFacetsValidator {

    @Override
    public void validate(PossibleFacets possibleFacet) {
        if (possibleFacet.getName() == null || possibleFacet.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}
