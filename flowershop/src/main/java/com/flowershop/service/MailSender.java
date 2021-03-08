package com.flowershop.service;

import com.flowershop.model.Order;
import com.flowershop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class MailSender {
    private static final String MAIN_MESSAGE = "Hello, %s! \n" +
            "Welcome to My FlowerShop. Please, visit next link: http://localhost:8080/login?token=%s \n" +
            "Your new password is %s.";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public static String messageForget(String username, String token, String password) {
        return String.format(MAIN_MESSAGE + " Change your password in your account, if you wish",
                username,
                token,
                password
        );
    }

    public static String messageCreateUser(String username, String token, String password) {
        return String.format(MAIN_MESSAGE,
                username,
                token,
                password
        );
    }

    public MimeMessagePreparator constructOrderConfirmationEmail(User user, Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        if (order.isCash()) {
            context.setVariable("cash", true);
        } else {
            context.setVariable("cash", false);
        }
        context.setVariable("user", user);
        context.setVariable("orderFlowerList", order.getOrderFlower());
        context.setLocale(Locale.ENGLISH);
        String text = templateEngine.process("orderConfirmationEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getEmail());
                email.setSubject("Order confirmation - " + order.getId());
                email.setText(text, true);
                email.setFrom(username);
            }
        };
        return messagePreparator;
    }

    public void sendMimeMessage(MimeMessagePreparator mimeMessagePreparator) {
        mailSender.send(mimeMessagePreparator);
    }
}
