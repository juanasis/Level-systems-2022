package ar.edu.undec.level.service;

import ar.edu.undec.level.security.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${mail.urlFront}")
    private String urlFront;

    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDto.getMailFrom());
        message.setTo(emailDto.getMailTo());
        message.setSubject(emailDto.getSubject());
        message.setText("Para cambiar la contraseña del usuario "+emailDto.getMailTo()+" ingresa en el siguiente link: "+urlFront+emailDto.getToken());

        javaMailSender.send(message);
    }


}
