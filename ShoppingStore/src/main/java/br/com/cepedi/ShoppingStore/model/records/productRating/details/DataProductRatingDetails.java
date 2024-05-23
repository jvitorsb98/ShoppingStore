package br.com.cepedi.ShoppingStore.model.records.productRating.details;

import java.math.BigDecimal;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;

public record DataProductRatingDetails(
    Long id,
    Long idProduct,
    BigDecimal ratingStars,
    String review,
	Long idUser
) {

	public DataProductRatingDetails(ProductRating productRating) {
		this(
			productRating.getId(),
			productRating.getProduct().getId(),
			productRating.getRatingStars(),
			productRating.getReview(),
			productRating.getUser().getId()
		);
	}}
