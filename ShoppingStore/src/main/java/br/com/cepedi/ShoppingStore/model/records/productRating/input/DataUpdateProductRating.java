package br.com.cepedi.ShoppingStore.model.records.productRating.input;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DataUpdateProductRating(

        @NotNull
        Long id,

        Long productId,

        @DecimalMin(value = "0.0", message = "{decimalmin.productRating.ratingStars}")
        @DecimalMax(value = "5.0", message = "{decimalmax.productRating.ratingStars}")
        BigDecimal ratingStars,

        @Size(min = 10, max = 200, message = "{size.productRating.review}")
        String review,

        Long userId

) {
}
