package br.com.cepedi.ShoppingStore.service.category.validations;

import org.springframework.stereotype.Component;
import br.com.cepedi.ShoppingStore.model.entitys.Category;

@Component
public class BasicCategoryValidator implements CategoryValidator {

    @Override
    public void validateUpdate(Category category) {
        // Adicione suas regras de validação para atualização aqui
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    @Override
    public void validateDisable(Category category) {
        // Adicione suas regras de validação para desativação aqui
        if (category.getDisabled()) {
            throw new IllegalArgumentException("Category is already disabled");
        }
    }
}
