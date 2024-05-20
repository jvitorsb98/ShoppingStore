package br.com.cepedi.ShoppingStore.service.mail.validations.mail;

public interface MailValidator<T> {
    void validate(T dto);
}
