package boundary;

import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDateTime;

import control.*;
import entity.*;

/**
 * The class handling the user login display menu.
 */
public class UserLogin {
	
	/**
	 * Private constructor of UserLogin.
	 */
	private UserLogin() {
	}

	/**
	 * The main program.
	 * 
	 * @param args An array of arguments.
	 */
	public static void main(String[] args) {

		try {
			Container.readLessonFile(Container.LESSON_FILE, Container.lessonList);
			Container.readCourseFile(Container.COURSE_FILE, Container.courseList);
			Container.readCoursePlanFile(Container.COURSEPLAN_FILE, Container.coursePlanList);
			Container.readCourseSlotsFile(Container.COURSESLOT_FILE, Container.courseSlotsList);
			Container.readStudentFile(Container.STUDENT_FILE, Container.studentList);
			Container.readAdminFile(Container.ADMIN_FILE, Container.adminList);

			// read all other files
		} catch (IOException e) {
			System.out.println("Reading of datafiles failed!");
		}

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
				System.out.println();
				switch (choice) {
				case 1: /* (1) Student Login */
					System.out.print("Enter your StudentID: ");
					userName = sc.next();

					System.out.print("Enter your Password: ");

					if (Container.DEBUG_MODE)
						password = sc.next();
					else {
						// For masking password.
						char[] passMask = System.console().readPassword();
						password = new String(passMask);
					}

					password = AccountControl.encryptThisString(password);

					System.out.println();
					if (AccountControl.accountLoginSuccess(userName, password, false)) {
						// After login successful
						System.out.println();

						StudentControl.saveStudentInfo(userName);

						// check access period with current time
						Date accDate = StudentControl.studentInfo.getAccessDate();
						Period accPeriod = StudentControl.studentInfo.getAccessPeriod();
						LocalDateTime now = LocalDateTime.now();
						Time currTime = new Time(now.getHour(), now.getMinute());
						if (!accDate.sameDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth())
								|| !accPeriod.withinPeriod(currTime)) {
							System.out.println("Not accessible now!");
							System.out.println("Please come back during your access period!\n");
							continue;
						}
						StudentUI.studentLogin();
					} else {
						System.out.println();
						System.out.println("You have enter the wrong Username or Password\n");
					}
					break;

				case 2: /* (2) Admin Login */

					sc.nextLine(); // Consume newline left-over

					System.out.print("Enter your Admin ID: ");
					userName = sc.next();

					System.out.print("Enter your Password: ");

					if (Container.DEBUG_MODE)
						password = sc.next();
					else {
						// For masking password.
						char[] passMask = System.console().readPassword();
						password = new String(passMask);
					}

					password = AccountControl.encryptThisString(password);

					System.out.println();
					if (AccountControl.accountLoginSuccess(userName, password, true)) {
						// After login successful
						System.out.println();
						AdminControl.saveAdminInfo(userName);
						AdminUI.adminLogin();
					} else {
						System.out.println();
						System.out.println("You have enter the wrong Username or Password\n");
					}

					break;
				case 3: /* (3) Exit */
					System.out.println("Quit...");
					run = false;
					break;

				default:
					System.out.println("Please Enter Choice from 1 to 3");
					System.out.println();
				}
			} else {
				System.out.println();
				System.out.println("Please Enter Choice from 1 to 3");
				// Clear sc
				sc.next();
				System.out.println();
			}
		} while (run);

	}

}
