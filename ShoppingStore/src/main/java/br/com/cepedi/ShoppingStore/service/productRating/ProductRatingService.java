package br.com.cepedi.ShoppingStore.service.productRating;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.productRating.validation.disable.ValidationDisabledProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.validation.register.ValidationProductRatingRegister;
import br.com.cepedi.ShoppingStore.service.productRating.validation.update.ValidationUpdateProductRating;

@Service
public class ProductRatingService {
	
	@Autowired
	private  ProductRatingRepository productRatingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private List<ValidationProductRatingRegister> validatorsRegister;
	
	@Autowired
	private List<ValidationUpdateProductRating> validatorsUpdateProductRating;
	
	@Autowired
	private List<ValidationDisabledProductRating> validationDisabledProductRating;
	
	
	public DataProductRatingDetails  register(DataRegisterProductRating data) {
		validatorsRegister.forEach(validatorsRegister -> validatorsRegister.validation(data));
		
		Product product = productRepository.getReferenceById(data.productId());
		User user = userRepository.getReferenceById(data.userId());
		ProductRating productRating = new ProductRating(data, user, product);
		productRatingRepository.save(productRating);
		
		return new DataProductRatingDetails(productRating);
	}
	
	public Page<DataProductRatingDetails> list(Pageable pageable) {
        return productRatingRepository.findAll(pageable).map(DataProductRatingDetails::new);
    }
	
	public DataProductRatingDetails detailsProduct(Long id) {
		return new DataProductRatingDetails(productRatingRepository.getReferenceById(id));
	}
	
	 public List<DataProductRatingDetails> getProductRatingsByUserId(Long userId) {
	        List<ProductRating> productRatings = productRatingRepository.findAllByUserId(userId);
	        return productRatings.stream().map(DataProductRatingDetails::new).collect(Collectors.toList());
	    }
	 
	 public List<DataProductRatingDetails> getProductRatingsByProductId(Long productId) {
	        List<ProductRating> productRatings = productRatingRepository.findAllByUserId(productId);
	        return productRatings.stream().map(DataProductRatingDetails::new).collect(Collectors.toList());
	    }
	 
	 public DataProductRatingDetails updateProductRating(Long id,DataUpdateProductRating data) {
		 validatorsUpdateProductRating.forEach(validatorsUpdateProductRating -> validatorsUpdateProductRating.validation(id,data));
		 ProductRating productRating = productRatingRepository.getReferenceById(id);
		 productRating.updateDataProductRating(data, userRepository, productRepository);
	 
		 return new DataProductRatingDetails(productRating);	 
	 }
	 
	 public void deleteProductRating(Long id) {
		 validationDisabledProductRating.forEach(validationDisabledProductRating -> validationDisabledProductRating.validation(id));
		 ProductRating productRating = productRatingRepository.getReferenceById(id);
		 productRating.disable();
		 productRatingRepository.deleteById(id);
	 }
}
