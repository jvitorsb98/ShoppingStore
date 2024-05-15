package br.com.cepedi.ShoppingStore.record;

import java.util.Date;

public record AuditLogDTO(
	    String eventName,
	    String eventDescription,
	    String userId,
	    String affectedResource,
	    String origin,
	    Date timestamp
	) {}