package br.com.cepedi.ShoppingStore.model.records.mail.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataRegisterMail(
    Long id,
    
    @Email
    @NotBlank(message = "Value of the emailFrom field cannot be null or empty")
    String from,
    
    @Email
    @NotBlank(message = "Value of the emailTo field cannot be null or empty")
    String to,

    @NotBlank(message = "Value of the content field cannot be null or empty")
    String content,
    
    String subject
) {
   
}