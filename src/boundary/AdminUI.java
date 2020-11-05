package boundary;
import java.util.*;
import control.AdminControl;
import control.HashingPassword;

import entity.Course;
import entity.Period;

import entity.AllEnums.AccountType;
import entity.AllEnums.Gender;


public class AdminUI {

	static Gender gender = Gender.DEFAULT;
	public static void adminLogin()
	{
		int choice;
		boolean run = true;
		
		String matricNo;
		String name;
		//Gender gender;
		String nationality;
		int maxAU;
		//List<Course> courses;
		//Period accessPeriod;
		String password = "pass";
		String hashPassword;
		int index;
		String courseID;
		List<List<String>> stringList = new ArrayList<List<String>>();

		System.out.println("Welcome (Admin Name)");

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n(1) Edit student access period");
			System.out.println("(2) Add a student (matric, name, number, gender, nationality, Max AU, Password etc)");
			System.out.println("(3) Add/Update a course (course code, school, its index numbers and vacancy).");
			System.out.println("(4) Check available slot for an index number (vacancy in a class)");
			System.out.println("(5) Display student list by index number.");
			System.out.println("(6) Display student list by course (all students registered for the selected course)");
			System.out.println("(7) Log Out");
			System.out.print("Enter your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) Edit student access period */

					break;
				case 2: /* (2) Add a student (matric, name, number, gender, nationality, Max AU, Password  etc) */
					
					System.out.print("Enter new student matric number: ");
					matricNo = sc.next();
					
					System.out.print("Enter new student name: ");
					name = sc.next();
					
					System.out.print("Enter new student gender M/F: ");
					if(sc.next().equals("M")) {
						gender = Gender.MALE;
					} else {
						gender = Gender.FEMALE;
					}
					
					System.out.print("Enter new student nationality: ");
					nationality = sc.next();
					
					System.out.print("Set new student maxAU: ");
					while(true)
					{
						if(sc.hasNextInt())
						{
							maxAU = sc.nextInt();
							break;
						}
						else
						{
							
						}
					}
					hashPassword = HashingPassword.encryptThisString(password);
					
//					AdminControl.createStudAcc(name, matricNo,
//							hashPassword, AccountType.STUDENT,
//							matricNo, gender, nationality, maxAU );

					break;
				case 3: /* (3) Add/Update a course (course code, school, its index numbers and vacancy).*/

					break;
				case 4: /* (4) Check available slot for an index number (vacancy in a class)*/
					System.out.println("\nEnter the index number for the Course you would like to check: ");
					if(sc.hasNextInt()) { // weird error bug here
						index = sc.nextInt();
						if(AdminControl.checkIfValidIndex(index)) {
							int availableSlots = AdminControl.checkNoOfSlotsByCourseIndex(index);
							//System.out.println("There are " + availableSlots + " available slots for index " + index + "\n");
							System.out.println("\nIndex: " + index);
							System.out.println("Available Slots: " + availableSlots + "\n");
						} else {
							System.out.println("\nIndex does not exist currently.");
						}
					} else {
						System.out.println("\n'" + sc.next() + "' is not a valid index. Please enter only Integers.");
					}
					break;
				case 5: /* (5) Display student list by index number.*/
					System.out.println("\nEnter the index number: ");
					if(sc.hasNextInt()) {
						index = sc.nextInt();
						
						if(AdminControl.checkIfValidIndex(index)) {
							stringList = AdminControl.getStudentListByCourseIndex(index);
							//System.out.println(stringList);
							if(stringList.isEmpty()) {
								System.out.println("\nNo student registered in this index.");
							} else {
								for(int i = 0;i<stringList.size();i++) {
									System.out.println("\nStudent " + (i+1));
									System.out.println("Name: " + stringList.get(i).get(0));
									System.out.println("Gender: " + stringList.get(i).get(1));
									System.out.println("Nationality: " + stringList.get(i).get(2));
								}
							}
							
						} else {
							System.out.println("\nIndex does not exist.");
						}
						
					} else {
						System.out.println("\n'" + sc.next() + "' is not a valid index. Please enter only Integers.");
					}					
					break;
				case 6: /* (6) Display student list by course (all students registered for the selected course)*/
					System.out.println("Enter the Course ID (e.g. CZ2002, CZ2003,...): ");
					courseID = sc.next();
					stringList = AdminControl.getStudentListByCourseID(courseID);
					
					if(AdminControl.checkIfValidCourseID(courseID)) {
						if(stringList.isEmpty()) {
							System.out.println("\nNo student registered in this course.");
						} else {
							for(int i = 0;i<stringList.size();i++) {
								System.out.println("\nStudent " + (i+1));
								System.out.println("Name: " + stringList.get(i).get(0));
								System.out.println("Gender: " + stringList.get(i).get(1));
								System.out.println("Nationality: " + stringList.get(i).get(2));
							}
						}
					} else {
						System.out.println("\nCourse does not exist.");
					}
					
					break;
				case 7: /* (7) Log Out*/
					System.out.println("Log Out Admin...");
					run = false;
					break;

				default:
					System.out.println("Please Enter Choice from 1 to 7");
				}
			} else {
				System.out.println("Please Enter Choice from 1 to 7");
				// Clear sc
				sc.next();
			}
		} while (run);
	}

}
