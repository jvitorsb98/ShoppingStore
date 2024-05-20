package br.com.cepedi.ShoppingStore.service.productAttribute;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.ValidationProductAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private List<ValidationProductAttribute> validators;

    public DataProductAttributeDetails register(DataRegisterProductAttribute data){
        validators.forEach(validator -> validator.validation(data));
        Product product = productRepository.getReferenceById(data.productId());
        ProductAttribute productAttribute = new ProductAttribute(data, product);
        productAttributeRepository.save(productAttribute);
        return DataProductAttributeDetails.fromEntity(productAttribute);
    }

    public Page<DataProductAttributeDetails> list(Pageable pageable){
        return productAttributeRepository.findAll(pageable).map(DataProductAttributeDetails::fromEntity);
    }

    public List<DataProductAttributeDetails> listByProductId(Long id){
        return productAttributeRepository.findAllByProductId(id)
                .stream()
                .map(DataProductAttributeDetails::fromEntity)
                .collect(Collectors.toList());
    }

    public DataProductAttributeDetails details(Long id){
        return DataProductAttributeDetails.fromEntity(productAttributeRepository.getReferenceById(id));
    }
}
