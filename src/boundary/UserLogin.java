package boundary;
import java.io.IOException;
import java.util.Scanner;

import test_cases.PreLoadedAccount;
import entity.LoginAccount;
import control.Container;
import control.HashingPassword;
import control.StudentControl;

public class UserLogin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try
		{
			Container.readLessonFile("Lesson.txt", Container.lessonList);
			Container.readCourseFile("Course.txt", Container.courseList);
			Container.readCoursePlanFile("CoursePlan.txt", Container.coursePlanList);
			Container.readCourseSlotsFile("CourseSlots.txt", Container.courseSlotsList);
			Container.readStudentFile("StudentAccount.txt", Container.studentList);
			Container.readAdminFile("Admin.txt", Container.adminList);
		
			// read all other files
		}catch(IOException e) {};
		
		// for checking
//		for (int i=0;i<Container.lessonList.size();i++)
//		{
//			System.out.println(Container.lessonList.get(i).getWeekly());
//			System.out.println(Container.lessonList.get(i).getCourseID());
//			System.out.println(Container.lessonList.get(i).getIsOnline());
//			System.out.println("\n");
//			
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
					password = sc.next(); // If want test password masking, comment this line
					
					// The bottom two lines is for masking password.
					// Can't work now cause IDE can't support System.console()
					//char[] passMask = System.console().readPassword(); 
					//password = new String(passMask);
					
					password = HashingPassword.encryptThisString(password);
					//System.out.println(password);
					
					if(LoginAccount.getFileInfo(userName, password))
					{
						// After login successful
						StudentControl.saveStudentInfo(userName);
						StudentUI.studentLogin();
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
