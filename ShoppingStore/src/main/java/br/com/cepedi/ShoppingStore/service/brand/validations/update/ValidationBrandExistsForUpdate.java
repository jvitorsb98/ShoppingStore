package br.com.cepedi.ShoppingStore.service.brand.validations.update;


import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationBrandExistsForUpdate implements BrandValidationUpdate{

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public void validation(DataUpdateBrand data) {
        if(!brandRepository.existsById(data.id())){
            throw new ValidationException("The required branch does not exists");
        }
    }
}
