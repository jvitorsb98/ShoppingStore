package br.com.cepedi.ShoppingStore.service.productAttribute.validations;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;

public interface ValidationProductAttribute {
    void validateIfProductExist(DataRegisterProductAttribute data);
}
