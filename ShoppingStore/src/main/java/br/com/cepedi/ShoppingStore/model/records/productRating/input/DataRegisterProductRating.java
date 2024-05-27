package br.com.cepedi.ShoppingStore.model.records.productRating.input;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record DataRegisterProductRating(

		@NotNull(message = "{notnull.productRating.productId}")
		Long productId,

		@NotNull(message = "{notnull.productRating.ratingStars}")
		@DecimalMin(value = "0.0", message = "{decimalmin.productRating.ratingStars}")
		@DecimalMax(value = "5.0", message = "{decimalmax.productRating.ratingStars}")
		BigDecimal ratingStars,

		@NotBlank(message = "{notblank.productRating.review}")
		@Size(min = 10, max = 200, message = "{size.productRating.review}")
		String review,

		@NotNull(message = "{notnull.productRating.userId}")
		Long userId
) {}
