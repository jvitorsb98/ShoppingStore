package br.com.cepedi.ShoppingStore.model.records.possibleFacets.details;


import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DataPossibleFacetsDetails(

        Long id,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Category ID cannot be null")
        Long idCategory

) {

    public  DataPossibleFacetsDetails(PossibleFacets possibleFacets){
        this(possibleFacets.getId(),possibleFacets.getName() ,possibleFacets.getCategory().getId());
    }
    
    
    

}
