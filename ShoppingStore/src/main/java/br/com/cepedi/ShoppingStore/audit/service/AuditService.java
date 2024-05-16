package br.com.cepedi.ShoppingStore.audit.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cepedi.ShoppingStore.audit.entitys.AuditLog;
import br.com.cepedi.ShoppingStore.audit.repositorys.AuditLogRepository;
import br.com.cepedi.ShoppingStore.record.AuditLogDTO;

@Service
public class AuditService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logEvent(AuditLogDTO auditLogDTO) {
        AuditLog log = new AuditLog();
        log.setEventName(auditLogDTO.eventName());
        log.setEventDescription(auditLogDTO.eventDescription());
        log.setTimestamp(auditLogDTO.timestamp());
        log.setUserId(auditLogDTO.userId());
        log.setAffectedResource(auditLogDTO.affectedResource());
        log.setOrigin(auditLogDTO.origin());
        auditLogRepository.save(log);
    }
}
