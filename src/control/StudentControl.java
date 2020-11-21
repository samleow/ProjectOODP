package control;

import java.io.*;
import java.util.*;
import java.util.Properties;
import control.*;
import entity.AllEnums.*;
import entity.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class StudentControl {
	
	public static Student studentInfo;
	
	public static void saveStudentInfo(String userName)
	{
		for(int i = 0; i < Container.studentList.size(); i++) {
			if(Container.studentList.get(i).getUserName().equals(userName)) {
				studentInfo = Container.studentList.get(i);
			}
		}
	}

	public static void addCourse(int indexno)
	{
		//validation of checking only integer 

		boolean waitingList = false;
		CoursePlan tempcourseplan = null;
	
			//Get the user input of indexno information
			for(int i = 0; i < Container.coursePlanList.size(); i++) {
				if(Container.coursePlanList.get(i).getIndex() == indexno) {
					tempcourseplan = Container.coursePlanList.get(i);
					break;
				}
			}

			//Check for timetable clash
			if(!timetableClash(waitingListCourses(), null, tempcourseplan)) {
				if(!timetableClash(studentInfo.getCoursePlan(), null, tempcourseplan)) {
				//Add Student Matric No into the CourseSlots slotList
					for (int k=0;k < Container.courseSlotsList.size();k++)
					{
						if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
							//If the Course got no vacancy, put the Student on Waiting List
							if(Container.courseSlotsList.get(k).getSlotList().size()<Container.courseSlotsList.get(k).getTotalSlots()) {
								Container.courseSlotsList.get(k).getSlotList().add(studentInfo.getMatricNo());
								break;
							}
							else {
								Container.courseSlotsList.get(k).getWaitingList().add(studentInfo.getMatricNo());
								System.out.println("No Vacancy Available. You will be put on the Waiting List \n");
								waitingList = true;
								break;
							}
						}
					}
					//Overwrite the latest data into the CourseSlots.txt
					Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
					
	
					//If not in waitingList then add the Course in the Student Class CoursePlan List
					if(!waitingList) {
						
						//Adding the Index No
			    		for (int k=0;k < Container.coursePlanList.size();k++)
			    		{
							if(Container.coursePlanList.get(k).getIndex() == indexno) {
								//It is link and reference
								studentInfo.getCoursePlan().add(Container.coursePlanList.get(k));
								//Overwrite the latest data into the StudentAccount.txt
								Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
								break;
							}
			    		}
			    		System.out.println("Successfully Added the Course \n");
					}
				}
				else {
					System.out.println("Current TimeTable Clashes. Failed to add the Course.\n");
				}
			} else {
				System.out.println("Current courses in the waiting list clashes. Failed to add the Course.\n");
			}
		}

	
	
	public static void dropCourse(int indexno)
	{
		CoursePlan tempCoursePlan = null;
		boolean addStudents = true;
		boolean removeMatricNoInWaitingList = false; 
		
			for(int i = 0; i < StudentControl.studentInfo.getCoursePlan().size(); i++) {
				if(StudentControl.studentInfo.getCoursePlan().get(i).getIndex() == indexno) {
					tempCoursePlan = StudentControl.studentInfo.getCoursePlan().get(i);
					break;
				}
			}
			
			if(waitingListCourses().size() != 0) {
				for(int y = 0; y < Container.courseSlotsList.size(); y++) {
					if(Container.courseSlotsList.get(y).getCoursePlan().getIndex() == indexno) {
						Container.courseSlotsList.get(y).getWaitingList().remove(studentInfo.getMatricNo());
						Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
						removeMatricNoInWaitingList = true;		
					}
				}
			}
			
			if(!removeMatricNoInWaitingList) {
		
			//Remove CoursePlan from List<CoursePlan> in Student class
    		for (int k=0;k < Container.coursePlanList.size();k++)
    		{
				if(Container.coursePlanList.get(k).getIndex() == indexno) {
					studentInfo.getCoursePlan().remove(Container.coursePlanList.get(k));
					Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
					break;
				}
    		}
    		
    		//Remove Student Matric No from the CourseSlots slotList
    		for (int k=0;k < Container.courseSlotsList.size();k++)
    		{
    			if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
					Container.courseSlotsList.get(k).getSlotList().remove(studentInfo.getMatricNo());
					Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
					break;
    			}
    		}
    		

    		for (int i=0; i < Container.courseSlotsList.size(); i++)
			{
				//Find the courseSlots based on indexno
    			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == indexno) {
    				//check the first student first, if failed, then check another and so on 
    				while(addStudents) {
    					//Check if got vacancy, if have then check waiting list got student a not
						if(Container.courseSlotsList.get(i).getSlotList().size()<Container.courseSlotsList.get(i).getTotalSlots()) {
							if(Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
									String student = "";
						    		Student tempstudent = null;
						    		boolean timetableClash = false;
									
						    		//get the student in the waiting list
									student = Container.courseSlotsList.get(i).getWaitingList().get(0);
									
									for(int z = 0; z < Container.studentList.size(); z++) {
										if(Container.studentList.get(z).getMatricNo().equals(student)) {
											tempstudent = Container.studentList.get(z);
											break;
										}
									}

									if(timetableClash(tempstudent.getCoursePlan(), null, tempCoursePlan)) {
										Container.courseSlotsList.get(i).getWaitingList().remove(0);
										Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
										System.out.println("Sending email........");
										EmailNotification.getInstance().sendNotification(tempstudent, Notification.createMessage(tempstudent.getName(), tempCoursePlan.getCourseID(), false));
										System.out.println("Sent successfully");
										System.out.println("Removed students from waiting list as his current timetable clashes.");
									}
									else {
										//remove student from the waiting list and add them
						    			for (int k=0;k < Container.courseSlotsList.size();k++)
										{
						    				if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
												Container.courseSlotsList.get(k).getSlotList().add(student);
												Container.courseSlotsList.get(k).getWaitingList().remove(0);
												Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
												break;
											}
										}
						    			
						    			//add index no into List<CoursePlan> in Student class
						    			for (int k=0;k < Container.coursePlanList.size();k++)
							    		{
											if(Container.coursePlanList.get(k).getIndex() == indexno) {
												tempstudent.getCoursePlan().add(Container.coursePlanList.get(k));
												Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
												System.out.println("Sending email........");
												EmailNotification.getInstance().sendNotification(tempstudent, Notification.createMessage(tempstudent.getName(), tempCoursePlan.getCourseID(), true));
												System.out.println("Sent successfully");
												System.out.println("Successfully added student into the Course and send email to notify him/her.");
												break;
											}
							    		}
									}
								}
							//break while loop if no students in the waiting list
							else addStudents = false;
							
						}
						// break while loop if the slot list is full
						else addStudents = false;
					}
    			}
				
    			}
			}
    		System.out.println("Successfully Removed the Course. \n");
		}
	

	public static void displayCourse()
	{
		CourseType courseType = CourseType.DEFAULT;
		int AU = -1;
		System.out.println("Display Current Courses you have registered");
		
		//Need to do like this as studentInfo.getCoursePlan().get(i).getCourse() give me NULL
		for(int i = 0; i < studentInfo.getCoursePlan().size(); i++) 
		{
			for(int j = 0; j < Container.courseList.size(); j++) 
			{
				if(studentInfo.getCoursePlan().get(i).getCourseID().equals(Container.courseList.get(i).getCourseID()))
				{ 
					AU = Container.courseList.get(i).getCourseAU();
					courseType = Container.courseList.get(i).getCourseType();
					break;
				}
			}
			System.out.println("CourseID: " + studentInfo.getCoursePlan().get(i).getCourseID() +  
					" | AU: " + AU  + " | Course Type: " + courseType + 
					" | Index No: " + studentInfo.getCoursePlan().get(i).getIndex() + "\n");
		}
	}
	
	public static boolean timetableClash(List<CoursePlan> s, CoursePlan oldCP, CoursePlan newCP)
	{
		Lesson l1,l2;
		for(int i=0;i<s.size();i++)
		{
			if(s.get(i).equals(oldCP))
				continue;
			
			for(int j=0;j<s.get(i).getLessons().size();j++)
			{
				l1 = s.get(i).getLessons().get(j);
				for(int k=0;k<newCP.getLessons().size();k++)
				{
					l2 = newCP.getLessons().get(k);
					if(l1.clashWith(l2))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
//			//Get the user input of indexno information
//	for(int i = 0; i < Container.coursePlanList.size(); i++) {
//		if(Container.coursePlanList.get(i).getIndex() == indexno) {
//			tempcourseplan = Container.coursePlanList.get(i);
//			break;
//		}
//	}
	
	//Check if user is in waiting list courses
//	outerloop:
//	for (int i = 0; i < Container.courseSlotsList.size(); i++ ) {
//		//Get specify index
//		if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == indexno) {
//			//Check if the waiting list got students a not
//			if(Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
//				//Loop through the waiting list
//				for(int y = 0; y < Container.courseSlotsList.get(i).getWaitingList().size(); y++) {
//					//Check if student matric no matches with the ones in the waiting list
//					if(studentInfo.getMatricNo().equals(Container.courseSlotsList.get(i).getWaitingList().get(y))) {
//						System.out.println("You are already in the waiting list. \n");
//						insideWaitingList = true;
//						break outerloop; 
//					}
//				}
//			}
//		}
//	}
	
	public static List<CoursePlan> waitingListCourses() {
		List<CoursePlan> waitingList = new ArrayList<CoursePlan>();
		
		for (int i = 0; i < Container.courseSlotsList.size(); i++ ) {
			if(Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
				for(int y = 0; y < Container.courseSlotsList.get(i).getWaitingList().size(); y++) {
//					//Check if student matric no matches with the ones in the waiting list
					if(studentInfo.getMatricNo().equals(Container.courseSlotsList.get(i).getWaitingList().get(y))) {
						waitingList.add(Container.courseSlotsList.get(i).getCoursePlan());
					}
				}
			}
		}
		return waitingList;
	}
	
	public static void displayCurrentAndWaitingCourses() {
		
		System.out.println("Display Current Courses you have took");
		for(int i = 0; i < studentInfo.getCoursePlan().size(); i++){
			System.out.println("CourseID: " + studentInfo.getCoursePlan().get(i).getCourseID() + " | " + "IndexNo: " + studentInfo.getCoursePlan().get(i).getIndex());
		}
		System.out.println("");
		
		System.out.println("Display Waiting List Courses");
		if (waitingListCourses().size() == 0) {
			System.out.println("No courses in waiting list");
		} else {
			for(int i = 0; i < waitingListCourses().size(); i++){
				System.out.println("CourseID: " + waitingListCourses().get(i).getCourseID() + " | " + "IndexNo: " + waitingListCourses().get(i).getIndex());
			}
		}
		System.out.println("");
	}
	
	
	
	
	
//	public static void sendEmail(String name, String CourseID, String email, boolean add) {
//		String sendMessage = "";
//		final String username = "ntu20.cz2002@gmail.com"; // need to be added
//		final String password = "cz2002@ntu"; // need to be added
//		
//		if(add) {
//			sendMessage = "Dear " + name + ","
//					+ "\n\n You have been notified that this course " + CourseID + " has been successfully added into your STARS Planner."
//					+ "\n\n (This is an auto-generated email. Please do not reply directly to this email.)"
//					+ "\n_________________________________________________________________________________________________"
//					+ "\nCONFIDENTIALITY: This email is intended solely for the person(s) named and may be confidential and/or privileged. "
//					+ "If you are not the intended recipient, please delete it, notify us and do not copy, use, or disclose its contents.";
//		} else {
//			sendMessage = "Dear " + name + ","
//					+ "\n\n You have been notified that this course " + CourseID + " is currently clashing with your current other modules. You will be removed from the waiting list."
//					+ "\n\n (This is an auto-generated email. Please do not reply directly to this email.)"
//					+ "\n_________________________________________________________________________________________________"
//					+ "\nCONFIDENTIALITY: This email is intended solely for the person(s) named and may be confidential and/or privileged. "
//					+ "If you are not the intended recipient, please delete it, notify us and do not copy, use, or disclose its contents.";
//		}
//
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "587");
//
//		Session session = Session.getInstance(props,
//		  new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		  });
//
//		try {
//
//			Message message = new MimeMessage(session);
//			//message.setFrom(new InternetAddress("do-not-reply@gmail.com"));
//			message.setRecipients(Message.RecipientType.TO,
//				InternetAddress.parse(email)); // to be added an email address
//			message.setSubject("STARS Planner Notification");
//			message.setText(sendMessage);
//
//			Transport.send(message);
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
}
