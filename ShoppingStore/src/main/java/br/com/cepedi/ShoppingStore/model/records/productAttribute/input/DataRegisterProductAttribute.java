package br.com.cepedi.ShoppingStore.model.records.productAttribute.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegisterProductAttribute(
        @NotBlank(message = "{notblank.productAttribute.name}")
        String name,
        @NotBlank(message = "{notblank.productAttribute.value}")
        String value,

        @NotNull(message = "{notnull.productAttribute.productId}")
        Long productId
) {}
