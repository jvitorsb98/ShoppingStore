package br.com.cepedi.ShoppingStore.model.entitys;

import static org.junit.jupiter.api.Assertions.*;

import br.com.cepedi.ShoppingStore.model.records.mail.input.DataRegisterMail;
import br.com.cepedi.ShoppingStore.security.model.entitys.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Mail")
public class MailTest {

	@DisplayName("Test mail object creation")
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

	@DisplayName("Test mail object creation with null values")
	@Test
	void testMailObjectCreationWithNullValues() {
		// Act
		Mail mail = new Mail(null, null, null, null, null);

		// Assert
		assertNull(mail.getId());
		assertNull(mail.getFrom());
		assertNull(mail.getTo());
		assertNull(mail.getContent());
		assertNull(mail.getSubject());
	}

	@DisplayName("Test of Mail entity sets")
	@Test
	void testOfMailEntitySets() {
		// Arrange
		Long id = 1L;
		String from = "test@example.com";
		String to = "recipient@example.com";
		String content = "This is a test email.";
		String subject = "Test Subject";

		// Act
		Mail mail = new Mail();
		mail.setId(id);
		mail.setFrom(from);
		mail.setTo(to);
		mail.setContent(content);
		mail.setSubject(subject);

		// Assert
		assertEquals(id, mail.getId());
		assertEquals(from, mail.getFrom());
		assertEquals(to, mail.getTo());
		assertEquals(content, mail.getContent());
		assertEquals(subject, mail.getSubject());
	}

	@DisplayName("Test mail object creation with DataRegisterMail")
	@Test
	void testMailObjectCreationWithDataRegisterMail() {
		// Arrange
		Long id = 1L;
		String from = "test@example.com";
		String to = "recipient@example.com";
		String content = "This is a test email.";
		String subject = "Test Subject";

		// Act
		DataRegisterMail dataRegisterMail = new DataRegisterMail(id, from, to, content, subject);
		Mail mail = new Mail(dataRegisterMail);

		// Assert
		assertEquals(from, mail.getFrom());
		assertEquals(to, mail.getTo());
		assertEquals(content, mail.getContent());
		assertEquals(subject, mail.getSubject());
	}
}
