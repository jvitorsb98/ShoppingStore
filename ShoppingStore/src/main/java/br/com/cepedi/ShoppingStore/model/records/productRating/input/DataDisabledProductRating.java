package br.com.cepedi.ShoppingStore.model.records.productRating.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DataDisabledProductRating(
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
        Long userId
				
		) {

}
