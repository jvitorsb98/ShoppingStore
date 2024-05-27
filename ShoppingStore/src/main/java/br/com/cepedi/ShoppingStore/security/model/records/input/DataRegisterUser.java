package br.com.cepedi.ShoppingStore.security.model.records.input;

import br.com.cepedi.ShoppingStore.model.records.validations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataRegisterUser(
        @NotBlank(message = "{notblank.user.login}")
        String login,

        @NotBlank(message = "{notblank.user.email}")
        @Email(message = "{email.user.email}")
        String email,

        @NotBlank(message = "{notblank.user.name}")
        String name,

        @NotBlank(message = "{notblank.user.password}")
        @Password(message = "{password.user.password}")
        String password
) {}
