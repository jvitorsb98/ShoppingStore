package br.com.cepedi.ShoppingStore.model.records.productAttribute.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdateProductAttribute(
        @NotNull(message = "{notnull.productAttribute.id}")
        Long id,

        String name,
        String value
) {}
