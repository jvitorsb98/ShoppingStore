package br.com.cepedi.ShoppingStore.model.records.productAttribute.input;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterProductAttribute(
    @NotBlank(message = "Name cannot be blank") String name,
    @NotBlank(message = "Value cannot be blank") String value,
    Long productId
) {}
