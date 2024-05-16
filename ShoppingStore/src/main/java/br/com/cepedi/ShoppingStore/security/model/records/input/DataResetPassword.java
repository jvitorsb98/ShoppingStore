package br.com.cepedi.ShoppingStore.security.model.records.input;

import br.com.cepedi.ShoppingStore.security.model.validations.Password;
import jakarta.validation.constraints.NotBlank;

public record DataResetPassword(

        @NotBlank
        String token,

        @Password
        String password

) {
}
