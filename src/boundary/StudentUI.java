package boundary;
import java.util.Scanner;
import control.StudentControl;

public class StudentUI {

	public static void studentLogin() {
		int choice, indexno;
		boolean run = true;

		System.out.println("Welcome " + StudentControl.studentInfo.getName());

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
					System.out.print("Enter the Index Number of the Course to add: ");
					indexno = sc.nextInt();
					StudentControl.addCourse(indexno);
					break;
					
				case 2: /* (2) *Drop Course */
					System.out.println("Display Current Courses you have took");
					for(int i = 0; i < StudentControl.studentInfo.getCoursePlan().size(); i++) 
					{
						System.out.println("CourseID: " + StudentControl.studentInfo.getCoursePlan().get(i).getCourseID() + " " + "IndexNo: " + StudentControl.studentInfo.getCoursePlan().get(i).getIndex());
					}
				
					System.out.print("Enter the Index Number of the Course to drop: ");
					indexno = sc.nextInt();
					StudentControl.dropCourse(indexno);
					break;
					
				case 3: /* (3) Check/Print Courses Registered*/
					StudentControl.displayCourse();

					break;
				case 4: /* (4) Check Vacancies Available*/

					break;
				case 5: /* (5) Change Index Number of Course*/

					break;
				case 6: /* (6) Swop Index Number with Another Student*/

					break;
				case 7: /* (7) Log Out*/
					StudentControl.studentInfo = null;
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
