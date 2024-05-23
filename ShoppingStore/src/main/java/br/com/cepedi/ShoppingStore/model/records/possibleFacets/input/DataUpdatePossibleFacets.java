package br.com.cepedi.ShoppingStore.model.records.possibleFacets.input;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdatePossibleFacets(

        @NotNull(message = "ID cannot be null")
        Long id,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Category ID cannot be null")
        Long categoryId

) {
}
