package br.com.cepedi.ShoppingStore.model.records.productRating.input;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import br.com.cepedi.ShoppingStore.model.entitys.Product;

public record DataRegisterProductRating(

		@NotNull(message = "User ID cannot be null")
		Long productId,

		@NotNull(message = "Rating Stars cannot be null")
		@DecimalMin(value = "0.0", message = "Rating Stars must be at least 0.0")
		@DecimalMax(value = "5.0", message = "Rating Stars must be at most 5.0")
		BigDecimal ratingStars,
		
		@NotBlank(message = "Description cannot be null or empty")
        @Size(min = 10, max = 200, message = "Review must be between 10 and 200 characters")
		String review,
		
		@NotNull(message = "User ID cannot be null")
        Long Userid


) {





}
