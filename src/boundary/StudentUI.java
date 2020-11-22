package boundary;
import java.util.Scanner;
import control.*;

public class StudentUI {

	public static void studentLogin() {
		int choice;
		boolean run = true;
		int index;

		System.out.println("Welcome " + StudentControl.studentInfo.getName());

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("(1) *Add Course");
			System.out.println("(2) Drop Course/Waiting List");
			System.out.println("(3) Check/Print Courses Registered");
			System.out.println("(4) Check Vacancies Available");
			System.out.println("(5) Change Index Number of Course");
			System.out.println("(6) Swap Index Number with Another Student");
			System.out.println("(7) Log Out");
			System.out.print("Enter choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) *Add Course */
					do {
						System.out.println("\n=== *Add Course ===");
						System.out.printf("Enter the Index Number of the Course to add (%d to return): ", Container.BREAK_MENU);
						if(sc.hasNextInt()) { 
							index = sc.nextInt();
							if(index == Container.BREAK_MENU) {
								break;
							}
							if(Validation.checkIfValidIndex(index)) {
								if(!Validation.checkIfStudentTookThisCourse(index)) {
									if(!Validation.checkIfCourseExempted(index)) {
										if(!Validation.checkIfStudentInCourseWaitingList(index)) {
											StudentControl.addCourse(index);
										break;
										} else {
											System.out.println("\nYou have already taken this course in the waiting list.");
										}
									} else {
										System.out.println("\nYou are exempted from this course.");
									}
								} else {
									System.out.println("\nYou have taken this course. Please enter another course index no.");
								}
							} else {
								System.out.println("\nIndex does not exist.");
							}
						} else {
							System.out.println("\n'" + sc.next() + "' is not a valid index. Please enter only integers.");
						}
					} while (true);
					break;

					
				case 2: /* (2) *Drop Course */
					do {
						System.out.println("\n=== Drop Course/Waiting List ===");
						StudentControl.displayCurrentAndWaitingCourses();
						System.out.printf("Enter the Index Number of the Course to drop (%d to return): ", Container.BREAK_MENU);
						if(sc.hasNextInt()) { 
							index = sc.nextInt();
							if(index == Container.BREAK_MENU) {
								break;
							}
							if(Validation.checkIfValidIndex(index)) {
								if(Validation.checkIfStudentTookThisIndex(index) || (Validation.checkIfStudentinIndexWaitingList(index))) {
									StudentControl.dropCourse(index);
									break;
								} else {
									System.out.println("\nIndex not found. Please select the index no. from above.");
								}
							}
							else {
								System.out.println("\nIndex does not exist.");
							}
						} else {
							System.out.println("\n'" + sc.next() + "' is not a valid index. Please enter only Integers.");
						}
					} while (true);
					break;
					
					
				case 3: /* (3) Check/Print Courses Registered*/
					System.out.println("\n=== Check/Print Courses Registered ===");
					StudentControl.displayCourse(StudentControl.studentInfo);

					break;
				case 4: /* (4) Check Vacancies Available*/
					StudentControl.checkVacanciesAvailableMenu(sc);
					break;
					
				case 5: /* (5) Change Index Number of Course*/
					StudentControl.changeIndexNoMenu(sc);
					break;
					
				case 6: /* (6) Swap Index Number with Another Student*/
					StudentControl.swapIndexMenu(sc);
					break;
					
				case 7: /* (7) Log Out*/
					StudentControl.studentInfo = null;
					System.out.println("\nLog Out Student...");
					run = false;
					break;
				default:
					System.out.println("\nPlease Enter Choice from 1 to 7");
				}
			} else {
				System.out.println("\nPlease Enter Choice from 1 to 7");
				sc.next();
			}
		} while (run);
	}
	
}
