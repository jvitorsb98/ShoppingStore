package br.com.cepedi.ShoppingStore.service.product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.product.cancel.ValidationCancelProduct;
import br.com.cepedi.ShoppingStore.service.product.register.ValidationProduct;
import br.com.cepedi.ShoppingStore.service.product.update.ValidationUpdateProduct;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

	@Autowired
    private List<ValidationProduct> validatorsRegister;
	
	@Autowired
    private List<ValidationUpdateProduct> validatorsUpdateProduct;
	
	@Autowired
    private List<ValidationCancelProduct> validatorsCancelProduct;

    public DataProductDetails register(DataRegisterProduct data) {
        // Validar os dados de entrada usando uma lista de validadores
        validatorsRegister.forEach(validator -> validator.validation(data));

        // Obter a referência da categoria pelo ID
        Category category = categoryRepository.getReferenceById(data.categoryId());

        // Criar um novo objeto de produto
        Product product = new Product(data, category);

        // Salvar o produto no repositório
        productRepository.save(product);

        // Retornar os detalhes do produto registrado
        return new DataProductDetails(product);
    }

    public Page<DataProductDetails> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(DataProductDetails::new);
    }
    
	public DataProductDetails detailsProduct(Long id) {
		return new DataProductDetails(productRepository.getReferenceById(id));
	}
	
	public Page<DataProductDetails> detailsProductCategory(Long id, Pageable pageable) {
		return productRepository.findAllByCategoryId(id,pageable).map(DataProductDetails::new);
	}
	
	public DataProductDetails updateProduct(Long id, DataUpdateProduct data) {
		validatorsUpdateProduct.forEach(validatorsUpdateProduct -> validatorsUpdateProduct.validation(id, data));
		 Product product = productRepository.getReferenceById(id);
		 product.updateDataProduct(data);	
		 return new DataProductDetails(product);
	}
	
	public void deleteProduct(Long id) {
        validatorsCancelProduct.forEach(validatorsCancelProduct -> validatorsCancelProduct.validation(id));
        Product product = productRepository.getReferenceById(id);
        product.disable();
    }
	
}
