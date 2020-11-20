package control;

import entity.*;

public abstract class Notification {
	public String type;
	
	public abstract boolean sendNotification(Student student, String sendMessage);
	
	public static String createMessage(String name, String courseID, boolean add) {
		String sendMessage = "";
		if(add) {
			sendMessage = "Dear " + name + ","
					+ "\n\n You have been notified that this course " + courseID + " has been successfully added into your STARS Planner."
					+ "\n\n (This is an auto-generated email. Please do not reply directly to this email.)"
					+ "\n_________________________________________________________________________________________________"
					+ "\nCONFIDENTIALITY: This email is intended solely for the person(s) named and may be confidential and/or privileged. "
					+ "If you are not the intended recipient, please delete it, notify us and do not copy, use, or disclose its contents.";
		} else {
			sendMessage = "Dear " + name + ","
					+ "\n\n You have been notified that this course " + courseID + " is currently clashing with your current other modules. You will be removed from the waiting list."
					+ "\n\n (This is an auto-generated email. Please do not reply directly to this email.)"
					+ "\n_________________________________________________________________________________________________"
					+ "\nCONFIDENTIALITY: This email is intended solely for the person(s) named and may be confidential and/or privileged. "
					+ "If you are not the intended recipient, please delete it, notify us and do not copy, use, or disclose its contents.";
		}
		return sendMessage;

	}
}
