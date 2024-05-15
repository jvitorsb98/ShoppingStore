package br.com.cepedi.ShoppingStore.records.mail.details;

import br.com.cepedi.ShoppingStore.model.entitys.Mail;

public record DataDetailsMail(
		 Long id,
		 String from,
	     String to,
	     String content,
		 String subject	
	) {
	public DataDetailsMail(Mail mail) {
		this( mail.getId(), mail.getFrom(), mail.getTo(), mail.getContent(), mail.getSubject());		
	}
}
