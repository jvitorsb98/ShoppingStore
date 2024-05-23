package br.com.cepedi.ShoppingStore.service.product;

import java.util.List;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
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
import br.com.cepedi.ShoppingStore.service.product.validations.cancel.ValidationDisabledProduct;
import br.com.cepedi.ShoppingStore.service.product.validations.register.ValidationRegisterProduct;
import br.com.cepedi.ShoppingStore.service.product.validations.update.ValidationUpdateProduct;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

	@Autowired
    private List<ValidationRegisterProduct> validatorsRegister;
	
	@Autowired
    private List<ValidationUpdateProduct> validatorsUpdateProduct;
	
	@Autowired
    private List<ValidationDisabledProduct> validatorsCancelProduct;

    public DataProductDetails register(DataRegisterProduct data) {
        validatorsRegister.forEach(validator -> validator.validation(data));
        Category category = categoryRepository.getReferenceById(data.categoryId());
        Brand brand = brandRepository.getReferenceById(data.brandId());
        Product product = new Product(data, category, brand);
        productRepository.save(product);
        return new DataProductDetails(product);
    }

    public Page<DataProductDetails> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(DataProductDetails::new);
    }

    public Page<DataProductDetails> listDeactivated(Pageable pageable){
        return productRepository.findAllByDisabledTrue(pageable).map(DataProductDetails::new);
    }
    
	public DataProductDetails detailsProduct(Long id) {
		return new DataProductDetails(productRepository.getReferenceById(id));
	}
	
	public Page<DataProductDetails> detailsProductCategory(Long id, Pageable pageable) {
		return productRepository.findAllByCategoryId(id,pageable).map(DataProductDetails::new);
	}

    public Page<DataProductDetails> detailsProductCategoryAndIsDeactivated(Long id, Pageable pageable) {
        return productRepository.findAllByCategoryIdAndDisabledIsTrue(id,pageable).map(DataProductDetails::new);
    }
	
	public DataProductDetails updateProduct(DataUpdateProduct data) {
		validatorsUpdateProduct.forEach(validatorsUpdateProduct -> validatorsUpdateProduct.validation(data));
        Category category = null;
        if(data.categoryId()!=null){
            category = categoryRepository.getReferenceById(data.categoryId());
        }

		 Product product = productRepository.getReferenceById(data.id());
		 product.updateDataProduct(data,category);
		 return new DataProductDetails(product);
	}
	
	public void deleteProduct(Long id) {
        validatorsCancelProduct.forEach(validatorsCancelProduct -> validatorsCancelProduct.validation(id));
        Product product = productRepository.getReferenceById(id);
        product.disable();
    }
	
}
