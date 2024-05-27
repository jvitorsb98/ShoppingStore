package br.com.cepedi.ShoppingStore.model.records.brand.input;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterBrand(

        @NotBlank(message = "{notblank.brand.name}")
        String name

) {


}
