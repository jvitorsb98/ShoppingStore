package br.com.cepedi.ShoppingStore.model.records.possibleFacets.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record DataRegisterPossibleFacets (

        @NotBlank(message = "{notblank.facets.name}")
        String name,

        @NotNull(message = "{notnull.facets.idCategory}")
        Long idCategory


) {
	

}
