package br.com.cepedi.ShoppingStore.security.model.records.input;

import jakarta.validation.constraints.NotBlank;

public record DataRequestResetPassword(

        @NotBlank
        String email

) {
}
