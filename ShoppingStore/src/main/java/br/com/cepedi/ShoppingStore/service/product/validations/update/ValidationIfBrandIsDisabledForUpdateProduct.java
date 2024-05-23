package br.com.cepedi.ShoppingStore.service.product.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfBrandIsDisabledForUpdateProduct implements ValidationUpdateProduct{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void validation(DataUpdateProduct data) {
        if (data.brandId() != null && brandRepository.existsById(data.brandId())) {
            Brand brand = brandRepository.getReferenceById(data.brandId());
            if(brand.getDisabled()){
                throw new ValidationException("The provided brand is disabled");
            }
        }
    }
}
