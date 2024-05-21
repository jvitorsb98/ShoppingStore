package br.com.cepedi.ShoppingStore.service.product.validations.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidationUpdateProductSome implements ValidationUpdateProduct{

	@Autowired
	private  ProductRepository repositoryProduct;
	
	@Override
	public void validation(DataUpdateProduct data) {
		 if (!repositoryProduct.existsById(data.id())) {
             throw new ValidationException("The provided product id does not exist");
         }
		
	}
	
}
