package br.com.cepedi.ShoppingStore.audit.serviceTest;

import static org.mockito.Mockito.verify;

import java.util.Date;


import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.cepedi.ShoppingStore.audit.repositorys.AuditLogRepository;
import br.com.cepedi.ShoppingStore.audit.service.AuditService;

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


}

