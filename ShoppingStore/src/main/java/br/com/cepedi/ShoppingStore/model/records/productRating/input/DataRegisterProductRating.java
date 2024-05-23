package br.com.cepedi.ShoppingStore.model.records.productRating.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

import br.com.cepedi.ShoppingStore.model.entitys.Product;

public record DataRegisterProductRating(

		@NotNull(message = "User ID cannot be null")
		Long productId,
		
		@Positive(message = "Quantity must be a positive value")
		@NotBlank(message = "Description cannot be null or empty")
		@NotNull(message = "Quantity cannot be null")
		BigDecimal ratingStars,
		
		@NotBlank(message = "Description cannot be null or empty")
        @Size(min = 10, max = 200, message = "Review must be between 10 and 200 characters")
		String review,
		
		@NotNull(message = "User ID cannot be null")
        Long Userid


) {





}
