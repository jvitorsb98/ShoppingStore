package br.com.cepedi.ShoppingStore.audit.record.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataRegisterAudit(

        @NotNull
        String eventName,

        String eventDescription,

        String affectedResource,

        @NotNull
        @Positive
        String origin


) {
}
