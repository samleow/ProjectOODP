package boundary;
import java.util.Scanner;
import control.*;
import entity.*;

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
			System.out.println("(6) Swop Index Number with Another Student");
			System.out.println("(7) Log Out");
			System.out.print("Enter choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) *Add Course */
					do {
						System.out.println("\n(1) *Add Course");
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
											System.out.println("You have already take this course in the waiting list. \n");
										}
									} else {
										System.out.println("You are exempted from this Course.");
									}
								} else {
									System.out.println("You have take this course. Please enter other course Index No.");
								}
							} else {
								System.out.println("Index does not exist.");
							}
						} else {
							System.out.println("'" + sc.next() + "' is not a valid index. Please enter only Integers.");
						}
					} while (true);
					break;

					
				case 2: /* (2) *Drop Course */
					do {
						System.out.println("\n(2) Drop Course/Waiting List");
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
									System.out.println("Index not found. Please enter Index No. above.\n");
								}
							}
							else {
								System.out.println("Index does not exist.\n");
							}
						} else {
							System.out.println("'" + sc.next() + "' is not a valid index. Please enter only Integers.\n");
						}
					} while (true);
					break;
					
					
				case 3: /* (3) Check/Print Courses Registered*/
					System.out.println("\n(3) Check/Print Courses Registered");
					System.out.println("Display Current Courses you have registered");
					StudentControl.displayCourse(StudentControl.studentInfo);

					break;
				case 4: /* (4) Check Vacancies Available*/
					checkVacanciesAvailableMenu(sc);
					break;
					
				case 5: /* (5) Change Index Number of Course*/
					changeIndexNoMenu(sc);
					break;
					
				case 6: /* (6) Swop Index Number with Another Student*/
					swopIndexMenu(sc);
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
			System.out.println();
		} while (run);
	}
	
	private static void checkVacanciesAvailableMenu(Scanner sc)
	{
		int indexno, v;
		CourseSlots cs = null;
		while(true)
		{
			indexno = -1;
			System.out.println();
			System.out.println("(4) Check Vacancies Available");
			System.out.printf("Enter course index no. to check (%d to return): ",Container.BREAK_MENU);
			if (sc.hasNextInt())
			{
				indexno = sc.nextInt();
				if(indexno==Container.BREAK_MENU)
					break;
				
				cs = Container.getCourseSlotByIndex(indexno);
				if(cs == null)
				{
					System.out.printf("No course index no. %d found!\n",indexno);
					continue;
				}
				v = cs.getTotalSlots()-cs.getSlotList().size();
				System.out.printf("The vacancies available for %d are : %d\n",indexno,v);
				System.out.printf("The number of students in waiting list for %d are : %d\n",indexno,cs.getWaitingList().size());
				break;
			}
			else
			{
				System.out.println("Invalid input for course index no.!");
				sc.next();
			}
		}
	}
	
	private static void changeIndexNoMenu(Scanner sc)
	{
		int indexno, cIndex;
		CoursePlan currCP;
		CourseSlots newCS;
		outerloop:
		while(true) {
			indexno = -1;
			cIndex = -1;
			System.out.println();
			System.out.println("(5) Change Index Number of Course");
			StudentControl.displayCourse(StudentControl.studentInfo);
			System.out.printf("Enter course index no. to change (%d to return): ",Container.BREAK_MENU);
			if (sc.hasNextInt())
			{
				cIndex = sc.nextInt();
				if(cIndex==Container.BREAK_MENU)
					break;
				
				currCP = Container.getCoursePlanByIndex(cIndex, StudentControl.studentInfo.getCoursePlan());
				if(currCP==null)
				{
					System.out.println();
					System.out.println("Course index no. not registered!");
					continue;
				}
				
				while(true)
				{
					System.out.println();
					System.out.printf("Enter new course index no. (%d to return): ",Container.BREAK_MENU);
					if (sc.hasNextInt())
					{
						indexno = sc.nextInt();
						if(indexno==Container.BREAK_MENU)
							break;
						System.out.println();
						
						if(currCP.getIndex()==indexno)
						{
							System.out.println("Course index no. entered is same as old one!");
							continue;
						}
						
						newCS = Container.getCourseSlotByIndex(indexno);						
						if(newCS==null)
						{
							System.out.println("Course index no. not found!");
							continue;
						}
						if(!newCS.getCoursePlan().getCourseID().equals(currCP.getCourseID()))
						{
							System.out.println("Course index no. does not match with course!");
							continue;
						}
						if(newCS.getTotalSlots() == newCS.getSlotList().size())
						{
							System.out.println("No vacancy for new course index!");
							continue;
						}
						if(StudentControl.timetableClash(StudentControl.studentInfo.getCoursePlan(), currCP, newCS.getCoursePlan()))
						{
							System.out.println("Course index no. clashes with timetable!");
							continue;
						}
						if(StudentControl.timetableClash(StudentControl.waitingListCourses(StudentControl.studentInfo), currCP, newCS.getCoursePlan()))
						{
							System.out.println("Current courses in the waiting list clashes. Failed to add the Course.");
							continue;
						}
						
						// student update
						StudentControl.studentInfo.getCoursePlan().remove(currCP);
						StudentControl.studentInfo.getCoursePlan().add(newCS.getCoursePlan());
						
						// course slots update
						newCS.getSlotList().add(StudentControl.studentInfo.getMatricNo());
						
						// reusing newCS to update old courseSlot
						newCS = Container.getCourseSlotByIndex(cIndex);
						if(newCS==null)
						{
							System.out.println("ERROR! CourseSlots Data does not match with StudentAccount Data!");
							System.out.println("Please validate data files and rerun application!");
							continue;
						}
						newCS.getSlotList().remove(StudentControl.studentInfo.getMatricNo());
						
						// TODO S: [CHECK IF WORKING] Update course slot waiting list -> move user into courseslot to fill vacancy
						if(newCS.getWaitingList().isEmpty() == false)
						{
							// add first student in waitingList to slotList
							newCS.getSlotList().add(newCS.getWaitingList().get(0));
							
							// remove the first index of the waiting list
							newCS.getWaitingList().remove(0);
							
							// TODO S: [CHECK IF WORKING] Email notification on vacancy change
							System.out.println("Sending email........");
							EmailNotification.getInstance().sendNotification(StudentControl.studentInfo, Notification.createMessage(StudentControl.studentInfo.getName(), newCS.getCoursePlan().getCourseID(), true));
							System.out.println("Sent successfully");					

							//update the student Account 
							StudentControl.studentInfo.getCoursePlan().add(newCS.getCoursePlan());
						}
						
						// saving changes to text file
						Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
						Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
						
						System.out.printf("Index no. for Course %s successfully set to %d!\n",currCP.getCourseID(),indexno);
						break outerloop;
					}
					else
					{
						System.out.println("Invalid input for course index no.!");
						sc.next();
					}
				}
				
			}
			else
			{
				System.out.println("Invalid input for course index no.!");
				sc.next();
			}
		}
	}
	
	private static void swopIndexMenu(Scanner sc)
	{
		String userName, password;
		Student st2;
		char[] passMask;
		int indexno, cIndex;
		CoursePlan currCP, newCP;
		outerloop:
		while(true)
		{
			indexno = -1;
			cIndex = -1;
			st2 = null;
			System.out.println();
			System.out.println("(6) Swop Index Number with Another Student");
			StudentControl.displayCourse(StudentControl.studentInfo);
			System.out.printf("Enter course index no. to swop (%d to return): ", Container.BREAK_MENU);
			if (sc.hasNextInt())
			{
				cIndex = sc.nextInt();
				if(cIndex==Container.BREAK_MENU)
					break outerloop;
				
				currCP = Container.getCoursePlanByIndex(cIndex, StudentControl.studentInfo.getCoursePlan());
				if(currCP==null)
				{
					System.out.println();
					System.out.println("Course index no. not registered!");
					continue;
				}
				
				while(true)
				{
					System.out.println();
					System.out.println("\tFOR PEER (STUDENT 2):");
					System.out.printf("Please enter user name (%d to return): ", Container.BREAK_MENU);
					userName = sc.next();
					System.out.println();
					if(userName.equals(""+Container.BREAK_MENU))
						break outerloop;
					else if(userName.equals(StudentControl.studentInfo.getUserName()))
					{
						System.out.println("You have entered the same username as the one currently logged in!");
						continue;
					}
					
					System.out.print("Enter your Password: ");
					
					if(Container.DEBUG_MODE)
						password = sc.next();
					else
					{
						// For masking password.
						passMask = System.console().readPassword(); 
						password = new String(passMask);
					}
					
					password = AccountControl.encryptThisString(password);
					
					if(AccountControl.accountLoginSuccess(userName, password, false))
						st2 = Container.getStudentByUsername(userName);
					else
					{
						System.out.println("You have enter the wrong Username or Password");
						continue;
					}
					
					if(st2 == null)
					{
						System.out.println("Student account not found!");
						continue;
					}
					
					while(true)
					{
						System.out.println();
						StudentControl.displayCourse(st2);
						System.out.printf("Enter student 2's course index no. to swop (%d to return): ", Container.BREAK_MENU);
						if (sc.hasNextInt())
						{
							indexno = sc.nextInt();
							if(indexno==Container.BREAK_MENU)
								break outerloop;
							
							if(cIndex == indexno)
							{
								System.out.println("Same course index no. selected!");
								continue;
							}
							
							newCP = Container.getCoursePlanByIndex(indexno, st2.getCoursePlan());
							if(newCP==null)
							{
								System.out.println("Course index no. not registered for student 2!");
								continue;
							}
							
							// if timetable clash for student 1
							if(StudentControl.timetableClash(StudentControl.studentInfo.getCoursePlan(), currCP, newCP))
							{
								System.out.println("Course index no. clashes with timetable for Student " + StudentControl.studentInfo.getUserName() + "!");
								continue;
							}
							else if(StudentControl.timetableClash(StudentControl.waitingListCourses(StudentControl.studentInfo), currCP, newCP))
							{
								System.out.println("Current courses in the waiting list clashes for Student" + StudentControl.studentInfo.getUserName() + ". Failed to add the Course.");
								continue;
							}
							// if timetable clash for student 2
							else if(StudentControl.timetableClash(st2.getCoursePlan(), newCP, currCP))
							{
								System.out.println("Course index no. clashes with timetable for Student " + st2.getUserName() + "!");
								continue;
							}
							else if(StudentControl.timetableClash(StudentControl.waitingListCourses(st2), newCP, currCP))
							{
								System.out.println("Current courses in the waiting list clashes for Student" + st2.getUserName() + ". Failed to add the Course.");
								continue;
							}
							
							System.out.println("Swopping ...");
							
							// student update
							StudentControl.studentInfo.getCoursePlan().remove(currCP);
							StudentControl.studentInfo.getCoursePlan().add(newCP);
							st2.getCoursePlan().remove(newCP);
							st2.getCoursePlan().add(currCP);
							
							// course slots update
							for(int i=0;i<Container.courseSlotsList.size();i++)
							{
								if(Container.courseSlotsList.get(i).getCoursePlan().equals(currCP))
								{
									Container.courseSlotsList.get(i).getSlotList().remove(StudentControl.studentInfo.getMatricNo());
									Container.courseSlotsList.get(i).getSlotList().add(st2.getMatricNo());
								}
								else if(Container.courseSlotsList.get(i).getCoursePlan().equals(newCP))
								{
									Container.courseSlotsList.get(i).getSlotList().remove(st2.getMatricNo());
									Container.courseSlotsList.get(i).getSlotList().add(StudentControl.studentInfo.getMatricNo());
								}
							}
							
							// saving changes to text file
							Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
							Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
														
							System.out.println("Successfully swopped indexes!");
							break outerloop;
						}
						else
						{
							System.out.println("Invalid input for course index no.!");
							// Clear sc
							sc.next();
						}
					}
				}
			}
			else
			{
				System.out.println("Invalid input for course index no.!");
				// Clear sc
				sc.next();
			}
		}
	}
	
}
