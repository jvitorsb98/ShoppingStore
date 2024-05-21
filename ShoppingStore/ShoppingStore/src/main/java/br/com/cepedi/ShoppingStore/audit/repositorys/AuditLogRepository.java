package br.com.cepedi.ShoppingStore.audit.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cepedi.ShoppingStore.audit.entitys.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {	
}
