package br.com.cepedi.ShoppingStore.service.brand.validations.disabled;


import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationBrandAlreadyDisabledForDisabled implements BrandValidatorDisabled{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void validation(Long id) {
        if(brandRepository.existsById(id)){
            Brand brand = brandRepository.getReferenceById(id);
            if(brand.getDisabled()){
                throw new ValidationException("The brand is already disabled");
            }
        }
    }
}
