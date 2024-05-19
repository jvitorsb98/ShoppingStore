package br.com.cepedi.ShoppingStore.model.records.category.details;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import lombok.Data;


public record DataCategoryDetails (

        Long id,

        String name,

        String description

) {

    public  DataCategoryDetails(Category category){
        this(category.getId(),category.getName(),category.getDescription());
    }
   
}
