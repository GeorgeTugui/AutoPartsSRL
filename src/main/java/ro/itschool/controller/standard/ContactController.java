package ro.itschool.controller.standard;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Properties;

@Controller
public class ContactController {

  @RequestMapping(value = "/contact", method = RequestMethod.GET)
  public String contactForm(Model model) {
    model.addAttribute("contactForm");
    return "contact";
  }

  @RequestMapping(value = "/contact", method = RequestMethod.POST)
  public String sendEmail(@RequestParam("Name") String name,
                          @RequestParam("Email") String email,
                          @RequestParam("Message") String message,
                          Model model) {

    String to = "scautopartss@gmail.com";
    String from = email;
    String host = "smtp.gmail.com";
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.smtp.port", "587");
    properties.setProperty("mail.smtp.username", "scautopartss@gmail.com");
    properties.setProperty("mail.smtp.password", "cpcmvzxhzoxwspwj");
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("scautopartss@gmail.com", "cpcmvzxhzoxwspwj");
      }
    });
    try {
      MimeMessage mimeMessage = new MimeMessage(session);
      mimeMessage.setFrom(new InternetAddress(from));
      mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      mimeMessage.setSubject("New Contact Request from " + name);
      mimeMessage.setText(message);
      Transport.send(mimeMessage);
      model.addAttribute("message", "Your message has been sent.");
    } catch (MessagingException ex) {
      model.addAttribute("error", "There was an error sending your message. Please try again.");
      ex.printStackTrace();
    }

    return "contact";
  }

}
