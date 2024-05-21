package br.com.cepedi.ShoppingStore.audit.interceptor;

import br.com.cepedi.ShoppingStore.audit.record.input.DataRegisterAudit;
import br.com.cepedi.ShoppingStore.audit.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        // Set up the security context with authentication
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Deve registrar o acesso ao método e capturar as informações corretas do usuário e endereço IP")
    void shouldLogMethodAccessAndCaptureCorrectUserInfoAndIpAddress() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getTarget()).thenReturn(new Object());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        String ipAddress = "192.168.0.1";
        LoggingAspect.setClientIpAddress(ipAddress);

        ArgumentCaptor<DataRegisterAudit> auditCaptor = ArgumentCaptor.forClass(DataRegisterAudit.class);

        // Act
        loggingAspect.logServiceAccess(joinPoint);

        // Assert
        verify(auditService).logEvent(auditCaptor.capture(), Mockito.eq(user));

        DataRegisterAudit capturedAudit = auditCaptor.getValue();
        assertEquals("testMethod", capturedAudit.eventName());
        assertEquals("Method execution", capturedAudit.eventDescription());
        assertEquals("Object", capturedAudit.affectedResource());
        assertEquals(ipAddress, capturedAudit.origin());

        LoggingAspect.clearClientIpAddress();
    }

    @Test
    @DisplayName("Deve registrar o acesso ao método com usuário nulo quando não autenticado")
    void shouldLogMethodAccessWithNullUserWhenNotAuthenticated() {
        // Arrange
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getTarget()).thenReturn(new Object());
        when(securityContext.getAuthentication()).thenReturn(null);
        String ipAddress = "192.168.0.1";
        LoggingAspect.setClientIpAddress(ipAddress);

        ArgumentCaptor<DataRegisterAudit> auditCaptor = ArgumentCaptor.forClass(DataRegisterAudit.class);

        // Act
        loggingAspect.logServiceAccess(joinPoint);

        // Assert
        verify(auditService).logEvent(auditCaptor.capture(), Mockito.isNull());

        DataRegisterAudit capturedAudit = auditCaptor.getValue();
        assertEquals("testMethod", capturedAudit.eventName());
        assertEquals("Method execution", capturedAudit.eventDescription());
        assertEquals("Object", capturedAudit.affectedResource());
        assertEquals(ipAddress, capturedAudit.origin());
        LoggingAspect.clearClientIpAddress();
    }

}