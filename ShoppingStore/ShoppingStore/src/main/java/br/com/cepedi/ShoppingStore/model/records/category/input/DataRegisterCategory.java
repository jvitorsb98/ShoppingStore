package br.com.cepedi.ShoppingStore.model.records.category.input;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record DataRegisterCategory(

        @NotBlank
        String name,

        @NotBlank
        String description

) {
}
