package br.com.cepedi.ShoppingStore.model.records.possibleFacets.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DataRegisterPossibleFacets (

        @NotBlank
        String name,

        @NotNull
        Long idCategory


) {
	

}
