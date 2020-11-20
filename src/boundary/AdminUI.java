package boundary;

import java.util.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import control.AdminControl;
import control.Container;
import control.HashingPassword;

import entity.Course;
import entity.CoursePlan;
import entity.Date;
import entity.Lesson;
import entity.Location;
import entity.Period;
import entity.Time;

import entity.Date;
import entity.AllEnums.AccountType;
import entity.AllEnums.Day;
import entity.AllEnums.Gender;

import entity.AllEnums.*;



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
		Period accessPeriod;
		Date accessDate;
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
					do {
					System.out.printf("\nEnter student matric number: (%d to return): ", Container.BREAK_MENU);

					matricNo = sc.next();
					
					if(matricNo.equals(Integer.toString(Container.BREAK_MENU))) {
						break;
					}
					if(AdminControl.checkIfValidMatricNo(matricNo)) {
						do {
						System.out.printf("\nEnter Access Date (e.g. YYYY-MM-DD) (%d to return): ", Container.BREAK_MENU);
						String strAccessDate = sc.next();
						
						if(strAccessDate.equals(Integer.toString(Container.BREAK_MENU))) {
							break;
						}
						
						if(AdminControl.checkIfValidDate(strAccessDate)) {
							StringTokenizer star = new StringTokenizer(strAccessDate , "-");
							accessDate = new Date(Integer.parseInt(star.nextToken().trim()),Integer.parseInt(star.nextToken().trim())
									,Integer.parseInt(star.nextToken().trim()));
							
							do {
							System.out.printf("\nEnter starting time (e.g. HH:MM) (%d to return): ", Container.BREAK_MENU);
							String strStartTime = sc.next();
							
							if(strStartTime.equals(Integer.toString(Container.BREAK_MENU))) {
								break;
							}
							
							if(AdminControl.checkIfValidTime(strStartTime)) {
								do {
								StringTokenizer startT = new StringTokenizer(strStartTime , ":");
								System.out.printf("\nEnter ending time (e.g. HH:MM) (%d to return): ", Container.BREAK_MENU);
								String strEndTime = sc.next();
								
								if(strEndTime.equals(Integer.toString(Container.BREAK_MENU))) {
									break;
								}
								
								if(AdminControl.checkIfValidTime(strEndTime)) {
									StringTokenizer endT = new StringTokenizer(strEndTime , ":");
									
									accessPeriod = new Period(new Time(Integer.parseInt(startT.nextToken().trim()),Integer.parseInt(startT.nextToken().trim())), 
											new Time(Integer.parseInt(endT.nextToken().trim()),Integer.parseInt(endT.nextToken().trim())), Day.DEFAULT);

									AdminControl.setStudentAccessPeriod(matricNo,accessPeriod,accessDate);
									System.out.println("\nUpdated " + matricNo + "'s access period");
									break;
								} else {
									System.out.println("\nTime is invalid.");
								}
								}while(true);
								break;
							} else {
								System.out.println("\nTime is invalid.");
							}
							} while(true);
							break;
						} else {
							System.out.println("\nDate is invalid.");
						}
						}while(true);
						break;
					} else {
						System.out.println("\nStudent does not exist currently.");
					}
					} while(true);
					break;
				case 2: /* (2) Add a student (matric, name, number, gender, nationality, Max AU, Password  etc) */
					

					addStudentAcc(sc);
					break;
					
					
				case 3: /* (3) Add/Update a course (course code, school, its index numbers and vacancy).*/

					
					add_update_course(sc);
					break;
					
					
				case 4: /* (4) Check available slot for an index number (vacancy in a class)*/
					do {
						System.out.printf("\nEnter the index number for the Course you would like to check (%d to return): ", Container.BREAK_MENU);
						if(sc.hasNextInt()) { 
							index = sc.nextInt();
							if(index == Container.BREAK_MENU) {
								break;
							}
							
							if(AdminControl.checkIfValidIndex(index)) {
								int availableSlots = AdminControl.getNoOfSlotsByCourseIndex(index);
								int totalSlots = AdminControl.getTotalSlotsByCourseIndex(index);
								//System.out.println("There are " + availableSlots + " available slots for index " + index + "\n");
								System.out.println("\nIndex: " + index);
								System.out.println("Slots: " + availableSlots + "/" + totalSlots + " [Vacancy/Total Size]");
							} else {
								System.out.println("\nIndex does not exist.");
							}
						} else {
							System.out.println("\n'" + sc.next() + "' is not a valid index. Please enter only Integers.");
						}
					} while (true);
					break;
				case 5: /* (5) Display student list by index number.*/
					do {
						System.out.printf("\nEnter the index number (%d to return): ", Container.BREAK_MENU);
						if(sc.hasNextInt()) {
							index = sc.nextInt();
							
							if(index == Container.BREAK_MENU) {
								break;
							}
							
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
					} while(true);
					break;
				case 6: /* (6) Display student list by course (all students registered for the selected course)*/
					do {
						System.out.printf("\nEnter the Course ID (e.g. CZ2002, CZ2003,...) (%d to return): ", Container.BREAK_MENU);
						courseID = sc.next();
						
						if(courseID.equals(Integer.toString(Container.BREAK_MENU))) {
							break;
						}
						
						String capsCourseID = courseID.toUpperCase();
						
						stringList = AdminControl.getStudentListByCourseID(capsCourseID);
						
						if(AdminControl.checkIfValidCourseID(capsCourseID)) {
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
					} while(true);
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
	
	
	
	private static void add_update_course(Scanner sc) 
	{
		int choice;
		boolean run = true;
		
		do
		{
			System.out.println("(1) Add a Course");
			System.out.println("(2) Add Index of a course.");
			//System.out.println("(3) Set Vacancy for a course.");
			System.out.println("(3) Add Lesson for a course.");
			System.out.println("(4) Update a Course Information");
			System.out.println("(5) Update a Course Plan");
			System.out.println("(6) Update a Course Lesson");
			System.out.println("(7) Update a Course Slots");
			System.out.println("(8) Quit");
			System.out.print("Enter the your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) 
			{
				choice = sc.nextInt();
				switch (choice) {
				
				case 1: //(1) Add a Course
					
					addCourse(sc);
					break;
				
				case 2: //(2) Add Index of a course.
				
					addCourseIndex(sc);
					break;
				
//				case 3: // (3) Set Vacancy for a course.
//					
//					addVacancy(sc);
//					break;
					
				case 3: // (3) Add Lesson for a course.
					
					addLesson(sc);
					break;
					
				case 4: //(4) Update a Course Information
					
					updateCourseInfo(sc);
					break;
					
				case 5: //(5) Update a Course Plan
					
					updateCoursePlan(sc);
					break;
					
				case 6: //(6) Update a Course Lesson
					
					updateLesson(sc);
					break;
					
				case 7: //(7) Update a Course Slots
					
					updateCourseSlots(sc);
					break;
					
				case 8:
					System.out.println("Quit...");
					break;
					
				default:
					System.out.println("Please Enter Choice from 1 to 8");
				
				}
			} 
			else 
			{
				System.out.println("Please Enter Choice from 1 to 8");
				// Clear sc
				sc.next();
			}
		}while(run);
		
		
	}


	// TODO: validation class and exit -1
	// update CourseSlots
	private static void updateCourseSlots(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Update Course Slots");
		int index;
		
		// validate Index
				boolean isIndex = false;
				
				while (true) {
					
					System.out.print("Enter the index: ");
					// user input the index section to modify
					if(sc.hasNextInt()) {
						index = sc.nextInt();

						// check if there exist a index in the coursPlanList
						for (int i = 0; i < Container.coursePlanList.size(); i++) 
						{
							if (index == Container.coursePlanList.get(i).getIndex()) 
							{
								isIndex = true;
								break;
							} 
							else 
							{
								isIndex = false;
							}
						}
						if (isIndex) 
						{
							break;
						} 
						else 
						{
							System.out.printf("Course Index: %d is not found.\n", index);
						}
					}
					else
					{
						System.out.println("Enter a numeric value ");
					}

				}
		
				sc.nextLine(); // Consume newline left-over
				
				int newTotalSlots = -1;
				int numCurrentStud = -1;
				int numInWaitingList = -1;
				
				//Display the total number of slot for that index
				for (int i=0 ;i<Container.courseSlotsList.size(); i++)
				{
					if(index == Container.courseSlotsList.get(i).getCoursePlan().getIndex())
					{
						System.out.println("---------------------------------");
						// print total slots
						System.out.printf("Index: %d have total of %d slots.\n", index, Container.courseSlotsList.get(i).getTotalSlots());
						System.out.println("---------------------------------");
						
						// print number of student registered
						numCurrentStud = Container.courseSlotsList.get(i).getSlotList().size();
						System.out.printf("Current number of student registered in Index %d is %d.\n", index, numCurrentStud);
						System.out.println("---------------------------------");
						
						// print number of student in waiting list
						numInWaitingList = Container.courseSlotsList.get(i).getWaitingList().size();
						System.out.printf("Current number of student in the waiting list for Index %d is %d.\n", index, numInWaitingList);
						System.out.println("---------------------------------");
					}
					
				}
				
				
				// validate int input
				while(true)
				{
					System.out.print("How many slots will you like to change?: ");
					if(sc.hasNextInt())
					{
						newTotalSlots = sc.nextInt();
						
						if(newTotalSlots < numCurrentStud)
						{
							System.out.println("Total Slots should not be less than the number of student currently registered.");
						}
						else
						{
							break;
						}

					}
					else
					{
						System.out.println("Enter a numeric value ");
					}
				}
				
				sc.nextLine(); // Consume newline left-over
				
				AdminControl.setCourseSlots(index, newTotalSlots);
				
				
		
	}



	// TODO: validation class and exit -1
	// update lesson
	private static void updateLesson(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Update Lesson");
		
		
		String courseIDInput ="";
		
		
		// validate courseCode
		boolean isCourseID = false;
		while (true) {
			System.out.print("Enter the Course Code: ");
			courseIDInput = sc.nextLine().toUpperCase();
			
			//exit
			if(courseIDInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}

			// check if the courseID exist in the courseList
			for (int i = 0; i < Container.courseList.size(); i++) 
			{
				if (courseIDInput.equals(Container.courseList.get(i).getCourseID())) 
				{
					//courseIDInput = Container.courseList.get(i).toString();
					isCourseID = true;
					break;
				} 
				else 
				{
					isCourseID = false;
				}
			}
			if (isCourseID) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course Code: %s \n", courseIDInput);
			}

		}
		
		// print the list of lesson
		for (int i=0 ;i<Container.lessonList.size(); i++)
		{
			if(courseIDInput.equals(Container.lessonList.get(i).getCourseID()))
			{
				System.out.println(Container.lessonList.get(i).toString());
			}
			
		}
		
		int lessonID;
		String storeSelectedInfo = "";
		String[] splitString;
		
		// validate the correct ID listed in Lesson.txt
		boolean isID = false;
		while(true)
		{
			System.out.print("Enter the ID as shown above you want to modify: ");
			if(sc.hasNextInt())
			{
				lessonID = sc.nextInt();
				if(lessonID == Container.BREAK_MENU)
				{
					return;
				}
				
				for (int i=0 ;i<Container.lessonList.size(); i++)
				{
					if(courseIDInput.equals(Container.lessonList.get(i).getCourseID()) && lessonID == Container.lessonList.get(i).getLessonID())
					{
						
						storeSelectedInfo = Container.lessonList.get(i).toString();
						isID = true;
						break;
					}
					
				}
				
				if(isID == true)
				{
					//info enter correctly
					break;
				}
				else
				{
					System.out.printf("Invalid ID: %s\n", lessonID);
				}
				
			}
			else
			{
				System.out.println("Please enter numeric value.");
			}
		
		}
		
		sc.nextLine(); // Consume newline left-over
		
		
		splitString = storeSelectedInfo.split("\\|");
		
		boolean isthereStudent = false;
		
		int choice = 0;
		
		boolean run = true;
		String[] splitTimeDay = splitString[4].split("\\,");
		String startTimeValue = splitTimeDay[0];
		String endTimeValue = splitTimeDay[1];
		String dayValue = splitTimeDay[2];
		
		String isOnlineValue;
		
		if(splitString[5].equals("true"))
		{
			isOnlineValue = "YES";
		}
		else
		{
			isOnlineValue = "NO";
		}
		
		String location;
		
		if(splitString[6].equals("null~null~null"))
		{
			location = "No address detail";
		}
		else
		{
			location = splitString[6];
		}
		
		// validate if there is student in the that ID
		// check through if the lesson ID exist in the CoursePlan
		for (int i=0 ;i<Container.coursePlanList.size(); i++)
		{
			
			for(int j= 0; j<Container.coursePlanList.get(i).getLessons().size(); j++)
			{
				
				// if the lesson ID exist in coursePlanList
				if(courseIDInput.equals(Container.coursePlanList.get(i).getCourseID()) && lessonID == Container.coursePlanList.get(i).getLessons().get(j).getLessonID())
				{
					
					int getCoursePlanIndex = Container.coursePlanList.get(i).getIndex();
					
					
					//check courseSlotsList if there are student in the waiting list or registered list
					for(int k = 0; k<Container.courseSlotsList.size(); k++ )
					{
						// check for matching index
						if(getCoursePlanIndex == Container.courseSlotsList.get(k).getCoursePlan().getIndex())
						{
							
							// if there is a student in the courseSlotsList
							if(Container.courseSlotsList.get(k).getSlotList().isEmpty() == false || Container.courseSlotsList.get(k).getWaitingList().isEmpty() == false )
							{
								isthereStudent = true;
								
							}
							// once found the CoursePlanIndex, end the loop
							break;
							
						}

					}
					
				}
				
			}

			
		}
		
		if(isthereStudent == true)
		{
			do 
			{
				System.out.println("There are Students in ID "+ splitString[0] + "you are only allow to modify these 2 option.");
				System.out.println("Which Selection in ID "+ splitString[0] + " you want to modify?");
				System.out.println("(1) Is the lesson online: " + isOnlineValue);
				System.out.println("(2) Location: " + location);
				System.out.println("(3) Quit");
				
				// validate the choice input
				if (sc.hasNextInt()) 
				{
					choice = sc.nextInt();
					switch (choice) {
					
					case 1: //(1) Is the lesson online: 
						sc.nextLine(); // Consume newline left-over
						
						// this is to clear location if the lesson is online
						Location lessonLocationClean = new Location();
						
						// validate isonline input
						String isonlineInput; // temp storage for user input gender
						boolean isOnline = false;
						
						while (true) 
						{
							System.out.print("Is the lesson online Y/N: ");
							isonlineInput = sc.nextLine().toUpperCase();
							
							if(isonlineInput.equals("Y")) 
							{
								isOnline = true;
								break;
							} 
							else if(isonlineInput.equals("N")){
								isOnline = false;
								break;
							}
							else
							{
								System.out.println("Invalid input ");
							}
						}

						AdminControl.setLessonIsOnline(lessonID, isOnline);
						
						if(isOnline == true)
						{
							isOnlineValue = "YES";
							splitString[5] = "true";
							//update the display for Location
							splitString[6] = "null~null~null";
							location = splitString[6];
						}
						else
						{
							isOnlineValue = "NO";
							splitString[5] = "false";
						}
						
						
						break;
						
					case 2:
						
						sc.nextLine(); // Consume newline left-over
						
						Location lessonLocation = new Location();
						//int lessonID = Container.lessonList.size() + 1 ; // increase the index of lesson ID
						
						String locationName;
						String locationExtName;
						String locationAddress;
						
						
						if(splitString[5].equals("true"))
						{
							// online lesson do not need location names
							System.out.println("This Lesson is held online.");
						}
						else
						{
							System.out.print("Enter the Location Name (E.g Hardware Lab 2): ");
							locationExtName = sc.nextLine();
							
							System.out.print("Enter the Location shortform Name (E.g HWLAB 2): ");
							locationName = sc.nextLine();
							
							System.out.print("Enter the Location Address (E.g N4-01B-05): ");
							locationAddress = sc.nextLine();
							
							lessonLocation = new Location(locationName, locationExtName, locationAddress);
							
							AdminControl.setLessonLocation(lessonID, lessonLocation);
							//update the display for Location
							splitString[6] = lessonLocation.toString();
						}
						
						
						
						if(splitString[6].equals("null~null~null"))
						{
							location = "No address detail";
						}
						else
						{
							location = splitString[6];
						}

						break;
						
						
					default:
						System.out.println("Please Enter Choice from 1 to 3");
				
					}
				}
				else
				{
					System.out.println("Please Enter Choice from 1 to 3");
					// Clear sc
					sc.next();
				}
			}
			while(choice != 3);
		}
		else
		{
		
			do 
			{
				
				System.out.println("Which Selection in ID "+ splitString[0] + " you want to modify?");
				System.out.println("(1) Course ID: " + splitString[1]);
				System.out.println("(2) Lesson Type: " + splitString[2]);
				System.out.println("(3) Week Type: " + splitString[3]);
				System.out.printf("(4) Lesson Period From: %s To: %s  \n", splitTimeDay[0], splitTimeDay[1] );
				System.out.println("(5) Lesson Day: " + splitTimeDay[2]);
				System.out.println("(6) Is the lesson online: " + isOnlineValue);
				System.out.println("(7) Location: " + location);
				System.out.println("(8) Quit");
	
	
				// validate the choice input
				if (sc.hasNextInt()) 
				{
					choice = sc.nextInt();
					switch (choice) {
					
					case 1: //(1) Course ID:
						
						sc.nextLine(); // Consume newline left-over
						
						String newCourseID;
						String existingCourseID;
						
						boolean isNewCode = false;
						
						// validate the CourseID
						while(true)
						{
							System.out.print("Modify Course code " + splitString[1] + " to: ");
							newCourseID = sc.nextLine().toUpperCase();
							
							// check if there exist courseID in courseList
							for (int i = 0; i < Container.courseList.size(); i++) 
							{
								if (newCourseID.equals(Container.courseList.get(i).getCourseID())) 
								{
									
									isNewCode = true;
									break;
								} 
								else 
								{
									isNewCode = false;
								}
							}
							if (isNewCode) {
								break;
							} 
							else 
							{
								System.out.printf("Course Code %s is Invalid.\n", newCourseID);
								
							}
						}
						
						AdminControl.setLessonCourseID(lessonID, newCourseID);
						splitString[1] = newCourseID;
						
						break;
						
					case 2: //(2) Lesson Type:
						
						sc.nextLine(); // Consume newline left-over
						
						String lessonTypeInput;
						LessonType lessonType;
						
						// validate Lesson Type
						//boolean isLessonType = false;
						while (true) {
							System.out.print("Modify lesson type " + splitString[2] + " to (e.g Lecture/Lec, Tutorial/Tut, Lab):  ");
							lessonTypeInput = sc.nextLine().toUpperCase();

							if (lessonTypeInput.equals(LessonType.LAB.toString())) 
							{
								lessonType = LessonType.LAB;
								break;
							} 
							else if (lessonTypeInput.equals(LessonType.LECTURE.toString()) || lessonTypeInput.equals("LEC")) {
								lessonType = LessonType.LECTURE;
								break;
							} 
							else if(lessonTypeInput.equals(LessonType.TUTORIAL.toString())|| lessonTypeInput.equals("TUT"))
							{
								lessonType = LessonType.TUTORIAL;
								break;
								
							}
							else 
							{
								System.out.println("Invalid input ");
							}

						}
						
						AdminControl.setLessonLessonType(lessonID, lessonType);
						splitString[2] = lessonType.toString();
						
						break;
						
					case 3: //(3) Week Type:

						sc.nextLine(); // Consume newline left-over
						
						String weekInput;
						WeekType weekType;
						
						// validate Week Type
						while (true) {
							System.out.print("Modify Week Type " + splitString[3] + " to (e.g ODD, EVEN, WEEKLY): ");
							weekInput = sc.nextLine().toUpperCase();

							if (weekInput.equals(WeekType.ODD.toString())) 
							{
								weekType = WeekType.ODD;
								break;
							} 
							else if (weekInput.equals(WeekType.EVEN.toString())) {
								weekType = WeekType.EVEN;
								break;
							} 
							else if(weekInput.equals(WeekType.WEEKLY.toString()))
							{
								weekType = WeekType.WEEKLY;
								break;

							}
							else 
							{
								System.out.println("Invalid input ");
							}

						}
						
						AdminControl.setLessonWeeklyType(lessonID, weekType);
						splitString[3] = weekType.toString();
						
						break;
						
						
					case 4: //(4) Lesson Period

						sc.nextLine(); // Consume newline left-over
						
						String timeInput;
						Time startTime;
						Time endTime;
						Period lessonPeriod;
						
						// Validate Start time
						while (true) 
						{
							System.out.print("Modify Lesson Start Time from " + splitTimeDay[0] +" to Format(HH:MM): ");
							timeInput = sc.nextLine();

							// exit
							if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
							{
								return;
								
							} 
							else if (AdminControl.checkIfValidTime(timeInput)) 
							{
								String[] splitStartTime = timeInput.split("\\:");

								startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
								break;
							} 
							else 
							{
								System.out.println("Invalid Input Time Format(HH:MM): ");
							}
						}
						
						//Update the display of start time
						splitTimeDay[0] = timeInput;
						
						// Validate End time
						while (true) 
						{
							System.out.print("Modify Lesson End Time from " + splitTimeDay[1] +" to Format(HH:MM): ");
							timeInput = sc.nextLine();

							// exit
							if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
							{
								return;
								
							} 
							else if (AdminControl.checkIfValidTime(timeInput)) {
								String[] splitEndTime = timeInput.split("\\:");

								endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
								break;
							} 
							else 
							{
								System.out.println("Invalid Input Time Format(HH:MM): ");
							}
						}
						
						//Update the display of end time
						splitTimeDay[1] = timeInput;
						
						// required to convert String from txt file to ENUM
						Day lessonDayDefault = Day.DEFAULT;
						
						if (dayValue.equals(lessonDayDefault.MONDAY.toString())) 
						{
							lessonDayDefault = lessonDayDefault.MONDAY;
							
						} 
						else if (dayValue.equals(lessonDayDefault.TUESDAY.toString())) {
							lessonDayDefault = lessonDayDefault.TUESDAY;
							
						} 
						else if(dayValue.equals(lessonDayDefault.WEDNESDAY.toString()))
						{
							lessonDayDefault = lessonDayDefault.WEDNESDAY;
							
						}
						else if(dayValue.equals(lessonDayDefault.THURSDAY.toString()))
						{
							lessonDayDefault = lessonDayDefault.THURSDAY;
							
						}
						else if(dayValue.equals(lessonDayDefault.FRIDAY.toString()))
						{
							lessonDayDefault = lessonDayDefault.FRIDAY;
							
						}
						else if(dayValue.equals(lessonDayDefault.SATURDAY.toString()))
						{
							lessonDayDefault = lessonDayDefault.SATURDAY;
							
						}
						else if(dayValue.equals(lessonDayDefault.SUNDAY.toString()))
						{
							lessonDayDefault = lessonDayDefault.SUNDAY;
							
						}
						else 
						{
							System.out.println("Invalid input ");
						}
						
						// lesson Period include day constructor
						lessonPeriod = new Period(startTime, endTime, lessonDayDefault);
						
						//update the Lesson Period
						AdminControl.setLessonPeriod(lessonID, lessonPeriod);
						
						
						break;
						
					case 5: //(5) Lesson Day:
						
						sc.nextLine(); // Consume newline left-over
						
						String dayInput;
						Day lessonDay = Day.DEFAULT;
						
						// validate Day
						while (true) {
							System.out.print("Enter the Lesson Day (e.g MONDAY/MON, TUESDAY/TUE): ");
							dayInput = sc.nextLine().toUpperCase();

							if (dayInput.equals(lessonDay.MONDAY.toString()) || (dayInput.equals("MON"))) 
							{
								lessonDay = lessonDay.MONDAY;
								break;
							} 
							else if (dayInput.equals(lessonDay.TUESDAY.toString())|| (dayInput.equals("TUE"))) {
								lessonDay = lessonDay.TUESDAY;
								break;
							} 
							else if(dayInput.equals(lessonDay.WEDNESDAY.toString())|| (dayInput.equals("WED")))
							{
								lessonDay = lessonDay.WEDNESDAY;
								break;

							}
							else if(dayInput.equals(lessonDay.THURSDAY.toString())|| (dayInput.equals("THU")))
							{
								lessonDay = lessonDay.THURSDAY;
								break;

							}
							else if(dayInput.equals(lessonDay.FRIDAY.toString())|| (dayInput.equals("FRI")))
							{
								lessonDay = lessonDay.FRIDAY;
								break;

							}
							else if(dayInput.equals(lessonDay.SATURDAY.toString())|| (dayInput.equals("SAT")))
							{
								lessonDay = lessonDay.SATURDAY;
								break;

							}
							else if(dayInput.equals(lessonDay.SUNDAY.toString())|| (dayInput.equals("SUN")))
							{
								lessonDay = lessonDay.SUNDAY;
								break;

							}
							else 
							{
								System.out.println("Invalid input ");
							}

						}
						
						//update the display of Lesson Day
						splitTimeDay[2] = lessonDay.toString();
						
						
						// Use the txt file Start Time 
						String[] splitStartTime = splitTimeDay[0].split("\\:");
						
						Time startTimeDefault = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
						
						// Use the txt file End Time 
						String[] splitEndTime = splitTimeDay[1].split("\\:");
						
						Time endTimeDefault = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
						
						lessonPeriod = new Period(startTimeDefault, endTimeDefault, lessonDay);
						
						//update the Lesson Period
						AdminControl.setLessonPeriod(lessonID, lessonPeriod);

						break;
						
					case 6: //(6) Is the lesson online:
						
						sc.nextLine(); // Consume newline left-over
						
						// validate isonline input
						String isonlineInput; // temp storage for user input gender
						boolean isOnline = false;
						
						while (true) 
						{
							System.out.print("Is the lesson online Y/N: ");
							isonlineInput = sc.nextLine().toUpperCase();
							
							if(isonlineInput.equals("Y")) 
							{
								isOnline = true;
								break;
							} 
							else if(isonlineInput.equals("N")){
								isOnline = false;
								break;
							}
							else
							{
								System.out.println("Invalid input ");
							}
						}

						AdminControl.setLessonIsOnline(lessonID, isOnline);
						
						if(isOnline == true)
						{
							isOnlineValue = "YES";
							splitString[5] = "true";
							//update the display for Location
							splitString[6] = "null~null~null";
							location = splitString[6];
						}
						else
						{
							isOnlineValue = "NO";
							splitString[5] = "false";
						}
						
						break;
						
					case 7: //(7) Location:
						
						sc.nextLine(); // Consume newline left-over
						
						Location lessonLocation = new Location();
						//int lessonID = Container.lessonList.size() + 1 ; // increase the index of lesson ID
						
						String locationName;
						String locationExtName;
						String locationAddress;
						
						
						if(splitString[5].equals("true"))
						{
							// online lesson do not need location names
							System.out.println("This Lesson is held online.");
							
						}
						else
						{
							System.out.print("Enter the Location Name (E.g Hardware Lab 2): ");
							locationExtName = sc.nextLine();
							
							System.out.print("Enter the Location shortform Name (E.g HWLAB 2): ");
							locationName = sc.nextLine();
							
							System.out.print("Enter the Location Address (E.g N4-01B-05): ");
							locationAddress = sc.nextLine();
							
							lessonLocation = new Location(locationName, locationExtName, locationAddress);
							
							AdminControl.setLessonLocation(lessonID, lessonLocation);
							
							//update the display for Location
							splitString[6] = lessonLocation.toString();
						}
						
						
						
						if(splitString[6].equals("null~null~null"))
						{
							location = "No address detail";
						}
						else
						{
							location = splitString[6];
						}

						break;
						
					case 8: // (8) Quit

						System.out.println("Quit...");
						break;
						
					default:
						System.out.println("Please Enter Choice from 1 to 5");
	

					}
				}
				else
				{
					System.out.println("Please Enter Choice from 1 to 8");
					// Clear sc
					sc.next();
				}
				
				
			}while(choice != 8);
		
		}
	}


	// TODO validate Class and exit -1
	// Update Course Plan
	private static void updateCoursePlan(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Update a Course Plan");
		
		int index;
		String storeCourseInfo ="";
		String[] splitString;
		
		// validate Index
		boolean isIndex = false;
		
		while (true) {
			
			System.out.print("Enter the index: ");
			// user input the index section to modify
			if(sc.hasNextInt()) {
				index = sc.nextInt();

				// check if there exist a index in the coursPlanList
				for (int i = 0; i < Container.coursePlanList.size(); i++) 
				{
					if (index == Container.coursePlanList.get(i).getIndex()) 
					{
						// store the selected index information from CoursePlan.txt
						storeCourseInfo = Container.coursePlanList.get(i).toString();
						isIndex = true;
						break;
					} 
					else 
					{
						isIndex = false;
					}
				}
				if (isIndex) 
				{
					break;
				} 
				else 
				{
					System.out.printf("Course Index: %d is not found.\n", index);
				}
			}
			else
			{
				System.out.println("Please enter numaric value");
			}

		}
		sc.nextLine(); // Consume newline left-over
		
		
		if(isIndex == true) 
		{

			splitString = storeCourseInfo.split("\\|");
			
			int choice = 0;
			
			boolean run = true;
			
			do 
			{
				
				System.out.println("Which Selection in index "+ splitString[2] + " you want to modify?");
				System.out.println("(1) Group Name: " + splitString[1]);
				System.out.println("(2) Index: " + splitString[2]);
				System.out.println("(3) Re-enter Lesson List");
				System.out.println("(4) Add Lesson to List");
				System.out.println("(5) Delete Lesson");
				System.out.println("(6) Quit");
				
				// validate the choice input
				if (sc.hasNextInt()) 
				{
					choice = sc.nextInt();
					switch (choice) {
					
					case 1: // (1) Update Group Name:
						sc.nextLine(); // Consume newline left-over
						
						//System.out.print("Modify Course name " + splitString[1] + " to: ");
						String newGroupID;
						
						
						// validate Group ID
						boolean isGroupID = false;
						while (true) {
							System.out.print("Modify Course name " + splitString[1] + " to: ");
							newGroupID = sc.nextLine().toUpperCase();

							//check if there exist a GroupID in coursPlantList
							for (int i = 0; i < Container.coursePlanList.size(); i++) 
							{
								if (newGroupID.equals(Container.coursePlanList.get(i).getGroupID())) 
								{
									isGroupID = true;
									break;
								} 
								else 
								{
									isGroupID = false;
								}
							}
							if (isGroupID) {
								System.out.printf("Course Group: %s is used.\n", newGroupID);
								
							} 
							else 
							{
								break;
							}

						}
						
						//update txt file
						AdminControl.setCoursePlanGroupID(index, newGroupID);
						
						splitString[1] = newGroupID; // update the listing
						
						break;
						
					case 2: //(2) Update Index:
						
						int newIndex;
						
						// validate Index
						boolean isNewIndex = false;
						while (true) {
							System.out.print("Modify Index " + splitString[2] + " to: ");
							
							if(sc.hasNextInt())
							{
								newIndex = sc.nextInt();

								// check if there exist a index in the coursePlantList
								for (int i = 0; i < Container.coursePlanList.size(); i++) 
								{
									if (newIndex == Container.coursePlanList.get(i).getIndex()) 
									{
										isNewIndex = true;
										break;
									} 
									else 
									{
										isNewIndex = false;
									}
								}
								if (isNewIndex) {
									System.out.printf("Course Index: %d is used.\n", index);

								} 
								else 
								{
									break;
								}

							}
							else
							{
								System.out.println("Please enter numaric value");
							}
						}
						sc.nextLine(); // Consume newline left-over
						
						//update txt file
						AdminControl.setCoursePlanIndex(index, newIndex);
						
						splitString[2] = Integer.toString(newIndex); // update the listing
						
						index = newIndex; // update the listing
						
						break;
						
						
					case 3: // (3) Re-enter Lesson List
						
						System.out.println("Re Enter Lesson List to index " + splitString[2]);
						System.out.println("Here are the Lesson you can add for " + splitString[2]);
						
						// Display list of lesson related to the course ID
						for (int i = 0; i < Container.lessonList.size(); i++) {
							
							if(Container.lessonList.get(i).getCourseID().equals(splitString[0]))
							System.out.println(Container.lessonList.get(i).toString());

						}
						
						int totalNumberAdd;
						int count = 0;
						int lessonIndex;
						CoursePlan addCoursePlanList = new CoursePlan();
						
						System.out.print("Enter the toal number of lesson you want to add: ");
						
						if(sc.hasNextInt())
						{
							// total number of the lesson user want to add
							totalNumberAdd = sc.nextInt();
							sc.nextLine(); // Consume newline left-over
							
							// loop the number of times the user want to add
							while(count < totalNumberAdd)
							{
								
								System.out.print("Enter the lesson ID you want to add: ");
								//user enter the ID from the lesson.txt
								if(sc.hasNextInt())
								{
									lessonIndex = sc.nextInt();
									sc.nextLine(); // Consume newline left-over
									
									// search for the ID in the Lesson.txt
									for(int i = 0;i < Container.lessonList.size(); i++) 
									{
										// validate the courseID and the Lesson ID
										if(Container.lessonList.get(i).getCourseID().equals(splitString[0]) && Container.lessonList.get(i).getLessonID() == lessonIndex)
										{
											
											// remove and add the lesson ID to CoursePlan.txt
											addCoursePlanList.getLessons().add(Container.lessonList.get(i));

											// increase counter 
											count++;
											
											System.out.println( count +" lesson added.");
										}
									}
									
								
									
								}
								else
								{
									System.out.println("Please enter numaric value");
								}
							}

						}
						else
						{
							System.out.println("Please enter numaric value");
						}
						
						//update txt file
						AdminControl.setCoursePlanLesson(index, addCoursePlanList.getLessons());
						
						break;
						
					case 4: //(4) Add Lesson to List
						
						System.out.println("Add Lesson List to index " + splitString[2]);
						System.out.println("Here are the Lesson you can add to " + splitString[2]);
						
						// Display list of lesson related to the course ID
						for (int i = 0; i < Container.lessonList.size(); i++) {
							
							if(Container.lessonList.get(i).getCourseID().equals(splitString[0]))
							System.out.println(Container.lessonList.get(i).toString());

						}
						
						int totalNumberAdd_1;
						int count_1 = 0;
						int lessonIndex_1;
						CoursePlan addCoursePlanList_1 = new CoursePlan();
						
						
						System.out.print("Enter the toal number of lesson you want to add: ");
						
						if(sc.hasNextInt())
						{
							// total number of the lesson user want to add
							totalNumberAdd_1 = sc.nextInt();
							sc.nextLine(); // Consume newline left-over
							
							// loop the number of times the user want to add
							while(count_1 < totalNumberAdd_1)
							{
								
								System.out.print("Enter the lesson ID you want to add: ");
								//user enter the ID from the lesson.txt
								if(sc.hasNextInt())
								{
									lessonIndex_1 = sc.nextInt();
									sc.nextLine(); // Consume newline left-over
									
									// search for the ID in the Lesson.txt
									for(int i = 0;i < Container.lessonList.size(); i++) 
									{
										// validate the courseID and the Lesson ID
										if(Container.lessonList.get(i).getCourseID().equals(splitString[0]) && Container.lessonList.get(i).getLessonID() == lessonIndex_1)
										{
											// Note: this is to prevent overwrite of lesson at CoursePlan.txt
											// Extract out the list from Course Plan.txt
											for(int j = 0; j <  Container.coursePlanList.size(); j++)
											{
												if ( Container.coursePlanList.get(j).getCourseID().equals(splitString[0]) && Container.coursePlanList.get(j).getIndex() == Integer.parseInt(splitString[2]))
												{
													// Extract out the list of Lesson from CoursePlan.txt
													for(int k = 0 ; k < Container.coursePlanList.get(j).getLessons().size(); k++)
													{
														//System.out.println(Container.coursePlanList.get(j).getLessons().get(k));
														// Add the previous Lessons from CoursePlan.txt 
														addCoursePlanList_1.getLessons().add(Container.coursePlanList.get(j).getLessons().get(k));
													}
												}
											}
											
											//add the lesson ID to CoursePlan.txt
											addCoursePlanList_1.getLessons().add(Container.lessonList.get(i));

											// increase counter 
											count_1++;
											
											System.out.println( count_1 +" lesson added.");
										}
									}
									
								
									
								}
								else
								{
									System.out.println("Please enter numaric value");
								}
							}

						}
						else
						{
							System.out.println("Enter a number");
						}
						
						//update txt file
						AdminControl.setCoursePlanLesson(index, addCoursePlanList_1.getLessons());
						
						break;
						
						
					case 5: //(5) Delete Lesson
						
						sc.nextLine(); // Consume newline left-over
						
						CoursePlan deleteLesson = new CoursePlan();
						
						System.out.println("Delete Lesson index " + splitString[2]);
						
						String answer;
						//valide the answer Y or N
						while(true)
						{
							System.out.print("Are you sure you want to delete Lesson from index " + splitString[2] + " ?(Y/N): ");

							answer = sc.nextLine().toUpperCase();

							if(answer.equals("Y"))
							{
								// send default value of the lesson Array list 
								//update txt file
								AdminControl.setCoursePlanLesson(index, deleteLesson.getLessons());

								break;
							}
							else if (answer.equals("N"))
							{
								break;
							}
							else
							{
								System.out.println("Please enter Y/N");
							}
						}
						
						break;
						
						
					case 6:
						System.out.println("Quit...");
						run = false;
						break;
						
					default:
						System.out.println("Please Enter Choice from 1 to 5");	
						
					}
				} 
				else 
				{
					System.out.println("Please Enter Choice from 1 to 5");
					// Clear sc
					sc.next();
				}
				
				
			}while(choice != 6);
		
		
		}
	}



	// TODO validate Class and exit -1
	// Update Course Information
	private static void updateCourseInfo(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Update a Course Information");
		
		String courseID;
		String storeCourseInfo ="";
		
		
		// validate courseCode
		boolean isCourseCode = false;
		while (true) {
			System.out.print("Enter the Course Code: ");
			courseID = sc.nextLine().toUpperCase();

			// check if the courseID exist in the courseList
			for (int i = 0; i < Container.courseList.size(); i++) 
			{
				if (courseID.equals(Container.courseList.get(i).getCourseID())) 
				{
					storeCourseInfo = Container.courseList.get(i).toString();
					isCourseCode = true;
					break;
				} 
				else 
				{
					isCourseCode = false;
				}
			}
			if (isCourseCode) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course Code: %s \n", courseID);
			}

		}
		
		
		if(isCourseCode == true) 
		{

			String[] splitString = storeCourseInfo.split("\\|");
			
			int choice;
			
			boolean run = true;
			
			do 
			{
				
				System.out.println("Which Selection you want to modify?");
				System.out.println("(1) Course name: " + splitString[0]);
				System.out.println("(2) School Name: " + splitString[1]);
				System.out.println("(3) Course Code: " + splitString[2]);
				System.out.println("(4) Course AU: " + splitString[3]);
				System.out.println("(5) Course Type: " + splitString[4]);
				System.out.println("(6) Quit");
				
				// validate the choice input
				if (sc.hasNextInt()) 
				{
					choice = sc.nextInt();
					switch (choice) {
					
					case 1: // (1) Update Course name:
						
						sc.nextLine(); // Consume newline left-over
						
						System.out.print("Modify Course name " + splitString[0] + " to: ");
						String newCourseName;
						
						newCourseName = sc.nextLine();
						
						//update txt file
						AdminControl.setCourseName(courseID, newCourseName);

						splitString[0] = newCourseName; // update the listing
						
						break;
						
					case 2: // (2) Update School Name:
						
						sc.nextLine(); // Consume newline left-over
						
						System.out.print("Modify School name " + splitString[1] + " to: ");
						
						String newSchoolName;
						
						newSchoolName = sc.nextLine().toUpperCase();
						
						//update txt file
						AdminControl.setSchoolName(courseID, newSchoolName);

						splitString[1] = newSchoolName; // update the listing
						
						break;
						
					case 3: //(3) Update Course Code:
						
						sc.nextLine(); // Consume newline left-over
						
						//System.out.print("Modify Course code " + splitString[2] + " to: ");
						
						String newCourseID;
						
						
						boolean isNewCode = false;
						
						// validate the CourseID
						while(true)
						{
							System.out.print("Modify Course code " + splitString[2] + " to: ");
							newCourseID = sc.nextLine().toUpperCase();
							
							// check if there exist courseID in courseList
							for (int i = 0; i < Container.courseList.size(); i++) 
							{
								if (newCourseID.equals(Container.courseList.get(i).getCourseID())) 
								{
									isNewCode = true;
									break;
								} 
								else 
								{
									isNewCode = false;
								}
							}
							if (isNewCode) {
								System.out.printf("Course Code %s is used.\n", courseID);
							} 
							else 
							{
								break;
							}
						}
						
						//update txt file
						AdminControl.setCourseID(courseID, newCourseID);

						splitString[2] = newCourseID; // update the listing
						courseID = newCourseID; // update the listing
						
						break;
						
					case 4: // (4) Update Course AU:
						
						sc.nextLine(); // Consume newline left-over
						
						System.out.print("Modify Course AU " + splitString[3] + " to: ");
						
						int newAU;
						
						// validate course AU
						while(true)
						{
							if(sc.hasNextInt())
							{
								newAU = sc.nextInt();
								sc.nextLine(); // Consume newline left-over
								break;
							}
							else
							{
								System.out.println("Invalid input ");
							}
							
						}
						
						//update txt file
						AdminControl.setCourseAU(courseID, newAU);

						splitString[3] = Integer.toString(newAU); // update the listing
						
						break;
				
						
						
					case 5: //(5) Update Course Type: 

						sc.nextLine(); // Consume newline left-over
						
						System.out.print("Modify Course Type " + splitString[4] + " to: ");
						
						String couseTypeInput;
						CourseType newCourseType;
						
						// validate Course type
						while (true) 
						{
	
							couseTypeInput = sc.nextLine().toUpperCase();
							if (couseTypeInput.equals(CourseType.CORE.toString())) 
							{
								newCourseType = CourseType.CORE;
								break;
							} 
							else if (couseTypeInput.equals(CourseType.GER.toString())) {
								newCourseType = CourseType.GER;
								break;
							} 
							else if(couseTypeInput.equals(CourseType.PRESCRIBED.toString()))
							{
								newCourseType = CourseType.PRESCRIBED;
								break;
								
							}
							else if (couseTypeInput.equals(CourseType.UNRESTRICTED_ELECTIVE.toString()) || couseTypeInput.equals("UE"))
							{
								newCourseType = CourseType.UNRESTRICTED_ELECTIVE;
								break;
							}
							else 
							{
								System.out.println("Invalid input ");
							}
						}
						
						//update txt file
						AdminControl.setCourseType(courseID, newCourseType);

						splitString[3] = newCourseType.toString(); // update the listing
						
						break;
						
					case 6:
						System.out.println("Quit...");
						run = false;
						break;

						
					default:
						System.out.println("Please Enter Choice from 1 to 5");
				
					}
				} 
				else 
				{
					System.out.println("Please Enter Choice from 1 to 5");
					// Clear sc
					sc.next();
				}
				
				
			}while(run);

		}

	}


	// TODO validate Class and exit -1
	// Add Lesson
	private static void addLesson(Scanner sc) {

		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Add a lesson for a course");
		
		String courseID;
		
		
		// validate courseCode
		boolean isCourseCode = false;
		while (true) {
			System.out.print("Enter the Course Code: ");
			courseID = sc.nextLine().toUpperCase();

			for (int i = 0; i < Container.courseList.size(); i++) 
			{
				if (courseID.equals(Container.courseList.get(i).getCourseID())) 
				{
					isCourseCode = true;
					break;
				} 
				else 
				{
					isCourseCode = false;
				}
			}
			if (isCourseCode) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course Code: %s \n", courseID);
			}

		}
		
		String lessonTypeInput;
		LessonType lessonType;
		
		// validate Lesson Type
		//boolean isLessonType = false;
		while (true) {
			System.out.print("Enter the Lesson Type (e.g Lecture/Lec, Tutorial/Tut, Lab): ");
			lessonTypeInput = sc.nextLine().toUpperCase();

			if (lessonTypeInput.equals(LessonType.LAB.toString())) 
			{
				lessonType = LessonType.LAB;
				break;
			} 
			else if (lessonTypeInput.equals(LessonType.LECTURE.toString()) || lessonTypeInput.equals("LEC")) {
				lessonType = LessonType.LECTURE;
				break;
			} 
			else if(lessonTypeInput.equals(LessonType.TUTORIAL.toString())|| lessonTypeInput.equals("TUT"))
			{
				lessonType = LessonType.TUTORIAL;
				break;
				
			}
			else 
			{
				System.out.println("Invalid input ");
			}

		}
		
		
		String weekInput;
		WeekType weekType;
		
		// validate Week Type
		while (true) {
			System.out.print("Enter the Week Type (e.g ODD, EVEN, WEEKLY): ");
			weekInput = sc.nextLine().toUpperCase();

			if (weekInput.equals(WeekType.ODD.toString())) 
			{
				weekType = WeekType.ODD;
				break;
			} 
			else if (weekInput.equals(WeekType.EVEN.toString())) {
				weekType = WeekType.EVEN;
				break;
			} 
			else if(weekInput.equals(WeekType.WEEKLY.toString()))
			{
				weekType = WeekType.WEEKLY;
				break;

			}
			else 
			{
				System.out.println("Invalid input ");
			}

		}
		
		
		String timeInput;
		Time startTime;
		Time endTime;
		Period lessonPeriod;
		
		// Validate Start time
		while (true) 
		{
			System.out.print("Enter Lesson Start Time Format(HH:MM): ");
			timeInput = sc.nextLine();

			// exit
			if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
			{
				return;
				
			} 
			else if (AdminControl.checkIfValidTime(timeInput)) 
			{
				String[] splitStartTime = timeInput.split("\\:");

				startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
				break;
			} 
			else 
			{
				System.out.println("Invalid Input Time Format(HH:MM): ");
			}
		}
		
		// Validate End time
		while (true) 
		{
			System.out.print("Enter Lesson End Time Format(HH:MM): ");
			timeInput = sc.nextLine();

			// exit
			if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
			{
				return;
				
			} 
			else if (AdminControl.checkIfValidTime(timeInput)) {
				String[] splitEndTime = timeInput.split("\\:");

				endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
				break;
			} 
			else 
			{
				System.out.println("Invalid Input Time Format(HH:MM): ");
			}
		}
				
		String dayInput;
		Day lessonDay = Day.DEFAULT;
		
		// validate Day
		while (true) {
			System.out.print("Enter the Lesson Day (e.g MONDAY/MON, TUESDAY/TUE): ");
			dayInput = sc.nextLine().toUpperCase();

			if (dayInput.equals(lessonDay.MONDAY.toString()) || (dayInput.equals("MON"))) 
			{
				lessonDay = lessonDay.MONDAY;
				break;
			} 
			else if (dayInput.equals(lessonDay.TUESDAY.toString())|| (dayInput.equals("TUE"))) {
				lessonDay = lessonDay.TUESDAY;
				break;
			} 
			else if(dayInput.equals(lessonDay.WEDNESDAY.toString())|| (dayInput.equals("WED")))
			{
				lessonDay = lessonDay.WEDNESDAY;
				break;

			}
			else if(dayInput.equals(lessonDay.THURSDAY.toString())|| (dayInput.equals("THU")))
			{
				lessonDay = lessonDay.THURSDAY;
				break;

			}
			else if(dayInput.equals(lessonDay.FRIDAY.toString())|| (dayInput.equals("FRI")))
			{
				lessonDay = lessonDay.FRIDAY;
				break;
			}
			else if(dayInput.equals(lessonDay.SATURDAY.toString())|| (dayInput.equals("SAT")))
			{
				lessonDay = lessonDay.SATURDAY;
				break;
			}
			else if(dayInput.equals(lessonDay.SUNDAY.toString())|| (dayInput.equals("SUN")))
			{
				lessonDay = lessonDay.SUNDAY;
				break;
			}
			else 
			{
				System.out.println("Invalid input ");
			}

		}

		// lesson Period include day constructor
		lessonPeriod = new Period(startTime, endTime, lessonDay);
		

		// validate isonline input
		String isonlineInput; // temp storage for user input gender
		boolean isOnline = false;
		
		while (true) 
		{
			System.out.print("Is the lesson online Y/N: ");
			isonlineInput = sc.nextLine().toUpperCase();
			
			if(isonlineInput.equals("Y")) 
			{
				isOnline = true;
				break;
			} 
			else if(isonlineInput.equals("N")){
				isOnline = false;
				break;
			}
			else
			{
				System.out.println("Invalid input ");
			}
		}
		
		
		Location lessonLocation = new Location();
		int lessonID = Container.lessonList.size() + 1 ; // increase the index of lesson ID
		
		String locationName;
		String locationExtName;
		String locationAddress;
		
		
		if(isOnline == true)
		{
			// online lesson do not need location names
			AdminControl.addLessonLocation(lessonID, courseID, lessonType, weekType, lessonPeriod, isOnline, lessonLocation);
		}
		else
		{
			System.out.print("Enter the Location Name (E.g Hardware Lab 2): ");
			locationExtName = sc.nextLine();
			
			System.out.print("Enter the Location shortform Name (E.g HWLAB 2): ");
			locationName = sc.nextLine();
			
			System.out.print("Enter the Location Address (E.g N4-01B-05): ");
			locationAddress = sc.nextLine();
			
			lessonLocation = new Location(locationName, locationExtName, locationAddress);
			
			AdminControl.addLessonLocation(lessonID, courseID, lessonType, weekType, lessonPeriod, isOnline, lessonLocation);
			
		}

	}



	// Can be deleted cos add vacancy merge with Add index
//	private static void addVacancy(Scanner sc) {
//		// TODO Auto-generated method stub
//		
//		
//		sc.nextLine(); // Consume newline left-over
//		
//		System.out.println("Set Vacancy of a course");
//		
//		String courseID;
//		
//		int index;
//		
//		CoursePlan courseIndex = new CoursePlan();
//		
//		int totalSlots;
//		
//		// validate courseCode
//		boolean isCourseCode = false;
//		while (true) {
//			System.out.print("Enter the Course Code: ");
//			courseID = sc.nextLine().toUpperCase();
//
//			// check if the courseID exist in the courseList
//			for (int i = 0; i < Container.courseList.size(); i++) 
//			{
//				if (courseID.equals(Container.courseList.get(i).getCourseID())) 
//				{
//					isCourseCode = true;
//					break;
//				} 
//				else 
//				{
//					isCourseCode = false;
//				}
//			}
//			if (isCourseCode) {
//				break;
//			} 
//			else 
//			{
//				System.out.printf("Invalid Course Code: %s \n", courseID);
//			}
//
//		}
//		
//		
//		boolean isIndex = false;
//		// validate index of the course
//		while (true) {
//			System.out.print("Enter a new "+ courseID + " index: ");
//			
//			if(sc.hasNextInt())
//			{
//				index = sc.nextInt();
//
//				// check if the index exist in the coursePlanList
//				for (int i = 0; i < Container.coursePlanList.size(); i++) 
//				{
//					if (index == Container.coursePlanList.get(i).getIndex()) 
//					{
//						courseIndex.setIndex(index);
//						isIndex = true;
//						break;
//					} 
//					else 
//					{
//						isIndex = false;
//					}
//				}
//				if (isIndex) {
//
//					break;
//				} 
//				else 
//				{
//					System.out.printf("Invalid Course Index: %d.\n", index);
//
//				}
//
//			}
//			else
//			{
//				System.out.println("Please enter numbers");
//			}
//		}
//		
//		sc.nextLine(); // Consume newline left-over
//		
//		// validate total slot input
//		while(true)
//		{
//			System.out.print("Enter total slot for "+ courseID + " index: "+ index + " :");
//			if(sc.hasNextInt())
//			{
//				totalSlots = sc.nextInt();
//				break;
//			}
//			else
//			{
//				System.out.println("Please enter numbers");
//			}
//		}
//		
//		sc.nextLine(); // Consume newline left-over
//		
//		AdminControl.addSlots(totalSlots, courseIndex);
//		
//	}



	// TODO validate Class and exit -1
	// Add Course Index and Index Vacancy
	private static void addCourseIndex(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Add Index of a course");
		
		String courseID;
		
		String groupID;
		
		int index;
		
		CoursePlan courseIndex = new CoursePlan();
		
		int totalSlots;

		
		// validate courseCode
		boolean isCourseCode = false;
		while (true) {
			System.out.print("Enter the Course Code: ");
			courseID = sc.nextLine().toUpperCase();

			//check if the courseID exist in the courseList
			for (int i = 0; i < Container.courseList.size(); i++) 
			{
				if (courseID.equals(Container.courseList.get(i).getCourseID())) 
				{
					isCourseCode = true;
					break;
				} 
				else 
				{
					isCourseCode = false;
				}
			}
			if (isCourseCode) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course Code: %s \n", courseID);
			}

		}
		
		
		// validate Group ID
		boolean isGroupID = false;
		while (true) {
			System.out.print("Enter a new "+ courseID + " group: ");
			groupID = sc.nextLine().toUpperCase();

			// Check if the groupID is being used
			for (int i = 0; i < Container.coursePlanList.size(); i++) 
			{
				if (courseID.equals(Container.coursePlanList.get(i).getGroupID())) 
				{
					isGroupID = true;
					break;
				} 
				else 
				{
					isGroupID = false;
				}
			}
			if (isGroupID) {
				System.out.printf("Course Group: %s is used.\n", groupID);
				
			} 
			else 
			{
				break;
			}

		}
		
		// validate Index
		boolean isIndex = false;
		while (true) {
			System.out.print("Enter a new "+ courseID + " index: ");
			
			if(sc.hasNextInt())
			{
				index = sc.nextInt();
				
				if(String.valueOf(index).length() == 5)
				{

					// Check if the index is being used
					for (int i = 0; i < Container.coursePlanList.size(); i++) 
					{
						if (index == Container.coursePlanList.get(i).getIndex()) 
						{
							isIndex = true;
							break;
						} 
						else 
						{
							
							isIndex = false;
						}
					}
					if (isIndex) {
						System.out.printf("Course Index: %d is used.\n", index);
	
					} 
					else // index not used
					{
						courseIndex.setIndex(index);
						break;
					}

				}
				else
				{
					System.out.println("Please enter 5 digit number");
				}
			}
			else
			{
				System.out.println("Please enter numbers");
			}
		}

		sc.nextLine(); // Consume newline left-over
		
		// validate total slot input
		while(true)
		{
			System.out.print("Enter total slot for "+ courseID + " index: "+ index + " :");
			if(sc.hasNextInt())
			{
				totalSlots = sc.nextInt();
				break;
			}
			else
			{
				System.out.println("Please enter numaric value");
			}
		}
		
		
		AdminControl.addIndex(courseID, groupID, index);
		
		AdminControl.addSlots(totalSlots, courseIndex);
		
	}



	// TODO validate Class and exit -1
	// Add Course
	private static void addCourse(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over

		//to exit the selection
		//boolean isExit = false; 
		
		System.out.println("Add a Course");
		
		// Algo
		String courseName;
		
		// SCSE
		String school;
		
		// CZ2001
		String courseID;
		
		// course total AU
		int au;
		
		// CORE, GE etc
		CourseType courseType;
		
		System.out.print("Enter the Course Name: ");
		courseName = sc.nextLine();
		if(courseName.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		
		System.out.print("Enter the Course School: ");
		school = sc.nextLine().toUpperCase();
		
		
		
		// validate courseCode
		boolean isCourseCode = false; 
		while(true)
		{
			System.out.print("Enter the Course Code: ");
			courseID = sc.nextLine().toUpperCase();
			
			// Check if there a used courseID
			for (int i=0 ;i<Container.courseList.size(); i++)
			{
				if(courseID.equals(Container.courseList.get(i).getCourseID()))
				{
					isCourseCode = true;
					break;
				}
				else
				{
					isCourseCode = false;
				}
			}
			if(isCourseCode)
			{
				System.out.printf("Course Code: %s is used.\n", courseID);
			}
			else
			{
				break;
			}
			
		}
		
		
		// validate course AU
		while(true)
		{
			System.out.print("Enter the Course total AU: ");
			if(sc.hasNextInt())
			{
				au = sc.nextInt();
				sc.nextLine(); // Consume newline left-over
				break;
			}
			else
			{
				System.out.println("Please enter numaric value ");
			}
			
		}
		
		// validate Course type
		String c; // temp storage for user input 
		while (true) 
		{
			System.out.print("Enter Course type: ");
			c = sc.nextLine().toUpperCase();
			if (c.equals(CourseType.CORE.toString())) 
			{
				courseType = CourseType.CORE;
				break;
			} 
			else if (c.equals(CourseType.GER.toString())) {
				courseType = CourseType.GER;
				break;
			} 
			else if(c.equals(CourseType.PRESCRIBED.toString()))
			{
				courseType = CourseType.PRESCRIBED;
				break;
				
			}
			else if (c.equals(CourseType.UNRESTRICTED_ELECTIVE.toString()) || c.equals("UE"))
			{
				courseType = CourseType.UNRESTRICTED_ELECTIVE;
				break;
			}
			else 
			{
				System.out.println("Invalid input ");
			}
		}
		
		
		AdminControl.addCourse(courseName, school, courseID, au, courseType);
		

		
		
		
	}



	private static void addStudentAcc(Scanner sc)
	{
		sc.nextLine(); // Consume newline left-over

		String name;
		String userName;
		String password = "123";
		AccountType acctype;
		String matricNo;
		Gender gender = Gender.DEFAULT;
		String nationality;
		int maxAU;
		String accessTime;
		String accessDate;
		Time startTime = new Time();
		Time endTime = new Time();
		Date date = new Date();
		Period period;
		String email;
		
		
		String hashPassword;
//		try
//		{
//			Container.readStudentFile("StudentAccount.txt", Container.studentList);
//		}catch(IOException e) {};
		
		System.out.print("Enter new student name: ");
		name = sc.nextLine();
		// exit
		if(name.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		// Validate User Name
		boolean isUserName = false;
		while(true)
		{
			System.out.print("Enter new student user name: ");
			userName = sc.nextLine().toLowerCase();
			// exit
			if(userName.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			// check if the student username is being used
			for (int i=0 ;i<Container.studentList.size(); i++)
			{
				
				//System.out.println(Container.studentList.get(i).getUserName());
				if(userName.equals(Container.studentList.get(i).getUserName())) 
				{
					isUserName = true;
					break;
				}
				else
				{
					isUserName = false;
				}
				
			}
			if(isUserName)
			{
				System.out.printf("User Name: %s is used.\n", userName);
			}
			else
			{
				break;
			}

			
		}
		
		System.out.println("Default password: 123");
		hashPassword = HashingPassword.encryptThisString(password);
		
		System.out.println("Account Type: STUDENT");
		acctype = AccountType.STUDENT;
		
		// validate Matric number
		boolean isMatric = false;
		while(true)
		{
			System.out.print("Enter new student matric number: ");
			matricNo = sc.nextLine().toUpperCase();
			// exit
			if(matricNo.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			//check if the matric number is being used
			for (int i=0 ;i<Container.studentList.size(); i++)
			{
				
				if(matricNo.equals(Container.studentList.get(i).getMatricNo())) 
				{
					isMatric = true;
					break;
				}
				else
				{
					isMatric = false;
				}
				
			}
			if(isMatric)
			{
				System.out.printf("Matric Number: %s is used.\n", matricNo);
			}
			else
			{
				break;
			}

			
		}


		// validate gender input
		String g; // temp storage for user input gender
		while (true) 
		{
			System.out.print("Enter new student gender M/F: ");
			g = sc.nextLine().toUpperCase();
			// exit
			if(g.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			if(g.equals("M")) {
				gender = Gender.MALE;
				break;
			} 
			else if(g.equals("F")){
				gender = Gender.FEMALE;
				break;
			}
			else
			{
				System.out.println("Invalid input ");
			}
		}
		
		System.out.print("Enter new student nationality: ");
		nationality = sc.nextLine();
		// exit
		if(nationality.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		System.out.println("Student default Max AU: 21 ");
		maxAU = 21;

		//TODO what if user enter YY/MM/DD
		// Validate date
		while(true)
		{
			// Date and time need validation
			System.out.print("Enter Student Access Date Format(YYYY-MM-DD): ");
			accessDate = sc.nextLine();
			// exit
			if (accessDate.equals(Integer.toString(Container.BREAK_MENU))) 
			{
				return;
			} 
			else if (AdminControl.checkIfValidDate(accessDate)) 
			{
				String[] splitDate = accessDate.split("\\-");
	
				date = new Date(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]),
						Integer.parseInt(splitDate[2]));
				break;
	
			}
			else
			{
				System.out.println("Invalid Input Date Format(YYYY-MM-DD): ");
			}
		
		}
		
		// Validate Start time
		while(true)
		{
			System.out.print("Enter Student Access Start Time Format(HH:MM): ");
			accessTime = sc.nextLine();

			// exit
			if(accessTime.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			else if(AdminControl.checkIfValidTime(accessTime))
			{
			
			
				String[] splitStartTime = accessTime.split("\\:");
				startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
				break;
			}
			else
			{
				System.out.println("Invalid Input Time Format(HH:MM): ");
			}
		}
		
		// Validate End time
		while(true)
		{
			System.out.print("Enter Student Access End Time Format(HH:MM): ");
			accessTime = sc.nextLine();
			
			// exit
			if(accessTime.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			else if(AdminControl.checkIfValidTime(accessTime))
			{
				String[] splitEndTime = accessTime.split("\\:");
	
				endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
				break;
			}
			else
			{
				System.out.println("Invalid Input Time Format(HH:MM): ");
			}
		}
		
		period = new Period(startTime, endTime);
		
		
		
		// Add student Email
		//System.out.print("Enter new student email: ");
		//email = sc.nextLine();
		email = "testing@gmail.com";
		System.out.printf("Auto Genternated Email: %s\n ",email);
		
		
		
		//Validate if the student have exempted module
		String isExemptedInput; // temp storage for user input gender
		boolean isExempted = false;
		
		while(true)
		{
			System.out.printf("Does the student %s have exempted module? Y/N: ", name);
			isExemptedInput = sc.nextLine().toUpperCase();
			// exit
			if(isExemptedInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			if(isExemptedInput.equals("Y")) 
			{
				isExempted = true;
				break;
			} 
			else if(isExemptedInput.equals("N")){
				isExempted = false;
				break;
			}
			else
			{
				System.out.println("Invalid input ");
			}
			
			
		}
		
		int totalNumberAdd;
		int count = 0;
		String courseID;
		List<String> exemptedList = new ArrayList<String>();
		
		if(isExempted == false)
		{
			// no module being exempted
			// add to txt file
			AdminControl.createStudAcc(name, userName,
					hashPassword, acctype,
					matricNo, gender, nationality, maxAU, period, date, email, exemptedList);
		}
		else
		{
			
			// student have module exempted
			while(true)
			{
				
				System.out.printf("Enter the total number of module %s is exempted: ", name);
				
				if(sc.hasNextInt())
				{
					// total number exempted module
					totalNumberAdd = sc.nextInt();
					sc.nextLine(); // Consume newline left-over
					// exit
					if(totalNumberAdd == Container.BREAK_MENU)
					{
						return;
					}
					
					while(count < totalNumberAdd)
					{
						
						// validate courseCode
						boolean isCourseCode = false;
						while (true) {
							System.out.print("Enter the Course Code: ");
							courseID = sc.nextLine().toUpperCase();
							
							//exit
							if(courseID.equals(Integer.toString(Container.BREAK_MENU)))
							{
								return;
							}

							for (int i = 0; i < Container.courseList.size(); i++) 
							{
								if (courseID.equals(Container.courseList.get(i).getCourseID())) 
								{
									isCourseCode = true;
									break;
								} 
								else 
								{
									isCourseCode = false;
								}
							}
							if (isCourseCode) {
								// course ID exist
								//add the course ID to a list
								exemptedList.add(courseID);
								count++;
								System.out.printf("%d Course added \n", count);
								break;
							} 
							else 
							{
								System.out.printf("Invalid Course Code: %s \n", courseID);
							}

						}
						
						
					}
					
					System.out.printf("Successfully added %d exempted Courses.\n", count);
					break;
				}
				else
				{
					System.out.println("Please enter numaric value");
				}

			}
			// add to txt file
			AdminControl.createStudAcc(name, userName,
					hashPassword, acctype,
					matricNo, gender, nationality, maxAU, period, date, email, exemptedList);
			
		}

		System.out.printf("%s added.\n", name);

	}
	
	

}
