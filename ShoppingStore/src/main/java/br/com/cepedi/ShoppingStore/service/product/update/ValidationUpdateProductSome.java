package br.com.cepedi.ShoppingStore.service.product.update;

import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidationUpdateProductSome implements ValidationUpdateProduct{
	
	private  ProductRepository repositoryProduct;
	
	@Override
	public void validation(Long id, DataUpdateProduct data) {
		 if (data.id() != null && !repositoryProduct.existsById(data.id())) {
             throw new ValidationException("The provided product id does not exist");
         }
		
	}
	
}
