package org.tfg.spring.Vape.parteDos;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.repositories.IUserService;

@Component
public class RegistrationListener implements 
  ApplicationListener<OnRegistrationCompleteEvent> {
 
    @Autowired
    private IUserService service;

 
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        
        String recipientAddress = user.getMail();
        String subject = "Registration Confirmation";
        String confirmationUrl 
          = event.getAppUrl() + "/registroConfirmado?token=" + token;
        String message ="Confirma tu registro pulsando en el siguiente enlace."/* messages.getMessage("message.regSucc", null, event.getLocale())*/;
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://museovape.up.railway.app:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
