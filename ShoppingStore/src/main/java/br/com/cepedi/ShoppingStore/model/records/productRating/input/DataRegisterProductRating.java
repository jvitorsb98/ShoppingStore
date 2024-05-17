package br.com.cepedi.ShoppingStore.model.records.productRating.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DataRegisterProductRating(

        @NotNull
        @Positive
        BigDecimal ratingStars,

        String review,

        Long Userid


) {



}
