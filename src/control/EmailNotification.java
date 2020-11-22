package control;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.Admin;
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
	
	public boolean sendNotification(Student student, String sendMessage)
	{
		ArrayList stringArray;
		try {
			stringArray = (ArrayList)Container.read(Container.SENDER_EMAIL_FILE);
		}
		catch(Exception e) {
			return false;
		}
		
		final String username = stringArray.get(0).toString(); // need to be added
		final String password = stringArray.get(1).toString(); // need to be added
		
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

