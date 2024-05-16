package br.com.cepedi.ShoppingStore.model.entitys;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity doctor")
class MailTest {

	 @Test
	    void testMailObjectCreation() {
	        // Arrange
	        Long id = 1L;
	        String from = "test@example.com";
	        String to = "recipient@example.com";
	        String content = "This is a test email.";
	        String subject = "Test Subject";

	        // Act
	        Mail mail = new Mail(id, from, to, content, subject);

	        // Assert
	        assertEquals(id, mail.getId());
	        assertEquals(from, mail.getFrom());
	        assertEquals(to, mail.getTo());
	        assertEquals(content, mail.getContent());
	        assertEquals(subject, mail.getSubject());
	    }

	    @Test
	    void testMailObjectCreationWithNullValues() {
	        // Arrange
	        Long id = null;
	        String from = null;
	        String to = null;
	        String content = null;
	        String subject = null;

	        // Act
	        Mail mail = new Mail(id, from, to, content, subject);

	        // Assert
	        assertNull(mail.getId());
	        assertNull(mail.getFrom());
	        assertNull(mail.getTo());
	        assertNull(mail.getContent());
	        assertNull(mail.getSubject());
	    }
}
