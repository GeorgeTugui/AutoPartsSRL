package ro.itschool.service.email;

public interface EmailSender {

    void sendEmail(String toEmail, String subject, String body);

}