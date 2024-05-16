package br.com.cepedi.ShoppingStore.record;


import java.util.Date;

public record AuditLogDetails(
    String eventName,
    String eventDescription,
    Date timestamp,
    String userId,
    String affectedResource,
    String origin
) {}

