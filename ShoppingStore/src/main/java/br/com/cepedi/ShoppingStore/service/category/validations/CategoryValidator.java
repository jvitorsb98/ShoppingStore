package br.com.cepedi.ShoppingStore.service.category.validations;

import br.com.cepedi.ShoppingStore.model.entitys.Category;

public interface CategoryValidator {
	void validateUpdate(Category category);
    void validateDisable(Category category);
}
