package br.com.cepedi.ShoppingStore.audit.entitys;


import br.com.cepedi.ShoppingStore.audit.record.input.DataRegisterAudit;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class AuditLogTest {

    @Mock
    private User mockUser;

    private Faker faker = new Faker();

    @Test
    public void testAuditLogConstructor() {
        // Given
        DataRegisterAudit dataRegisterAudit = createMockDataRegisterAudit();

        // When
        AuditLog auditLog = new AuditLog(dataRegisterAudit, mockUser);

        // Then
        assertEquals(dataRegisterAudit.eventName(), auditLog.getEventName());
        assertEquals(dataRegisterAudit.eventDescription(), auditLog.getEventDescription());
        assertEquals(dataRegisterAudit.affectedResource(), auditLog.getAffectedResource());
        assertEquals(dataRegisterAudit.origin(), auditLog.getOrigin());
        assertEquals(mockUser, auditLog.getUser());
    }

    private DataRegisterAudit createMockDataRegisterAudit() {
        return new DataRegisterAudit(
                faker.lorem().word(),
                faker.lorem().sentence(),
                faker.lorem().word(),
                faker.lorem().word()
        );
    }
    @Test
    public void testGetters() {
        // Given
        Long id = faker.number().randomNumber();
        String eventName = faker.lorem().word();
        String eventDescription = faker.lorem().sentence();
        Date timestamp = new Date();
        String affectedResource = faker.lorem().word();
        String origin = faker.lorem().word();

        // When
        AuditLog auditLog = new AuditLog();
        auditLog.setId(id);
        auditLog.setEventName(eventName);
        auditLog.setEventDescription(eventDescription);
        auditLog.setTimestamp(timestamp);
        auditLog.setAffectedResource(affectedResource);
        auditLog.setOrigin(origin);

        // Then
        assertEquals(id, auditLog.getId());
        assertEquals(eventName, auditLog.getEventName());
        assertEquals(eventDescription, auditLog.getEventDescription());
        assertEquals(timestamp, auditLog.getTimestamp());
        assertEquals(affectedResource, auditLog.getAffectedResource());
        assertEquals(origin, auditLog.getOrigin());
    }

    @Test
    public void testSetters() {
        // Given
        AuditLog auditLog = new AuditLog();
        User user = new User();
        Long id = faker.number().randomNumber();
        String eventName = faker.lorem().word();
        String eventDescription = faker.lorem().sentence();
        Date timestamp = new Date();
        String affectedResource = faker.lorem().word();
        String origin = faker.lorem().word();

        // When
        auditLog.setUser(user);
        auditLog.setId(id);
        auditLog.setEventName(eventName);
        auditLog.setEventDescription(eventDescription);
        auditLog.setTimestamp(timestamp);
        auditLog.setAffectedResource(affectedResource);
        auditLog.setOrigin(origin);

        // Then
        assertEquals(user, auditLog.getUser());
        assertEquals(id, auditLog.getId());
        assertEquals(eventName, auditLog.getEventName());
        assertEquals(eventDescription, auditLog.getEventDescription());
        assertEquals(timestamp, auditLog.getTimestamp());
        assertEquals(affectedResource, auditLog.getAffectedResource());
        assertEquals(origin, auditLog.getOrigin());
    }

    @Test
    public void testAllArgsConstructor() {
        // Given
        Long id = faker.number().randomNumber();
        String eventName = faker.lorem().word();
        String eventDescription = faker.lorem().sentence();
        String affectedResource = faker.lorem().word();
        String origin = faker.lorem().word();

        // When
        AuditLog auditLog = new AuditLog(id, eventName, eventDescription, null, mockUser, affectedResource, origin);

        // Then
        assertEquals(id, auditLog.getId());
        assertEquals(eventName, auditLog.getEventName());
        assertEquals(eventDescription, auditLog.getEventDescription());
        assertEquals(affectedResource, auditLog.getAffectedResource());
        assertEquals(origin, auditLog.getOrigin());
    }

    @Test
    public void testBuilderAnnotation() {
        // Given
        Long id = faker.number().randomNumber();
        String eventName = faker.lorem().word();
        String eventDescription = faker.lorem().sentence();
        String affectedResource = faker.lorem().word();
        String origin = faker.lorem().word();
        User user = new User(); // Você pode criar um objeto User com Faker se quiser
        Date timestamp = new Date(); // Obtenha a data atual para testar o timestamp

        // When
        AuditLog auditLog = AuditLog.builder()
                .id(id)
                .eventName(eventName)
                .eventDescription(eventDescription)
                .user(user) // Testa o método user(User)
                .timestamp(timestamp) // Testa o método timestamp
                .affectedResource(affectedResource)
                .origin(origin)
                .build();

        // Then
        assertEquals(id, auditLog.getId());
        assertEquals(eventName, auditLog.getEventName());
        assertEquals(eventDescription, auditLog.getEventDescription());
        assertEquals(user, auditLog.getUser()); // Verifica se o user está correto
        assertEquals(timestamp, auditLog.getTimestamp()); // Verifica se o timestamp está correto
        assertEquals(affectedResource, auditLog.getAffectedResource());
        assertEquals(origin, auditLog.getOrigin());
    }

}



