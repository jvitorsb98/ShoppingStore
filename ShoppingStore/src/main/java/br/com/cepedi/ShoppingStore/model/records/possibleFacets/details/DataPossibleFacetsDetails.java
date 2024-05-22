package br.com.cepedi.ShoppingStore.model.records.possibleFacets.details;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import lombok.Data;


public record DataPossibleFacetsDetails(

        Long id,

        String name,

        Long idCategory

) {

    public  DataPossibleFacetsDetails(PossibleFacets possibleFacets){
        this(possibleFacets.getId(),possibleFacets.getName() ,possibleFacets.getCategory().getId());
    }

    public Long getId() {
        return id;
    }

}
