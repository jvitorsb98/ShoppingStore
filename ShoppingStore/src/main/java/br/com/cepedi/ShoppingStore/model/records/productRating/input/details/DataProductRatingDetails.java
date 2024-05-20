package br.com.cepedi.ShoppingStore.model.records.productRating.input.details;

import java.math.BigDecimal;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;

public record DataProductRatingDetails(
    Long id,
    Product product,
    BigDecimal ratingStars,
    String review,
    User user
) {

	public DataProductRatingDetails(ProductRating productRating) {
		this(
			productRating.getId(),
			productRating.getProduct(),
			productRating.getRatingStars(),
			productRating.getReview(),
			productRating.getUser()
		);
	}}
