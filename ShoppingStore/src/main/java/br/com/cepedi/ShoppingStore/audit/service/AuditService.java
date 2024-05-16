package br.com.cepedi.ShoppingStore.audit.service;



import br.com.cepedi.ShoppingStore.audit.record.DataRegisterAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cepedi.ShoppingStore.audit.entitys.AuditLog;
import br.com.cepedi.ShoppingStore.audit.repositorys.AuditLogRepository;
import br.com.cepedi.ShoppingStore.record.AuditLogDTO;

@Service
public class AuditService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logEvent(DataRegisterAudit data) {
        AuditLog log = new AuditLog(data);
        auditLogRepository.save(log);
    }
}
