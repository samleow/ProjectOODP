package boundary;
import java.util.Scanner;
import control.*;
import entity.*;
import entity.AllEnums.WeekType;

public class StudentUI {

	public static void studentLogin() {
		int choice;
		boolean run = true;
		int index;

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
					do {
						System.out.printf("\nEnter the Index Number of the Course to add (%d to return): ", Container.BREAK_MENU);
						if(sc.hasNextInt()) { 
							index = sc.nextInt();
							if(index == Container.BREAK_MENU) {
								break;
							}
							if(Validation.checkIfValidIndex(index)) {
								if(!Validation.checkIfStudentTookThisIndex(index)) {
									if(!Validation.checkIfCourseExempted(index)) {
										if(!Validation.checkIfStudentInWaitingList(index)) {
											StudentControl.addCourse(index);
										break;
										} else {
											System.out.println("You are already in the waiting list. \n");
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
						StudentControl.displayCurrentAndWaitingCourses();
						System.out.printf("Enter the Index Number of the Course to drop (%d to return): ", Container.BREAK_MENU);
						if(sc.hasNextInt()) { 
							index = sc.nextInt();
							if(index == Container.BREAK_MENU) {
								break;
							}
							if(Validation.checkIfValidIndex(index)) {
								if(Validation.checkIfStudentTookThisIndex(index) || (Validation.checkIfStudentInWaitingList(index))) {
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
					StudentControl.displayCourse();

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
	
	// TODO S: Menu going back to previous error input instead of back to start
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
						// TODO S: [NEED DO?] check if new course index clash with waiting list timetable
						
						// student update
						StudentControl.studentInfo.getCoursePlan().remove(currCP);
						StudentControl.studentInfo.getCoursePlan().add(newCS.getCoursePlan());
						
						// course slots update
						newCS.getSlotList().add(StudentControl.studentInfo.getMatricNo());
						// TODO S: [WIP] if user is in new course slot waiting list, remove from waiting list after add
						// if student was already in waiting list for this course, remove from waiting list
						// should search through entire courseSlots list to check for every index of same course,
						// not just for that particular index
//						if(newCS.getWaitingList().contains(StudentControl.studentInfo.getMatricNo()))
//							newCS.getWaitingList().remove(StudentControl.studentInfo.getMatricNo());
						
						// reusing newCS to update old courseSlot
						newCS = Container.getCourseSlotByIndex(cIndex);
						if(newCS==null)
						{
							System.out.println("ERROR! CourseSlots Data does not match with StudentAccount Data!");
							System.out.println("Please validate data files and rerun application!");
							continue;
						}
						newCS.getSlotList().remove(StudentControl.studentInfo.getMatricNo());
						// TODO S: [WIP HALTED] Update course slot waiting list -> move user into courseslot to fill vacancy
						
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
	
	// TODO S: clean up old code after replacement
//	private static void swopIndexMenu(Scanner sc)
//	{
//		boolean op6 = true;
//		String userName, password;
//		Student st2;
//		int indexno, cIndex;
//		do {
//			// TODO S: Menu going back to previous error input instead of back to start
//			indexno = -1;
//			cIndex = -1;
//			st2 = null;
//			System.out.println("(6) Swop Index Number with Another Student");
//			System.out.printf("Enter course index no. to swop (%d to return): ", Container.BREAK_MENU);
//			if (sc.hasNextInt())
//			{
//				cIndex = sc.nextInt();
//				if(cIndex==Container.BREAK_MENU)
//				{
//					op6=false;
//					break;
//				}
//
//				CoursePlan currCP = null;
//				for(int i=0;i<StudentControl.studentInfo.getCoursePlan().size();i++)
//				{
//					if(StudentControl.studentInfo.getCoursePlan().get(i).getIndex()==cIndex)
//					{
//						currCP = StudentControl.studentInfo.getCoursePlan().get(i);
//						break;
//					}
//				}
//
//				if(currCP==null)
//				{
//					System.out.println("Course index no. not registered!");
//					continue;
//				}
//
//				System.out.println("\tFOR PEER (STUDENT 2):");
//				System.out.printf("Please enter user name (%d to return): ", Container.BREAK_MENU);
//				userName = sc.next();
//				if(userName.equals(""+Container.BREAK_MENU))
//				{
//					op6=false;
//					break;
//				}
//				else if(userName.equals(StudentControl.studentInfo.getUserName()))
//				{
//					System.out.println("You have entered the same username as the one currently logged in!");
//					continue;
//				}
//				
//				System.out.print("Enter your Password: ");
//				
//				if(Container.DEBUG_MODE)
//					password = sc.next();
//				else
//				{
//					// For masking password.
//					char[] passMask = System.console().readPassword(); 
//					password = new String(passMask);
//				}
//
//				password = HashingPassword.encryptThisString(password);
//				
//				if(LoginAccount.getFileInfo(userName, password))
//				{
//					// After login successful
//					for(int i=0;i<Container.studentList.size();i++)
//					{
//						if(Container.studentList.get(i).getUserName().equals(userName))
//						{
//							st2 = Container.studentList.get(i);
//							break;
//						}
//					}
//				}
//				else
//				{
//					System.out.println("You have enter the wrong Username or Password");
//					continue;
//				}
//				
//				if(st2 == null)
//				{
//					System.out.println("Student account not found!");
//					continue;
//				}
//
//				System.out.printf("Enter student 2's course index no. to swop (%d to return): ", Container.BREAK_MENU);
//				if (sc.hasNextInt())
//				{
//					indexno = sc.nextInt();
//					if(indexno==Container.BREAK_MENU)
//					{
//						op6=false;
//						break;
//					}
//					
//					if(cIndex == indexno)
//					{
//						System.out.println("Same course index no. selected!");
//						continue;
//					}
//					
//					CoursePlan newCP = null;
//					for(int i=0;i<st2.getCoursePlan().size();i++)
//					{
//						if(st2.getCoursePlan().get(i).getIndex()==indexno)
//						{
//							newCP = st2.getCoursePlan().get(i);
//							break;
//						}
//					}
//					
//					if(newCP==null)
//					{
//						System.out.println("Course index no. not registered for student 2!");
//						continue;
//					}
//					
//					if(StudentControl.timetableClash(StudentControl.studentInfo, currCP, newCP))
//					{
//						System.out.println("Course index no. clashes with timetable for Student " + StudentControl.studentInfo.getUserName() + "!");
//						continue;
//					}
//					else if(StudentControl.timetableClash(st2, newCP, currCP))
//					{
//						System.out.println("Course index no. clashes with timetable for Student " + st2.getUserName() + "!");
//						continue;
//					}
//					
//					System.out.println("Swopping ...");
//					
//					// student update
//					StudentControl.studentInfo.getCoursePlan().remove(currCP);
//					StudentControl.studentInfo.getCoursePlan().add(newCP);
//					st2.getCoursePlan().remove(newCP);
//					st2.getCoursePlan().add(currCP);
//					
//					// course slots update
//					for(int i=0;i<Container.courseSlotsList.size();i++)
//					{
//						if(Container.courseSlotsList.get(i).getCoursePlan().equals(currCP))
//						{
//							Container.courseSlotsList.get(i).getSlotList().remove(StudentControl.studentInfo.getMatricNo());
//							Container.courseSlotsList.get(i).getSlotList().add(st2.getMatricNo());
//						}
//						else if(Container.courseSlotsList.get(i).getCoursePlan().equals(newCP))
//						{
//							Container.courseSlotsList.get(i).getSlotList().remove(st2.getMatricNo());
//							Container.courseSlotsList.get(i).getSlotList().add(StudentControl.studentInfo.getMatricNo());
//						}
//					}
//					
//					// saving changes to text file
//					Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
//					Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
//					
//					if(Container.DEBUG_MODE)
//					{
//						System.out.println(StudentControl.studentInfo.getCoursePlan());
//						System.out.println(st2.getCoursePlan());
//						for(int i=0;i<Container.courseSlotsList.size();i++)
//						{
//							System.out.println(Container.courseSlotsList.get(i).getCoursePlan().getCourseID() + ":\t"
//									+ Container.courseSlotsList.get(i).getSlotList());
//						}
//					}
//
//					System.out.println("Successfully swopped indexes!");
//					op6=false;
//					break;
//				}
//				else
//				{
//					System.out.println("Invalid input for course index no.!");
//					// Clear sc
//					sc.next();
//				}
//			}
//			else
//			{
//				System.out.println("Invalid input for course index no.!");
//				// Clear sc
//				sc.next();
//			}
//		}
//		while(op6);
//	}
	
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
							// if timetable clash for student 2
							else if(StudentControl.timetableClash(st2.getCoursePlan(), newCP, currCP))
							{
								System.out.println("Course index no. clashes with timetable for Student " + st2.getUserName() + "!");
								continue;
							}
							// TODO S: [NEED DO?] check if new course index clash with waiting list timetable for both students
							
							System.out.println("Swopping ...");
							
							// student update
							StudentControl.studentInfo.getCoursePlan().remove(currCP);
							StudentControl.studentInfo.getCoursePlan().add(newCP);
							st2.getCoursePlan().remove(newCP);
							st2.getCoursePlan().add(currCP);
							
							// TODO S: [WIP] remove student(s) from waiting list if they are inside
							// similar to case 5
							
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
							
							// TODO S: do proper checks on courseslots after proper test cases population
							if(Container.DEBUG_MODE)
							{
								System.out.println(StudentControl.studentInfo.getCoursePlan());
								System.out.println(st2.getCoursePlan());
								for(int i=0;i<Container.courseSlotsList.size();i++)
								{
									System.out.println(Container.courseSlotsList.get(i).getCoursePlan().getCourseID() + ":\t"
											+ Container.courseSlotsList.get(i).getSlotList());
								}
							}
							
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
