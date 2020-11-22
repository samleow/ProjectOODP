package control;
import java.util.*;
import entity.AllEnums.*;
import entity.*;

public class StudentControl {

	public static Student studentInfo;
	public static CoursePlan currentTimeTableClashes;

	public static void saveStudentInfo(String userName) {
		for (int i = 0; i < Container.studentList.size(); i++) {
			if (Container.studentList.get(i).getUserName().equals(userName)) {
				studentInfo = Container.studentList.get(i);
			}
		}
	}

	public static void addCourse(int indexno) {
		// validation of checking only integer

		boolean waitingList = false;
		CoursePlan tempcourseplan = null;

		// Get the user input of indexno information
		for (int i = 0; i < Container.coursePlanList.size(); i++) {
			if (Container.coursePlanList.get(i).getIndex() == indexno) {
				tempcourseplan = Container.coursePlanList.get(i);
				break;
			}
		}

		// Check for timetable clash
		if (!timetableClash(waitingListCourses(studentInfo), null, tempcourseplan)) {
			if (!timetableClash(studentInfo.getCoursePlan(), null, tempcourseplan)) {
				// Add Student Matric No into the CourseSlots slotList
				for (int k = 0; k < Container.courseSlotsList.size(); k++) {
					if (Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
						// If the Course got no vacancy, put the Student on Waiting List
						if (Container.courseSlotsList.get(k).getSlotList().size() < Container.courseSlotsList.get(k)
								.getTotalSlots()) {
							Container.courseSlotsList.get(k).getSlotList().add(studentInfo.getMatricNo());
							break;
						} else {
							Container.courseSlotsList.get(k).getWaitingList().add(studentInfo.getMatricNo());
							System.out.println("No Vacancy Available. You will be put on the Course: " + tempcourseplan.getCourseID() + " | Index No: " + tempcourseplan.getIndex() + " Waiting List. \n");
							waitingList = true;
							break;
						}
					}
				}
				// Overwrite the latest data into the CourseSlots.txt
				Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);

				// If not in waitingList then add the Course in the Student Class CoursePlan
				// List
				if (!waitingList) {

					// Adding the Index No
					for (int k = 0; k < Container.coursePlanList.size(); k++) {
						if (Container.coursePlanList.get(k).getIndex() == indexno) {
							// It is link and reference
							studentInfo.getCoursePlan().add(Container.coursePlanList.get(k));
							// Overwrite the latest data into the StudentAccount.txt
							Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
							break;
						}
					}
					System.out.println("Successfully Added the Course: " + tempcourseplan.getCourseID() + " | Index No: " + tempcourseplan.getIndex() + ". \n");
				}
			} else {
				System.out.println("Current Course: " + currentTimeTableClashes.getCourseID() + " | Index No: " + currentTimeTableClashes.getIndex() + " Clashes. Failed to add the Course.\n");
			}
		} else {
			System.out.println("Current Course in the waiting list clashes: "  + currentTimeTableClashes.getCourseID() + " | Index No: " + currentTimeTableClashes.getIndex() + ". Failed to add the Course.\n");
		}
	}

	public static void dropCourse(int indexno) {
		CoursePlan tempCoursePlan = null;
		boolean addStudents = true;
		boolean removeMatricNoInWaitingList = false;
		String student = "";
		Student tempstudent = null;

		for (int i = 0; i < StudentControl.studentInfo.getCoursePlan().size(); i++) {
			if (StudentControl.studentInfo.getCoursePlan().get(i).getIndex() == indexno) {
				tempCoursePlan = StudentControl.studentInfo.getCoursePlan().get(i);
				break;
			}
		}

		if (waitingListCourses(studentInfo).size() != 0) {
			for (int i = 0; i < waitingListCourses(studentInfo).size(); i++) {
				if(waitingListCourses(studentInfo).get(i).getIndex() == indexno) {
					for (int y = 0; y < Container.courseSlotsList.size(); y++) {
						if (Container.courseSlotsList.get(y).getCoursePlan().getIndex() == indexno) {
							Container.courseSlotsList.get(y).getWaitingList().remove(studentInfo.getMatricNo());
							Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
							removeMatricNoInWaitingList = true;
							System.out.println("Successfully removed the Course: " + Container.courseSlotsList.get(y).getCoursePlan().getCourseID() + " | Index No: " + Container.courseSlotsList.get(y).getCoursePlan().getIndex() + " from the waiting list. \n");
							break;
						}
					}
				}
			}
		}
		

		if (!removeMatricNoInWaitingList) {

			// Remove CoursePlan from List<CoursePlan> in Student class
			for (int k = 0; k < Container.coursePlanList.size(); k++) {
				if (Container.coursePlanList.get(k).getIndex() == indexno) {
					studentInfo.getCoursePlan().remove(Container.coursePlanList.get(k));
					Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
					break;
				}
			}

			// Remove Student Matric No from the CourseSlots slotList
			for (int k = 0; k < Container.courseSlotsList.size(); k++) {
				if (Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
					Container.courseSlotsList.get(k).getSlotList().remove(studentInfo.getMatricNo());
					Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
					break;
				}
			}

			for (int i = 0; i < Container.courseSlotsList.size(); i++) {
				// Find the courseSlots based on indexno
				if (Container.courseSlotsList.get(i).getCoursePlan().getIndex() == indexno) {
					// check the first student first, if failed, then check another and so on
					while (addStudents) {
						// Check if got vacancy, if have then check waiting list got student a not
						if (Container.courseSlotsList.get(i).getSlotList().size() < Container.courseSlotsList.get(i)
								.getTotalSlots()) {
							if (Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
								student = "";
								tempstudent = null;

								// get the student in the waiting list
								student = Container.courseSlotsList.get(i).getWaitingList().get(0);

								for (int z = 0; z < Container.studentList.size(); z++) {
									if (Container.studentList.get(z).getMatricNo().equals(student)) {
										tempstudent = Container.studentList.get(z);
										break;
									}
								}

								if (timetableClash(tempstudent.getCoursePlan(), null, tempCoursePlan)) {
									Container.courseSlotsList.get(i).getWaitingList().remove(0);
									Container.overwriteFileWithData(Container.COURSESLOT_FILE,
											Container.courseSlotsList);
									EmailNotification.getInstance().sendNotification(tempstudent, Notification.createMessage(tempstudent.getName(), tempCoursePlan.getCourseID(), false));
									} else {
									// remove student from the waiting list and add them
									for (int k = 0; k < Container.courseSlotsList.size(); k++) {
										if (Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
											Container.courseSlotsList.get(k).getSlotList().add(student);
											Container.courseSlotsList.get(k).getWaitingList().remove(0);
											Container.overwriteFileWithData(Container.COURSESLOT_FILE,Container.courseSlotsList);
											break;
										}
									}

									// add index no into List<CoursePlan> in Student class
									for (int k = 0; k < Container.coursePlanList.size(); k++) {
										if (Container.coursePlanList.get(k).getIndex() == indexno) {
											tempstudent.getCoursePlan().add(Container.coursePlanList.get(k));
											Container.overwriteFileWithData(Container.STUDENT_FILE,
													Container.studentList);
											EmailNotification.getInstance().sendNotification(tempstudent,
													Notification.createMessage(tempstudent.getName(),
															tempCoursePlan.getCourseID(), true));
											break;
										}
									}
								}
							}
							// break while loop if no students in the waiting list
							else
								addStudents = false;
						}
						// break while loop if the slot list is full
						else
							addStudents = false;
					}
				}
			}
			System.out.println("Successfully removed the Course. " + tempCoursePlan.getCourseID() + " | Index No: " + tempCoursePlan.getIndex() + " \n");
		}
	}

	public static void displayCourse(Student student) {
		CourseType courseType = CourseType.DEFAULT;
		int AU = -1;

		System.out.println("Display Current Courses you have registered");
		for (int i = 0; i < student.getCoursePlan().size(); i++) {
			for (int j = 0; j < Container.courseList.size(); j++) {
				if (student.getCoursePlan().get(i).getCourseID().equals(Container.courseList.get(j).getCourseID())) {
					AU = Container.courseList.get(j).getCourseAU();
					courseType = Container.courseList.get(j).getCourseType();
					break;
				}
			}
			System.out.println("CourseID: " + student.getCoursePlan().get(i).getCourseID() + " | AU: " + AU
					+ " | Course Type: " + courseType + " | Index No: " + student.getCoursePlan().get(i).getIndex());
		}
		

		System.out.println("\nDisplay Waiting List Courses");
		if (waitingListCourses(student).size() == 0) {
			System.out.println("No courses in waiting list");
		} else {
			for (int i = 0; i < waitingListCourses(student).size(); i++) {
				for (int j = 0; j < Container.courseList.size(); j++) {
					if (waitingListCourses(student).get(i).getCourseID().equals(Container.courseList.get(j).getCourseID())) {
						AU = Container.courseList.get(j).getCourseAU();
						courseType = Container.courseList.get(j).getCourseType();
						break;
					}
				}
				System.out.println("CourseID: " + waitingListCourses(student).get(i).getCourseID() + " | AU: " + AU
						+ " | Course Type: " + courseType + " | Index No: " + waitingListCourses(student).get(i).getIndex());
			}
		}
		System.out.println("");
	}

	public static boolean timetableClash(List<CoursePlan> s, CoursePlan oldCP, CoursePlan newCP) {
		currentTimeTableClashes = null;
		Lesson l1, l2;
		for (int i = 0; i < s.size(); i++) {
			if (s.get(i).equals(oldCP))
				continue;

			for (int j = 0; j < s.get(i).getLessons().size(); j++) {
				l1 = s.get(i).getLessons().get(j);
				for (int k = 0; k < newCP.getLessons().size(); k++) {
					l2 = newCP.getLessons().get(k);
					if (l1.clashWith(l2)) {
						currentTimeTableClashes = s.get(i);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static List<CoursePlan> waitingListCourses(Student student) {
		List<CoursePlan> waitingList = new ArrayList<CoursePlan>();

		for (int i = 0; i < Container.courseSlotsList.size(); i++) {
			if (Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
				for (int y = 0; y < Container.courseSlotsList.get(i).getWaitingList().size(); y++) {
					// Check if student matric no matches with the ones in the waiting list
					if (student.getMatricNo().equals(Container.courseSlotsList.get(i).getWaitingList().get(y))) {
						waitingList.add(Container.courseSlotsList.get(i).getCoursePlan());
					}
				}
			}
		}
		return waitingList;
	}

	public static void displayCurrentAndWaitingCourses() {

		System.out.println("Display Current Courses you have registered");
		for (int i = 0; i < studentInfo.getCoursePlan().size(); i++) {
			System.out.println("CourseID: " + studentInfo.getCoursePlan().get(i).getCourseID() + " | " + "IndexNo: "
					+ studentInfo.getCoursePlan().get(i).getIndex());
		}
		System.out.println("");

		System.out.println("Display Waiting List Courses");
		if (waitingListCourses(studentInfo).size() == 0) {
			System.out.println("No courses in waiting list");
		} else {
			for (int i = 0; i < waitingListCourses(studentInfo).size(); i++) {
				System.out.println("CourseID: " + waitingListCourses(studentInfo).get(i).getCourseID() + " | "
						+ "IndexNo: " + waitingListCourses(studentInfo).get(i).getIndex());
			}
		}
		System.out.println("");
	}

	/**
	 * Checks and displays the vacancy of an index number based on user input.
	 * @param sc The scanner object to handle inputs.
	 */
	public static void checkVacanciesAvailableMenu(Scanner sc)
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
				// calculate the vacancy
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
	
	/**
	 * Changes an index number of the student based on user inputs.
	 * @param sc The scanner object to handle inputs.
	 */
	public static void changeIndexNoMenu(Scanner sc)
	{
		int indexno, cIndex;
		CoursePlan currCP;
		CourseSlots tempCS;
		Student s;
		outerloop:
		while(true) {
			indexno = -1;
			cIndex = -1;
			System.out.println();
			System.out.println("(5) Change Index Number of Course");
			displayCourse(studentInfo);
			System.out.printf("Enter course index no. to change (%d to return): ",Container.BREAK_MENU);
			if (sc.hasNextInt())
			{
				cIndex = sc.nextInt();
				if(cIndex==Container.BREAK_MENU)
					break;
				
				currCP = Container.getCoursePlanByIndex(cIndex, studentInfo.getCoursePlan());
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
						
						tempCS = Container.getCourseSlotByIndex(indexno);						
						if(tempCS==null)
						{
							System.out.println("Course index no. not found!");
							continue;
						}
						if(!tempCS.getCoursePlan().getCourseID().equals(currCP.getCourseID()))
						{
							System.out.println("Course index no. does not match with course!");
							continue;
						}
						if(tempCS.getTotalSlots() == tempCS.getSlotList().size())
						{
							System.out.println("No vacancy for new course index!");
							continue;
						}
						if(timetableClash(studentInfo.getCoursePlan(), currCP, tempCS.getCoursePlan()))
						{
							System.out.println("Course index no. clashes with timetable!");
							continue;
						}
						if(timetableClash(waitingListCourses(studentInfo), currCP, tempCS.getCoursePlan()))
						{
							System.out.println("Current courses in the waiting list clashes. Failed to add the Course.");
							continue;
						}
						
						// student update
						studentInfo.getCoursePlan().remove(currCP);
						studentInfo.getCoursePlan().add(tempCS.getCoursePlan());
						
						// course slots update
						tempCS.getSlotList().add(studentInfo.getMatricNo());
						
						// reusing tempCS to update old courseSlot
						tempCS = Container.getCourseSlotByIndex(cIndex);
						if(tempCS==null)
						{
							System.out.println("ERROR! CourseSlots Data does not match with StudentAccount Data!");
							System.out.println("Please validate data files and rerun application!");
							continue;
						}
						tempCS.getSlotList().remove(studentInfo.getMatricNo());
						if(!tempCS.getWaitingList().isEmpty())
						{
							// get first student in waiting list
							s = Container.getStudentByMatricNo(tempCS.getWaitingList().get(0));
							if(s==null)
							{
								System.out.println("ERROR! CourseSlots Data does not match with StudentAccount Data!");
								System.out.println("Please validate data files and rerun application!");
								continue;
							}
							if(timetableClash(s.getCoursePlan(),null,tempCS.getCoursePlan()))
							{
								// remove student from waiting list
								tempCS.getWaitingList().remove(0);
								
								// Sends email notification
								System.out.println("Sending email........");
								EmailNotification.getInstance().sendNotification(s, Notification.createMessage(s.getName(), tempCS.getCoursePlan().getCourseID(), false));
								System.out.println("Sent successfully");
							}
							else
							{
								// add student to slotList
								tempCS.getSlotList().add(s.getMatricNo());
								
								// remove student from waiting list
								tempCS.getWaitingList().remove(0);
								
								// Sends email notification
								System.out.println("Sending email........");
								EmailNotification.getInstance().sendNotification(s, Notification.createMessage(s.getName(), tempCS.getCoursePlan().getCourseID(), true));
								System.out.println("Sent successfully");
								
								//update the student Account
								s.getCoursePlan().add(tempCS.getCoursePlan());
							}
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
	
	/**
	 * Swaps the index number of two students based on user inputs.
	 * @param sc The scanner object to handle inputs.
	 */
	public static void swapIndexMenu(Scanner sc)
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
			System.out.println("(6) Swap Index Number with Another Student");
			displayCourse(studentInfo);
			System.out.printf("Enter course index no. to swap (%d to return): ", Container.BREAK_MENU);
			if (sc.hasNextInt())
			{
				cIndex = sc.nextInt();
				if(cIndex==Container.BREAK_MENU)
					break outerloop;
				
				currCP = Container.getCoursePlanByIndex(cIndex, studentInfo.getCoursePlan());
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
					else if(userName.equals(studentInfo.getUserName()))
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
						displayCourse(st2);
						System.out.printf("Enter student 2's course index no. to swap (%d to return): ", Container.BREAK_MENU);
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
							if(timetableClash(studentInfo.getCoursePlan(), currCP, newCP))
							{
								System.out.println("Course index no. clashes with timetable for Student " + studentInfo.getUserName() + "!");
								continue;
							}
							else if(timetableClash(waitingListCourses(studentInfo), currCP, newCP))
							{
								System.out.println("Current courses in the waiting list clashes for Student" + studentInfo.getUserName() + ". Failed to add the Course.");
								continue;
							}
							// if timetable clash for student 2
							else if(timetableClash(st2.getCoursePlan(), newCP, currCP))
							{
								System.out.println("Course index no. clashes with timetable for Student " + st2.getUserName() + "!");
								continue;
							}
							else if(timetableClash(waitingListCourses(st2), newCP, currCP))
							{
								System.out.println("Current courses in the waiting list clashes for Student" + st2.getUserName() + ". Failed to add the Course.");
								continue;
							}
							
							System.out.println("Swapping ...");
							
							// student update
							studentInfo.getCoursePlan().remove(currCP);
							studentInfo.getCoursePlan().add(newCP);
							st2.getCoursePlan().remove(newCP);
							st2.getCoursePlan().add(currCP);
							
							// course slots update
							for(int i=0;i<Container.courseSlotsList.size();i++)
							{
								if(Container.courseSlotsList.get(i).getCoursePlan().equals(currCP))
								{
									Container.courseSlotsList.get(i).getSlotList().remove(studentInfo.getMatricNo());
									Container.courseSlotsList.get(i).getSlotList().add(st2.getMatricNo());
								}
								else if(Container.courseSlotsList.get(i).getCoursePlan().equals(newCP))
								{
									Container.courseSlotsList.get(i).getSlotList().remove(st2.getMatricNo());
									Container.courseSlotsList.get(i).getSlotList().add(studentInfo.getMatricNo());
								}
							}
							
							// saving changes to text file
							Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
							Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
														
							System.out.println("Successfully swapped indexes!");
							break outerloop;
						}
						else
						{
							System.out.println("Invalid input for course index no.!");
							sc.next();
						}
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
}
