package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;

public interface ValidationUpdateProductRating {
	void validation(Long id, DataUpdateProductRating data);
}
