package br.com.cepedi.ShoppingStore.service.brand.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationBrandIsDisabledForUpdate implements BrandValidationUpdate{

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public void validation(DataUpdateBrand data) {
        if(brandRepository.existsById(data.id())){
            Brand brand = brandRepository.getReferenceById(data.id());
            if(brand.getDisabled()){
                throw new ValidationException("The required brand is disabled");
            }
        }
    }
}
