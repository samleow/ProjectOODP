package boundary;
import java.util.Scanner;

public class StudentUI {

	public static void studentLogin() {
		int choice;
		boolean run = true;

		System.out.println("Welcome (Stuent Name)");

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("(1) *Add Course");
			System.out.println("(2) Drop Course");
			System.out.println("(3) Check/Print Courses Registered");
			System.out.println("(4) Check Vacancies Available");
			System.out.println("(5) Change Index Number of Course");
			System.out.println("(6) Swop Index Number with Another Student");
			System.out.println("(7) Log Out");
			System.out.print("Enter the your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) *Add Course */

					break;
				case 2: /* (2) Drop Course */

					break;
				case 3: /* (3) Check/Print Courses Registered*/

					break;
				case 4: /* (4) Check Vacancies Available*/

					break;
				case 5: /* (5) Change Index Number of Course*/

					break;
				case 6: /* (6) Swop Index Number with Another Student*/

					break;
				case 7: /* (7) Log Out*/
					System.out.println("Log Out Student...");
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
