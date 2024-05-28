package br.com.cepedi.ShoppingStore.model.records.brand.details;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;

public record DataBrandDetails(

        Long id,

        String name,

        Boolean disabled
) {

    public DataBrandDetails(Brand brand){
        this(brand.getId(),brand.getName(), brand.getDisabled());
    }

}
