package br.com.cepedi.ShoppingStore.model.records.product.input;



import java.math.BigDecimal;
import java.math.BigInteger;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DataUpdateProduct(

    @NotNull
    Long id,

    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name,

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description,

    @Positive(message = "Price must be a positive value")
    BigDecimal price,

    @Size(max = 50, message = "SKU cannot exceed 50 characters")
    String sku,

    String imageUrl,

    Long categoryId,

    @Positive(message = "Quantity must be a positive value")
    BigInteger quantity,

    @Size(max = 100, message = "Manufacturer cannot exceed 100 characters")
    String manufacturer,

    boolean featured

) {
}
