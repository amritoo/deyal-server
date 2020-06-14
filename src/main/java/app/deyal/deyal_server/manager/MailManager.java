package app.deyal.deyal_server.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MailManager {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }

    public void sendEmailWithAttachment(String to, String subject) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        // default = text/plain
        //messageHelper.setText("Check attachment for image!");
        // true = text/html
        messageHelper.setText("<h1>Check attachment for image!</h1>", true);
        messageHelper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
    }

}
