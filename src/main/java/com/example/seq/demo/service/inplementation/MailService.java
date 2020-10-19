//package com.example.seq.demo.service.inplementation;
//
//import com.example.seq.demo.service.IMailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailService implements IMailService {
//    private String username = "myProjectMailSender";
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//
//    @Override
//    public void send(String emailTo, String subject, String message) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom(this.username);
//        simpleMailMessage.setTo(emailTo);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(message);
//        this.javaMailSender.send(simpleMailMessage);
//    }
//}
