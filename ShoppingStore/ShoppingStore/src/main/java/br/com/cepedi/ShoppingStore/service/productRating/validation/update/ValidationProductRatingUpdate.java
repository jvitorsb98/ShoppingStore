package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;

public interface ValidationProductRatingUpdate {
    void validation (Long id, DataUpdateProductRating data);

}
