package br.com.cepedi.ShoppingStore.service.category;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.service.category.validations.CategoryValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryValidator categoryValidator;

    public DataCategoryDetails registerCategory(DataRegisterCategory data) {
        Category category = new Category(data);
        category = categoryRepository.save(category);
        return new DataCategoryDetails(category);
    }

    public Page<DataCategoryDetails> listAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(DataCategoryDetails::new);
    }

    public Page<DataCategoryDetails> listCategoriesByName(String name, Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable)
                .map(DataCategoryDetails::new);
    }

    public Page<DataCategoryDetails> listCategoriesByDescription(String description, Pageable pageable) {
        return categoryRepository.findByDescriptionContaining(description, pageable)
                .map(DataCategoryDetails::new);
    }

    public DataCategoryDetails getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new DataCategoryDetails(category);
    }
    
    public DataCategoryDetails updateCategory(Long id, DataRegisterCategory newData) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Crie uma nova instância de Category com os dados atualizados
        Category updatedCategory = buildUpdatedCategory(category, newData);

        // Validação para atualização
        categoryValidator.validateUpdate(updatedCategory);

        // Salve a categoria atualizada
        Category savedCategory = categoryRepository.save(updatedCategory);

        return new DataCategoryDetails(savedCategory);
    }
    
    private Category buildUpdatedCategory(Category category, DataRegisterCategory newData) {
        Category updatedCategory = new Category();
        updatedCategory.setId(category.getId());
        updatedCategory.setName(newData.name());
        updatedCategory.setDescription(newData.description());

        return updatedCategory;
    }

    public DataCategoryDetails disableCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Validação para desativação
        categoryValidator.validateDisable(category);

        category.disable();
        category = categoryRepository.save(category);
        return new DataCategoryDetails(category);
    }
}


