package br.com.cepedi.ShoppingStore.service.mailservice;

import br.com.cepedi.ShoppingStore.security.model.entitys.Mail;
import br.com.cepedi.ShoppingStore.model.records.mail.details.DataDetailsMail;
import br.com.cepedi.ShoppingStore.model.records.mail.input.DataRegisterMail;
import br.com.cepedi.ShoppingStore.repository.MailRepository;
import br.com.cepedi.ShoppingStore.service.mailservice.validations.mail.MailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailServiceTest {

    @Mock
    private MailRepository mailRepository;


    @InjectMocks
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_ValidData() {
        // Setup
        DataRegisterMail dataRegisterMail = new DataRegisterMail(1L, "from@example.com", "to@example.com", "Content", "Subject");
        Mail mail = new Mail(dataRegisterMail);
        when(mailRepository.save(any(Mail.class))).thenReturn(mail);

        // Test
        DataDetailsMail result = mailService.register(dataRegisterMail);

        // Verify
        assertNotNull(result);
        verify(mailRepository, times(1)).save(any(Mail.class));
    }

    @Test
    void testListAll_NoEmails() {
        // Setup
        Page<Mail> expectedPage = new PageImpl<>(Collections.emptyList());
        when(mailRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Test
        Page<DataDetailsMail> resultPage = mailService.listAll(Pageable.unpaged());

        // Verify
        assertTrue(resultPage.isEmpty());
        verify(mailRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testFindById_NonexistentEmail() {
        // Setup
        Long id = 1L;
        when(mailRepository.findById(id)).thenReturn(Optional.empty());

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> mailService.findById(id));
        verify(mailRepository, times(1)).findById(id);
    }

    @Test
    void testFindByFrom_NoEmailsFound() {
        // Setup
        String from = "from@example.com";
        when(mailRepository.findByFrom(from)).thenReturn(Collections.emptyList());

        // Test
        List<DataDetailsMail> resultList = mailService.findByFrom(from);

        // Verify
        assertTrue(resultList.isEmpty());
        verify(mailRepository, times(1)).findByFrom(from);
    }
}
