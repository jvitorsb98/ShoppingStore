package br.com.cepedi.ShoppingStore.service.productRating.validation.disable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidationDisabledProductExist implements ValidationDisabledProductRating {

	@Autowired
	private  ProductRepository repositoryProduct;
	
	@Override
	public void validation(Long id ) {
		 if(!repositoryProduct.existsById(id)){
	            throw new ValidationException("The required product is does not exists");
	        }		
	}
}
