package br.com.cepedi.ShoppingStore.model.records.product.input;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DataRegisterProduct(

        @NotBlank(message = "{notblank.product.name}")
        @Size(max = 100, message = "{size.product.name}")
        String name,

        @NotBlank(message = "{notblank.product.description}")
        @Size(max = 500, message = "{size.product.description}")
        String description,

        @NotNull(message = "{notnull.product.price}")
        @Positive(message = "{positive.product.price}")
        BigDecimal price,

        @NotBlank(message = "{notblank.product.sku}")
        @Size(max = 50, message = "{size.product.sku}")
        String sku,

        @NotBlank(message = "{notblank.product.imageUrl}")
        String imageUrl,

        @NotNull(message = "{notnull.product.categoryId}")
        Long categoryId,

        @NotNull(message = "{notnull.product.quantity}")
        @Positive(message = "{positive.product.quantity}")
        BigInteger quantity,

        @NotNull(message = "{notnull.product.brandId}")
        Long brandId,

        Boolean featured

) {
}
