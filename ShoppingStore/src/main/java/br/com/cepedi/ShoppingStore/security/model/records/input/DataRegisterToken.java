package br.com.cepedi.ShoppingStore.security.model.records.input;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;

public record DataRegisterToken(

        @NotBlank
        String token,

        @NotNull
        Long user_id,

        @NotNull
        @Future
        Instant expireDate

) {
}
