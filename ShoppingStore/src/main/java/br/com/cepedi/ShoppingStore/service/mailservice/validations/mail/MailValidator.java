package br.com.cepedi.ShoppingStore.service.mailservice.validations.mail;

public interface MailValidator<T> {
    void validate(T dto);
}
