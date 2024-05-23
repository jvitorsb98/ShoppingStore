	package br.com.cepedi.ShoppingStore.service.productRating.validation.register;
	
	import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
	import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
	import jakarta.validation.ValidationException;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;
	
	@Component
	public class ValidationUserExistsForRegisterProductRating implements ValidationProductRatingRegister{
	
	    @Autowired
	    private UserRepository userRepository;
	
	    @Override
	    public void validation(DataRegisterProductRating data) {
	        if (data.Userid() != null && !userRepository.existsById(data.Userid())) {
	            throw new ValidationException("The provided user id does not exist");
	        }
	    }
	}
