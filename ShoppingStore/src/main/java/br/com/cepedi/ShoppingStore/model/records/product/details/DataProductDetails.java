package br.com.cepedi.ShoppingStore.model.records.product.details;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataProductDetails(
    Long id,
    @NotBlank(message = "Name cannot be blank")
    String name,
    
    String description,
    
    @NotNull(message = "Price cannot be null")
    BigDecimal price,
    
    @NotBlank(message = "SKU cannot be blank")
    String sku,
    
    String imageUrl,
    
    @NotNull(message = "Category ID cannot be null")
    Long categoryId,
    
    BigInteger quantity,
    
    String manufacturer,
    boolean featured
 // List<ProductAttribute> productAttributeList,
 // List<ProductRating> productRating
) {
    public DataProductDetails(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getSku(),
            product.getImageUrl(),
            product.getCategory().getId(),
            product.getQuantity(),
            product.getManufacturer(),
            product.getFeatured()//Aqui usamos isFeatured()em vez de get() para boolean no Lombok
       );
    }
}
