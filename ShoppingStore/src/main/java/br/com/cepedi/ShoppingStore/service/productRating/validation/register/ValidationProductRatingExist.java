package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataProductRatingRegister;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;

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
