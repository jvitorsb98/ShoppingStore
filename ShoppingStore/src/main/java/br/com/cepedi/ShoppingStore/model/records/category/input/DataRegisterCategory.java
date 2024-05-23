package br.com.cepedi.ShoppingStore.model.records.category.input;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record DataRegisterCategory(

        @NotBlank(message = "{NotBlank.dataRegisterCategory.name}")
        @JsonPropertyDescription("Nome da categoria")
        String name,

        @NotBlank(message = "{NotBlank.dataRegisterCategory.description}")
        @JsonPropertyDescription("Descrição da categoria")
        String description
) {
}
