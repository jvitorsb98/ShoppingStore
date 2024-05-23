package br.com.cepedi.ShoppingStore.model.records.category.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdateCategory(

        @NotNull(message = "{NotNull.dataUpdateCategory.id}")
        Long id,
        String name,
        String description

) {
}
