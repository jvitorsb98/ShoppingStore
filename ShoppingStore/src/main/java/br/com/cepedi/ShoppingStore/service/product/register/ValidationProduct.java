package br.com.cepedi.ShoppingStore.service.product.register;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;


public interface ValidationProduct {
	 void validation(DataRegisterProduct data);
	
	
}
