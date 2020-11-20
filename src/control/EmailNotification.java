package control;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.Student;

public class EmailNotification extends Notification {
	private static EmailNotification _instance = null;
	
	private EmailNotification() {
	}
	
	public static EmailNotification getInstance() 
    { 
        if (_instance == null) 
        	_instance = new EmailNotification(); 
  
        return _instance; 
    } 
	
	public boolean sendNotification(Student student, String sendMessage) {
		final String username = "ntu20.cz2002@gmail.com"; // need to be added
		final String password = "cz2002@ntu"; // need to be added

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			Message message = new MimeMessage(session);
			//message.setFrom(new InternetAddress("do-not-reply@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(student.getEmail())); // to be added an email address
			message.setSubject("STARS Planner Notification");
			message.setText(sendMessage);

			Transport.send(message);
			
		} catch (MessagingException e) {
			return false;
		}
		
		return true;
	}
	
}

