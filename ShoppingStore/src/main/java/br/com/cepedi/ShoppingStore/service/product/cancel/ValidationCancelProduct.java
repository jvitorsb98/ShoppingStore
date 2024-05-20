package br.com.cepedi.ShoppingStore.service.product.cancel;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataCancelProduct;

public interface ValidationCancelProduct {
	void validation(Long id, DataCancelProduct data);

	void validation(Long id);
}
