package br.com.cepedi.ShoppingStore.service.product.cancel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataCancelProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidationCancelProductExistence  implements ValidationCancelProduct{

	@Autowired
	private  ProductRepository repositoryProduct;
	
	@Override
	public void validation(Long id ) {
		 if(!repositoryProduct.existsById(id)){
	            throw new ValidationException("The required product is does not exists");
	        }		
	}

	@Override
	public void validation(Long id, DataCancelProduct data) {
		 if(!repositoryProduct.existsById(id)){
	            throw new ValidationException("The required product is does not exists");
	        }	
		
	}

}
