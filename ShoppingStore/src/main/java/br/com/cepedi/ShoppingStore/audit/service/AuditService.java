package br.com.cepedi.ShoppingStore.audit.service;



import br.com.cepedi.ShoppingStore.audit.record.input.DataRegisterAudit;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cepedi.ShoppingStore.audit.entitys.AuditLog;
import br.com.cepedi.ShoppingStore.audit.repositorys.AuditLogRepository;

@Service
public class AuditService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logEvent(DataRegisterAudit data, User user) {
        AuditLog log = new AuditLog(data,user);
        auditLogRepository.save(log);
    }
}
