package br.com.cepedi.ShoppingStore.service.brand.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationBrandExistsForDisabled implements BrandValidatorDisabled{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void validation(Long id) {
        if(!brandRepository.existsById(id)){
            throw new ValidationException("The required branch does not exists");
        }
    }
}
