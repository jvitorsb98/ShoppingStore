package br.com.cepedi.ShoppingStore.model.records.productAttribute.details;


import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;

public record DataProductAttributeDetails(
    Long id,
    String name,
    String value,
    Long productId
) {

    public static DataProductAttributeDetails fromEntity(ProductAttribute productAttribute) {
        return new DataProductAttributeDetails(
                productAttribute.getId(),
                productAttribute.getName(),
                productAttribute.getValue(),
                productAttribute.getProduct().getId()
        );
    }

}

