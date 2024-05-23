package br.com.cepedi.ShoppingStore.service.productRating;

import java.util.List;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.validation.disabled.ValidationDisabledProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.validation.update.ValidationUpdateProductRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.productRating.validation.register.ValidationProductRatingRegister;

@Service
public class ProductRatingService {

	@Autowired
	private ProductRatingRepository productRatingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private List<ValidationProductRatingRegister> validatorsRegister;

	@Autowired
	private List<ValidationUpdateProductRating> validatorsUpdate;

	@Autowired
	private List<ValidationDisabledProductRating> validatorsDisabled;

	public DataProductRatingDetails register(DataRegisterProductRating data) {
		validatorsRegister.forEach(validator -> validator.validation(data));
		Product product = productRepository.getReferenceById(data.productId());
		User user = userRepository.getReferenceById(data.Userid());
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
	
	 public Page<DataProductRatingDetails> getProductRatingsByUserId(Long userId,  Pageable pageable) {       
	        return productRatingRepository.findAllByUserId(userId, pageable).map(DataProductRatingDetails::new);
	    }
	 
	 public Page<DataProductRatingDetails> getProductRatingsByProductId(Long productId, Pageable pageable) {
	       
		 return productRatingRepository.findAllByProductId(productId, pageable).map(DataProductRatingDetails::new);
	    }
	
	public DataProductRatingDetails updateProductRating(DataUpdateProductRating data) {
		validatorsUpdate.forEach(validator -> validator.validation(data));

		ProductRating productRating = productRatingRepository.getReferenceById(data.id());
		User user = null;
		Product product = null;
		if(data.userId() != null){
			user = userRepository.getReferenceById(data.userId());
		}

		if(data.productId() != null){
			product = productRepository.getReferenceById(data.productId());
		}

		productRating.updateDataProductRating(data, user, product);

		return new DataProductRatingDetails(productRating);
	}

	public void disableProductRating(Long id) {
		validatorsDisabled.forEach(validator -> validator.validation(id));
		ProductRating productRating = productRatingRepository.getReferenceById(id);

		productRating.disable();
	}
	
}
