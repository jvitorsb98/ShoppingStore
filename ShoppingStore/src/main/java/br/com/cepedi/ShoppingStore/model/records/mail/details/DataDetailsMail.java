package br.com.cepedi.ShoppingStore.model.records.mail.details;

import br.com.cepedi.ShoppingStore.security.model.entitys.Mail;
import jakarta.validation.constraints.NotBlank;

public record DataDetailsMail(
        Long id,
        @NotBlank(message = "{NotBlank.dataDetailsMail.from}")
        String from,
        @NotBlank(message = "{NotBlank.dataDetailsMail.to}")
        String to,
        @NotBlank(message = "{NotBlank.dataDetailsMail.content}")
        String content,
        @NotBlank(message = "{NotBlank.dataDetailsMail.subject}")
        String subject
	) {
	public DataDetailsMail(Mail mail) {
		this( mail.getId(), mail.getFrom(), mail.getTo(), mail.getContent(), mail.getSubject());		
	}
}
