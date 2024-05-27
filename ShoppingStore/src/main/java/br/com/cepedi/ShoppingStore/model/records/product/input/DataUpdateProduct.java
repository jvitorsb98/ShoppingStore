package br.com.cepedi.ShoppingStore.model.records.product.input;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DataUpdateProduct(

        @NotNull(message = "{notnull.product.id}")
        Long id,

        @Size(max = 100, message = "{size.product.name}")
        String name,

        @Size(max = 500, message = "{size.product.description}")
        String description,

        @Positive(message = "{positive.product.price}")
        BigDecimal price,

        @Size(max = 50, message = "{size.product.sku}")
        String sku,

        String imageUrl,

        Long categoryId,

        @Positive(message = "{positive.product.quantity}")
        BigInteger quantity,

        Long brandId,

        boolean featured

) {
}
