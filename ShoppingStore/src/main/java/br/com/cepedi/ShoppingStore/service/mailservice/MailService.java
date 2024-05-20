package br.com.cepedi.ShoppingStore.service.mailservice;

import br.com.cepedi.ShoppingStore.security.model.entitys.Mail;
import br.com.cepedi.ShoppingStore.model.records.mail.details.DataDetailsMail;
import br.com.cepedi.ShoppingStore.model.records.mail.input.DataRegisterMail;
import br.com.cepedi.ShoppingStore.repository.MailRepository;
import br.com.cepedi.ShoppingStore.service.mailservice.validations.mail.MailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Autowired
    private  MailRepository mailRepository;


 // Method to register a new email
    public DataDetailsMail register(DataRegisterMail dataRegisterMail) {
        Mail mail = new Mail(dataRegisterMail);
        Mail savedMail = mailRepository.save(mail);
        return new DataDetailsMail(savedMail);
    }

    // Method to list all emails on a page
    public Page<DataDetailsMail> listAll(Pageable pageable) {
        return mailRepository.findAll(pageable)
                             .map(DataDetailsMail::new);
    }

    // Method to find an email by ID
    public DataDetailsMail findById(Long id) {
        Mail mail = mailRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("Mail not found with ID: " + id));
        return new DataDetailsMail(mail);
    }

    // Method to find all emails by sender
    public List<DataDetailsMail> findByFrom(String from) {
        List<Mail> mails = mailRepository.findByFrom(from);
        return mails.stream().map(DataDetailsMail::new).collect(Collectors.toList());
    }
}

