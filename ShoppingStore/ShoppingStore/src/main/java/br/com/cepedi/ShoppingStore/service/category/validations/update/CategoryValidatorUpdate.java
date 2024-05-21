package br.com.cepedi.ShoppingStore.service.category.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;

public interface CategoryValidatorUpdate {
	void validation(DataUpdateCategory dataUpdateCategory);
}
