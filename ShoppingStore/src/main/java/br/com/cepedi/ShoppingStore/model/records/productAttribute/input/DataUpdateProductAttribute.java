package br.com.cepedi.ShoppingStore.model.records.productAttribute.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdateProductAttribute(
        @NotNull(message = "Id must not be null") Long id,
        @NotBlank(message = "Name must have be valid") String name,
        @NotBlank(message = "Value must haver be valid") String value
) {}
