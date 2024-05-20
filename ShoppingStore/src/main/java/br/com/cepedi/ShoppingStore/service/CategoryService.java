package br.com.cepedi.ShoppingStore.service;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public DataCategoryDetails registerCategory(DataRegisterCategory data) {
        Category category = new Category(data);
        category = categoryRepository.save(category);
        return new DataCategoryDetails(category.getId(), category.getName(), category.getDescription());
    }

    public Page<DataCategoryDetails> listAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> new DataCategoryDetails(category.getId(), category.getName(), category.getDescription()));
    }

    public Page<DataCategoryDetails> listCategoriesByName(String name, Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable)
                .map(category -> new DataCategoryDetails(category.getId(), category.getName(), category.getDescription()));
    }

    public Page<DataCategoryDetails> listCategoriesByDescription(String description, Pageable pageable) {
        return categoryRepository.findByDescriptionContaining(description, pageable)
                .map(category -> new DataCategoryDetails(category.getId(), category.getName(), category.getDescription()));
    }

    public DataCategoryDetails getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new DataCategoryDetails(category.getId(), category.getName(), category.getDescription());
    }
}


