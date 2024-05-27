package br.com.cepedi.ShoppingStore.model.records.category.input;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record DataRegisterCategory(

        @NotBlank(message = "{notblank.category.name}")
        String name,

        @NotBlank(message = "{notblank.category.description}")
        String description

) {
}
