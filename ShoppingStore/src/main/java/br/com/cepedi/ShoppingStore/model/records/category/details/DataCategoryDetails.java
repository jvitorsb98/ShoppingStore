package br.com.cepedi.ShoppingStore.model.records.category.details;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public record DataCategoryDetails (

        Long id,

        @NotBlank(message = "{NotBlank.dataCategoryDetails.name}")
        String name,
        
        @NotBlank(message = "{NotBlank.dataCategoryDetails.description}")
        String description

) {

    public  DataCategoryDetails(Category category){
        this(category.getId(),category.getName(),category.getDescription());
    }

	

}
   