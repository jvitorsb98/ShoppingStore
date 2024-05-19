package br.com.cepedi.ShoppingStore.security.model.validations.mail;

public interface MailValidator<T> {
    void validate(T dto);
}
