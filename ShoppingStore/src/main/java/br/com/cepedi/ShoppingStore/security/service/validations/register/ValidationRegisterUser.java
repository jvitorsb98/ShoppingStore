package br.com.cepedi.ShoppingStore.security.service.validations.register;

import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;

public interface ValidationRegisterUser {

    void validation(DataRegisterUser data);
}
