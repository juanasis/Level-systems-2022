package ar.edu.undec.level.service;

import ar.edu.undec.level.security.dto.EmailDto;
import ar.edu.undec.level.storage.entity.ItemPedido;
import ar.edu.undec.level.storage.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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

    public void enviarComprobante(String email, Pedido pedido) {
        String host = "localhost";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("levelsystems23@gmail.com","pcqdyzjiwnnfaaph");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            Address addressTo = new InternetAddress(email);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setSubject("Comprobante de pedido");

            StringBuilder listaDePedido = new StringBuilder();
            double total = 0;
            for(ItemPedido item: pedido.getItemsList()) {
                listaDePedido.append("<p><strong>x</strong>").append(item.getCantidad()).append("  ->  ").append(item.getProducto().getNombre()).append("</p>");
                total = total + item.getProducto().getPrecio() * item.getCantidad();
            }

            listaDePedido.append("<hr></hr><h3>Total").append("$").append(total).append("</h3>");
            message.setContent("<h1>Lista de items de tu pedido</h1><hr></hr>"+listaDePedido,"text/html");

            Transport.send(message);
        } catch(MessagingException a) {
            a.printStackTrace();
        }
    }


}
