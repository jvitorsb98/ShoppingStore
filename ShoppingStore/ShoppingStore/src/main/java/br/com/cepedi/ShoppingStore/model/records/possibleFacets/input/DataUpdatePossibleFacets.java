package br.com.cepedi.ShoppingStore.model.records.possibleFacets.input;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdatePossibleFacets(

        @NotNull
        Long id,

        String name,

        Long categoryId

) {
}
