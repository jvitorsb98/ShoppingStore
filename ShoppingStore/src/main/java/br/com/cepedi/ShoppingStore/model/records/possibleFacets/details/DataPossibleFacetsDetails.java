package br.com.cepedi.ShoppingStore.model.records.possibleFacets.details;


import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;


public record DataPossibleFacetsDetails(

        Long id,

        String name,

        Long idCategory,

        Boolean disabled
) {

    public  DataPossibleFacetsDetails(PossibleFacets possibleFacets){
        this(possibleFacets.getId(),possibleFacets.getName() ,possibleFacets.getCategory().getId(), possibleFacets.getDisabled());
    }
    
    
    

}
