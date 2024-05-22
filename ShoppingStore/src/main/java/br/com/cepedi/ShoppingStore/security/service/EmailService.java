package br.com.cepedi.ShoppingStore.security.service;


import br.com.cepedi.ShoppingStore.model.records.mail.input.DataRegisterMail;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.TokenRepository;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.mail.MailService;
import com.auth0.jwt.JWT;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MailService mailService;

    public void sendResetPasswordEmail(String name, String email, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Password Reset");

        // Processando o modelo HTML
        Map<String, Object> model = Map.of("token", token, "name", name);
        String htmlBody = processHtmlTemplate("reset_password_email_template.html", model);

        helper.setText(htmlBody, true);

        emailSender.send(message);
        DataRegisterMail dataRegisterMail = new DataRegisterMail(email,"shoppingstoreclient@gmail.com",htmlBody,"Password Reset");
        mailService.register(dataRegisterMail);
    }

    public String sendActivationEmail(String name, String email) throws MessagingException {

        User user = repository.findUserByEmail(email);

        String token = tokenService.generateTokenForActivatedEmail(user);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Activation Email");

        // Processando o modelo HTML
        Map<String, Object> model = Map.of("token", token, "name", name);
        String htmlBody = processHtmlTemplate("activate_user_by_email_template.html", model);

        helper.setText(htmlBody, true);
        emailSender.send(message);
        DataRegisterMail dataRegisterMail = new DataRegisterMail(email,"shoppingstoreclient@gmail.com",htmlBody,"Activation Email");
        mailService.register(dataRegisterMail);
        return token;
    }


    private String processHtmlTemplate(String templateName, Map<String, Object> model) throws MessagingException {
        try {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new MessagingException("Failed to process email template", e);
        }
    }
}