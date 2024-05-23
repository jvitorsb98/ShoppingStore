package br.com.cepedi.ShoppingStore.service.product.validations.register;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfBrandExistsForRegisterProduct implements ValidationRegisterProduct{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void validation(DataRegisterProduct data) {
        if (!brandRepository.existsById(data.brandId())) {
            throw new ValidationException("The provided brand id does not exist");
        }
    }
}
