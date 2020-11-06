package boundary;
import java.io.IOException;
import java.util.Scanner;

import entity.LoginAccount;
import control.Container;
import control.HashingPassword;

public class UserLogin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try
		{
			Container.readLessonFile(Container.LESSON_FILE, Container.lessonList);
			Container.readCourseFile(Container.COURSE_FILE, Container.courseList);
			Container.readCoursePlanFile(Container.COURSEPLAN_FILE, Container.coursePlanList);
			Container.readCourseSlotsFile(Container.COURSESLOT_FILE, Container.courseSlotsList);
			Container.readStudentFile(Container.STUDENT_FILE, Container.studentList);
			Container.readAdminFile(Container.ADMIN_FILE, Container.adminList);
		
			// read all other files
		}catch(IOException e)
		{
			System.out.println("Reading of datafiles failed!");
		}
		
		// for checking
//		for (int i=0;i<Container.studentList.size();i++)
//		{
//			System.out.println(Container.studentList.get(i));
//			System.out.println(Container.studentList.get(i).getAccessPeriod());
//			System.out.println(Container.studentList.get(i).getAccessDate());
//		}
		

		int choice;
		boolean run = true;
		String userName, password;

		System.out.println("Welcome to My Student Automated Registration System (MySTARS)");
		
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("(1) Student Login");
			System.out.println("(2) Admin Login");
			System.out.println("(3) Exit");
			System.out.print("Enter the your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) Student Login*/
					
					System.out.print("Enter your StudentID: ");
					userName = sc.next(); 
					
					System.out.print("Enter your Password: ");
					
					if(Container.DEBUG_MODE)
						password = sc.next();
					else
					{
						// For masking password.
						char[] passMask = System.console().readPassword(); 
						password = new String(passMask);
					}

					password = HashingPassword.encryptThisString(password);
					
					if(LoginAccount.getFileInfo(userName, password))
					{
						// After login successful
						StudentUI.studentLogin(userName);
					}
					else
					{
						System.out.println("You have enter the wrong Username or Password");
					}
					break;
				case 2: /* (2) Admin Login*/

					// After login successful
					AdminUI.adminLogin();
					break;
				case 3: /* (3) Exit*/
					System.out.println("Quit...");
					run = false;
					break;

				default:
					System.out.println("Please Enter Choice from 1 to 3");
				}
			} else {
				System.out.println("Please Enter Choice from 1 to 3");
				// Clear sc
				sc.next();
			}
		} while (run);

	}



}
