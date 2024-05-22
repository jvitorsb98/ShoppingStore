package br.com.cepedi.ShoppingStore.service.productRating.validation.disabled;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataDisabledProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationDisabledDataProductRatingExist implements ValidationProductRatingDisabled {

	@Autowired
	private  ProductRepository repositoryProduct;

    @Override
	public void validation(Long id ) {
		 if(!repositoryProduct.existsById(id)){
	            throw new ValidationException("The required product is does not exists");
	        }		
	}
}
