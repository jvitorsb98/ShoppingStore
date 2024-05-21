package br.com.cepedi.ShoppingStore.service.productAttribute;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.disabled.ValidationDisabledProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.register.ValidationRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.update.ValidationUpdateProductAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private List<ValidationRegisterProductAttribute> validationsRegister;

    @Autowired
    private List<ValidationDisabledProductAttribute> validationsDisabled;

    @Autowired
    private List<ValidationUpdateProductAttribute> validationsUpdate;

    @Transactional
    public DataProductAttributeDetails register(DataRegisterProductAttribute data){
        validationsRegister.forEach(validator -> validator.validation(data));
        Product product = productRepository.getReferenceById(data.productId());
        ProductAttribute productAttribute = new ProductAttribute(data, product);
        productAttributeRepository.save(productAttribute);
        return DataProductAttributeDetails.fromEntity(productAttribute);
    }

    public Page<DataProductAttributeDetails> list(Pageable pageable){
        return productAttributeRepository.findAllByDisabledFalse(pageable).map(DataProductAttributeDetails::fromEntity);
    }

    public Page<DataProductAttributeDetails> listDeactivated(Pageable pageable){
        return productAttributeRepository.findAllByDisabledTrue(pageable).map(DataProductAttributeDetails::fromEntity);
    }

    public List<DataProductAttributeDetails> listByProductId(Long id){
        return productAttributeRepository.findAllByProductIdAndDisabledIsFalse(id)
                .stream()
                .map(DataProductAttributeDetails::fromEntity)
                .collect(Collectors.toList());
    }

    public List<DataProductAttributeDetails> listByProductIdDeactivated(Long id){
        return productAttributeRepository.findAllByProductIdAndDisabledIsTrue(id)
                .stream()
                .map(DataProductAttributeDetails::fromEntity)
                .collect(Collectors.toList());
    }

    public DataProductAttributeDetails details(Long id){
        return DataProductAttributeDetails.fromEntity(productAttributeRepository.getReferenceById(id));
    }

    @Transactional
    public DataProductAttributeDetails update(DataUpdateProductAttribute data){
        validationsUpdate.forEach(validator -> validator.validation(data));
        ProductAttribute productAttribute = productAttributeRepository.getReferenceById(data.id());
        productAttribute.updateProductAttribute(data);
        productAttributeRepository.save(productAttribute);
        return DataProductAttributeDetails.fromEntity(productAttribute);
    }

    @Transactional
    public void disabled(Long id){
        validationsDisabled.forEach(validator -> validator.validation(id));
        ProductAttribute productAttribute = productAttributeRepository.getReferenceById(id);
        productAttribute.disable();
        productAttributeRepository.save(productAttribute);
    }
}
