package br.com.cepedi.ShoppingStore.audit.record.details;


import br.com.cepedi.ShoppingStore.audit.entitys.AuditLog;

import java.util.Date;

public record AuditLogDetails(
    String eventName,
    String eventDescription,
    Date timestamp,
    Long userId,
    String affectedResource,
    String origin
) {

    public AuditLogDetails(AuditLog auditLog){
        this(auditLog.getEventName(),auditLog.getEventDescription(),auditLog.getTimestamp(),auditLog.getUserId(),auditLog.getAffectedResource(),auditLog.getOrigin());
    }


}

