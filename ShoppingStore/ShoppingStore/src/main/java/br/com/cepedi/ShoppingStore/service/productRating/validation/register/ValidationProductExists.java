package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationProductExists implements ValidationProductRatingRegister {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void validation(DataRegisterProductRating data) {
		if (data.productId() != null && !productRepository.existsById(data.productId())) {
            throw new ValidationException("The provided product id does not exist");
        }
		
	}

}
