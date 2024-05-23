package br.com.cepedi.ShoppingStore.model.records.possibleFacets.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record DataRegisterPossibleFacets (
        
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Category ID cannot be null")
        Long idCategory


) {
	

}
