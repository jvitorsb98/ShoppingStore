package br.com.cepedi.ShoppingStore.service.product.update;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;

public interface ValidationUpdateProduct {
	  void validation(Long id, DataUpdateProduct data);
}
