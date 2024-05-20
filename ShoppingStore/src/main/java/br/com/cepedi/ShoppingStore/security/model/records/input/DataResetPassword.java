package br.com.cepedi.ShoppingStore.security.model.records.input;

import br.com.cepedi.ShoppingStore.service.mailservice.validations.Password;
import jakarta.validation.constraints.NotBlank;

public record DataResetPassword(

        @NotBlank
        String token,

        @Password
        String password

) {
}
