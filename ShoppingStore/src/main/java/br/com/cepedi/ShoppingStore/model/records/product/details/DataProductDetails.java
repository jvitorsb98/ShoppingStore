package br.com.cepedi.ShoppingStore.model.records.product.details;

import java.math.BigDecimal;
import java.util.List;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;

public record DataProductDetails(
    Long id,
    String name,
    String description,
    BigDecimal price,
    String sku,
    String imageUrl,
    Category category,
    Long categoryId,
    Integer quantity,
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
            product.getCategory(),
            product.getCategory().getId(),
            product.getQuantity(),
            product.getManufacturer(),
            product.isFeatured() //Aqui usamos isFeatured()em vez de get() para boolean no Lombok 
//          product.getProductAttributeList(),
//          product.getProductRating()   
       );
    }
}
