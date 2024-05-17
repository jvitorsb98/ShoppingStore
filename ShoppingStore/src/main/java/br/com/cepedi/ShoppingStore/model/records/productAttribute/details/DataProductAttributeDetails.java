package br.com.cepedi.ShoppingStore.model.records.productAttribute.details;


public record DataProductAttributeDetails(
    Long id,
    String name,
    String value,
    Long productId
) {}

