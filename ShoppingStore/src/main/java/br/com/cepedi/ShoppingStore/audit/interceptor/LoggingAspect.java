package br.com.cepedi.ShoppingStore.audit.interceptor;

import br.com.cepedi.ShoppingStore.audit.record.input.DataRegisterAudit;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.audit.service.AuditService;

@Aspect
@Component
public class LoggingAspect {
	
    @Autowired
    private AuditService auditService;

    private static ThreadLocal<String> clientIpAddress = new ThreadLocal<>();

    public static void setClientIpAddress(String ipAddress) {
        clientIpAddress.set(ipAddress);
    }

    public static void clearClientIpAddress() {
        clientIpAddress.remove();
    }

    // Exclui AuditService do observado para evitar loop infinito
    @Before("execution(* br.com.cepedi.ShoppingStore.audit.service.*.*(..)) && !target(br.com.cepedi.ShoppingStore.audit.service.AuditService)")
    public void logServiceAccess(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String description = "Method execution";
        User user = null;
        String origin = clientIpAddress.get();
       
      //arguardando o SpringSecurity para conserta a auditoria e fazer seus testes.
        
       // Recupera informações do usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        }

        DataRegisterAudit dataRegisterAudit = new DataRegisterAudit(methodName,description,joinPoint.getTarget().getClass().getSimpleName(),origin);

        auditService.logEvent(dataRegisterAudit, user);    }
    
}