package control;

import entity.*;

/**
 * An abstract class that stores the standard notification template.
 */
public abstract class Notification {
	/**
	 * The type of notification.
	 */
	public String type;

	/**
	 * Sends a notification to a given Student.
	 * 
	 * @param student     The Student to receive the notification.
	 * @param sendMessage The message to send.
	 * @return Returns the success of sending the notification.
	 */
	public abstract boolean sendNotification(Student student, String sendMessage);

	/**
	 * Creates a notification message with a default format. Default notification of
	 * Student successfully added into course.
	 * 
	 * @param name     Name of recipient.
	 * @param courseID The ID of the course.
	 * @param add      Set true if Student is successfully added into course, else
	 *                 set false.
	 * @return Returns the message of the notification.
	 */
	public static String createMessage(String name, String courseID, boolean add) {
		String sendMessage = "";
		if (add) {
			sendMessage = "Dear " + name + "," + "\n\n You have been notified that this course " + courseID
					+ " has been successfully added into your STARS Planner."
					+ "\n\n (This is an auto-generated email. Please do not reply directly to this email.)"
					+ "\n_________________________________________________________________________________________________"
					+ "\nCONFIDENTIALITY: This email is intended solely for the person(s) named and may be confidential and/or privileged. "
					+ "If you are not the intended recipient, please delete it, notify us and do not copy, use, or disclose its contents.";
		} else {
			sendMessage = "Dear " + name + "," + "\n\n You have been notified that this course " + courseID
					+ " is currently clashing with your current other modules. You will be removed from the waiting list."
					+ "\n\n (This is an auto-generated email. Please do not reply directly to this email.)"
					+ "\n_________________________________________________________________________________________________"
					+ "\nCONFIDENTIALITY: This email is intended solely for the person(s) named and may be confidential and/or privileged. "
					+ "If you are not the intended recipient, please delete it, notify us and do not copy, use, or disclose its contents.";
		}
		return sendMessage;

	}
}
