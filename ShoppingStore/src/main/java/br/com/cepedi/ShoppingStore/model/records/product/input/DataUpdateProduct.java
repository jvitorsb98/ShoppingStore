package br.com.cepedi.ShoppingStore.model.records.product.input;



import java.math.BigDecimal;
import java.math.BigInteger;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DataUpdateProduct(

    Long id,

    @NotBlank(message = "Name cannot be null or empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name,

    @NotBlank(message = "Description cannot be null or empty")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description,

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    BigDecimal price,

    @NotBlank(message = "SKU cannot be null or empty")
    @Size(max = 50, message = "SKU cannot exceed 50 characters")
    String sku,

    @NotBlank(message = "Image URL cannot be null or empty")
    String imageUrl,

    @NotNull(message = "Category ID cannot be null")
    Long categoryId,

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be a positive value")
    BigInteger quantity,

    @NotBlank(message = "Manufacturer cannot be null or empty")
    @Size(max = 100, message = "Manufacturer cannot exceed 100 characters")
    String manufacturer,

    boolean featured

    // List<ProductAttribute> productAttributeList,
    // List<ProductRating> productRating,

) {
}
