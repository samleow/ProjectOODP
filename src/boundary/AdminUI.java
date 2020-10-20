package boundary;
import java.util.Scanner;

public class AdminUI {

	
	public static void adminLogin()
	{
		int choice;
		boolean run = true;

		System.out.println("Welcome (Admin Name)");

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("(1) Edit student access period");
			System.out.println("(2) Add a student (name, matric number, gender, nationality, etc)");
			System.out.println("(3) Add/Update a course (course code, school, its index numbers and vacancy).");
			System.out.println("(4) Check available slot for an index number (vacancy in a class)");
			System.out.println("(5) Display student list by index number.");
			System.out.println("(6) Display student list by course (all students registered for the selected course)");
			System.out.println("(7) Log Out");
			System.out.print("Enter the your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) Edit student access period */

					break;
				case 2: /* (2) Add a student (name, matric number, gender, nationality, etc) */

					break;
				case 3: /* (3) Add/Update a course (course code, school, its index numbers and vacancy).*/

					break;
				case 4: /* (4) Check available slot for an index number (vacancy in a class)*/

					break;
				case 5: /* (5) Display student list by index number.*/

					break;
				case 6: /* (6) Display student list by course (all students registered for the selected course)*/

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
