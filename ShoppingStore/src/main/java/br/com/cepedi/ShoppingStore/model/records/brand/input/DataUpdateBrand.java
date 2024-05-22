package br.com.cepedi.ShoppingStore.model.records.brand.input;

import jakarta.validation.constraints.NotNull;

public record DataUpdateBrand(

        @NotNull
        Long id,

        String name

) {
}
