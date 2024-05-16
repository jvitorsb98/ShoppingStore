package br.com.cepedi.ShoppingStore.audit.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataRegisterAudit(

        @NotNull
        String eventName,

        String eventDescription,

        @NotNull
        Long userId,

        String affectedResource,

        @NotNull
        @Positive
        String origin


) {
}
