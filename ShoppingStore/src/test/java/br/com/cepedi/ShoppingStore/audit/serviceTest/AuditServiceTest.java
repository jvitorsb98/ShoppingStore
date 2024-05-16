package br.com.cepedi.ShoppingStore.audit.serviceTest;

import static org.mockito.Mockito.verify;

import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.cepedi.ShoppingStore.audit.repositorys.AuditLogRepository;
import br.com.cepedi.ShoppingStore.audit.service.AuditService;
import br.com.cepedi.ShoppingStore.record.AuditLogDTO;

@SpringBootTest
public class AuditServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditService auditService;

    @InjectMocks
    private AuditLogDTO auditLogDTO;

    @Before
    public void setUp() {
   

    	auditLogDTO = new AuditLogDTO("TestEvent","TestDescription", "TestUser","TestResource", "TestOrigin", new Date());
        auditLogDTO.eventName();
        auditLogDTO.eventDescription();
        auditLogDTO.timestamp();
        auditLogDTO.userId();
        auditLogDTO.affectedResource();
        auditLogDTO.origin();
    }

    @Test
    public void testLogEvent() {
        auditService.logEvent(auditLogDTO);

        verify(auditLogRepository).save(
                argThat(log -> "TestEvent".equals(log.getEventName()) &&
                        "TestDescription".equals(log.getEventDescription()) &&
                        auditLogDTO.timestamp().equals(log.getTimestamp()) &&
                        "TestUser".equals(log.getUserId()) &&
                        "TestResource".equals(log.getAffectedResource()) &&
                        "TestOrigin".equals(log.getOrigin())
                )
        );
    }
}

