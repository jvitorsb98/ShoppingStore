package br.com.cepedi.ShoppingStore.model.records.brand.input;

import jakarta.validation.constraints.NotNull;

public record DataUpdateBrand(

        @NotNull(message = "{notnull.brand.id}")
        Long id,

        String name

) {
}
