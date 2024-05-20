package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataProductRatingRegister;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;

public interface ValidationProductRatingRegister {

	//void validation(DataProductRatingRegister data);
	void validation(DataRegisterProductRating data);

	void validation(DataProductRatingRegister data);
}
