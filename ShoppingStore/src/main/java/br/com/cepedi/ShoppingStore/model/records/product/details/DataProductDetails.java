package br.com.cepedi.ShoppingStore.model.records.product.details;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    Long categoryId,
    BigInteger quantity,
    Long brandId,
    boolean featured,
    Boolean disabled
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
            product.getBrand().getId(),
            product.getFeatured(), //Aqui usamos isFeatured()em vez de get() para boolean no Lombok
            product.getDisabled()
       );
    }
}
