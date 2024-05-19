package br.com.cepedi.ShoppingStore.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.product.validations.ValidationProduct;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

	@Autowired
    private List<ValidationProduct> validators; 

    public DataProductDetails register(DataRegisterProduct data) {
        // Validar os dados de entrada usando uma lista de validadores
        validators.forEach(validator -> validator.validation(data));

        // Obter a referência da categoria pelo ID
        Category category = categoryRepository.getReferenceById(data.categoryId());

        // Criar um novo objeto de produto
        Product product = new Product(
            null,
            data.name(),
            data.description(),
            data.price(),
            data.sku(),
            data.imageUrl(),
            data.quantity(),
            data.manufacturer(),
            data.featured(),
            category
        );

        // Salvar o produto no repositório
        productRepository.save(product);

        // Retornar os detalhes do produto registrado
        return new DataProductDetails(product);
    }

    public Page<DataProductDetails> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(DataProductDetails::new);
    }
}
