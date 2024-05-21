package br.com.cepedi.ShoppingStore.service.productRating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.details.DataProductRatingDetails;
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

    public DataProductRatingDetails register(DataRegisterProductRating data) {
        // Validar os dados de entrada usando uma lista de validadores
        validatorsRegister.forEach(validator -> validator.validation(data));

        // Obter as referências do produto e do usuário pelo ID
        Product product = productRepository.getReferenceById(data.productId());
        User user = userRepository.getReferenceById(data.userId());

        // Criar um novo objeto de avaliação de produto
        ProductRating productRating = new ProductRating(data, user, product);

        // Salvar a avaliação no repositório
        productRatingRepository.save(productRating);

        // Retornar os detalhes da avaliação registrada
        return new DataProductRatingDetails(productRating);
    }

    public Page<DataProductRatingDetails> list(Pageable pageable) {
        return productRatingRepository.findAll(pageable).map(DataProductRatingDetails::new);
    }

    public DataProductRatingDetails detailsProduct(Long id) {
        return new DataProductRatingDetails(productRatingRepository.getReferenceById(id));
    }

    public DataProductRatingDetails deleteProduct(Long id,Pageable pageable) {
        ProductRating productRating = productRatingRepository.getReferenceById(id);
        productRatingRepository.delete(productRating);
        return new DataProductRatingDetails(productRating);
    }
}
