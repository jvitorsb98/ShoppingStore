package br.com.cepedi.ShoppingStore.audit.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cepedi.ShoppingStore.audit.entitys.AuditLog;
import br.com.cepedi.ShoppingStore.audit.repositorys.AuditLogRepository;

@Service
public class AuditService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logEvent(String eventName, String description, String userId, String resource, String origin) {
        AuditLog log = new AuditLog();
        log.setEventName(eventName);
        log.setEventDescription(description);
        log.setTimestamp(new Date()); // Registro automático da data/hora atual
        log.setUserId(userId); // ID do usuário (extraído do contexto de segurança)
        log.setAffectedResource(resource); // Nome da classe ou método que foi afetado
        log.setOrigin(origin); // Endereço IP de origem
        auditLogRepository.save(log);
    }
}