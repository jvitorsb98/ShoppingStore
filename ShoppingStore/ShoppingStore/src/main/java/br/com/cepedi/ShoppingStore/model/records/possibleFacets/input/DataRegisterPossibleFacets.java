package br.com.cepedi.ShoppingStore.model.records.possibleFacets.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record DataRegisterPossibleFacets (

        @NotBlank
        String name,

        @NotNull
        Long idCategory


) {

}
