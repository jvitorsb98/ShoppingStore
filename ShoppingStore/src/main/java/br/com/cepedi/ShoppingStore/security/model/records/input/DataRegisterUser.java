package br.com.cepedi.ShoppingStore.security.model.records.input;

import br.com.cepedi.ShoppingStore.service.mail.validations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataRegisterUser(
        @NotBlank
        String login,

        @NotBlank
        @Email
        String email,
        @NotBlank
        String name ,

        @NotBlank
        @Password
        String password) {
}
