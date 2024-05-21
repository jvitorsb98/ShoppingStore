package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataProductRatingRegister;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
@Component
public class ValidationProductRatingExist implements ValidationProductRatingRegister {
	
	private ProductRatingRepository productRatingRepository;
	

	
	@Override
	public void validation(DataProductRatingRegister data) {
		if (data.id() != null && !productRatingRepository.existsById(data.id())) {
            throw new ValidationException("The provided productRating id does not exist");
        }
		
	}



	@Override
	public void validation(DataRegisterProductRating data) {
		if (data.id() != null && !productRatingRepository.existsById(data.id())) {
            throw new ValidationException("The provided productRating id does not exist");
        }
		
	}

}
