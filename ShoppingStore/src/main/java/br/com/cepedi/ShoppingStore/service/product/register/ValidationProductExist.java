package br.com.cepedi.ShoppingStore.service.product.register;

import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidationProductExist implements ValidationProduct {
	
	private  ProductRepository repositoryProduct;
	
	public ValidationProductExist(ProductRepository repositoryProduct) {
		this.repositoryProduct = repositoryProduct;
	}
    @Override
    public void validation(DataRegisterProduct data) {
    	 if (data.id() != null && !repositoryProduct.existsById(data.id())) {
             throw new ValidationException("The provided product id does not exist");
         }
		   
	   }
}
