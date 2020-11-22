package boundary;

import java.util.*;

import java.util.List;
import java.util.Scanner;

import control.*;
import entity.*;

import entity.Date;
import entity.AllEnums.*;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.text.*;


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


		System.out.println("Welcome " + AdminControl.adminInfo.getName());

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n(1) Edit student access period");
			System.out.println("(2) Add a student (matric, name, number, gender, nationality, Max AU, Password etc)");
			System.out.println("(3) Add/Update a course (course ID, school, its index numbers and vacancy).");
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
					if(Validation.checkIfValidMatricNo(matricNo)) {
						do {
						System.out.printf("\nEnter Access Date (e.g. YYYY-MM-DD) (%d to return): ", Container.BREAK_MENU);
						String strAccessDate = sc.next();
						
						if(strAccessDate.equals(Integer.toString(Container.BREAK_MENU))) {
							break;
						}
						
						if(Validation.checkIfValidDate(strAccessDate)) {
							StringTokenizer star = new StringTokenizer(strAccessDate , "-");
							accessDate = new Date(Integer.parseInt(star.nextToken().trim()),Integer.parseInt(star.nextToken().trim())
									,Integer.parseInt(star.nextToken().trim()));
							
							do {
							System.out.printf("\nEnter starting time (e.g. HH:MM) (%d to return): ", Container.BREAK_MENU);
							String strStartTime = sc.next();
							
							if(strStartTime.equals(Integer.toString(Container.BREAK_MENU))) {
								break;
							}
							
							if(Validation.checkIfValidTime(strStartTime)) {
								do {
								StringTokenizer startT = new StringTokenizer(strStartTime , ":");
								System.out.printf("\nEnter ending time (e.g. HH:MM) (%d to return): ", Container.BREAK_MENU);
								String strEndTime = sc.next();
								
								if(strEndTime.equals(Integer.toString(Container.BREAK_MENU))) {
									break;
								}
								
								if(Validation.checkIfValidTime(strEndTime)) {
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
					
					
				case 3: /* (3) Add/Update a course (course ID, school, its index numbers and vacancy).*/

					
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
							
							if(Validation.checkIfValidIndex(index)) {
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
							
							if(Validation.checkIfValidIndex(index)) {
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
						
						if(Validation.checkIfValidCourseID(capsCourseID)) {
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
					System.out.println("Invalid choice. Please Enter Choice from 1 to 7");
				}
			} else {
				System.out.println("Invalid choice. Please Enter Choice from 1 to 7");
				// Clear sc
				sc.next();
			}
		} while (run);
	}
	
	
	
	private static void add_update_course(Scanner sc) 
	{
		int choice = -1;
		
		
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
					System.out.println("Invalid choice. Please Enter Choice from 1 to 8");
				
				}
			} 
			else 
			{
				System.out.println("Invalid choice. Please Enter Choice from 1 to 8");
				// Clear sc
				sc.next();
			}
		}while(choice != 8);
		
		
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
					
					System.out.print("Enter the index:  (-1 to return): ");
					// user input the index section to modify
					if(sc.hasNextInt()) 
					{
						index = sc.nextInt();
						//exit
						if(index == Container.BREAK_MENU)
						{
							return;
						}
						// check if there exist a index in the coursPlanList
						else if (Validation.checkIfValidIndex(index)) 
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
						// Clear sc
						sc.next();
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
					System.out.print("How many slots will you like to change?: (-1 to return): ");
					if(sc.hasNextInt())
					{
						newTotalSlots = sc.nextInt();
						
						//exit
						if(newTotalSlots == Container.BREAK_MENU)
						{
							return;
						}
						else if(newTotalSlots < numCurrentStud)
						{
							System.out.println("Total vacancy should not be less than the number of students currently registered.");
						}
						else
						{
							break;
						}

					}
					else
					{
						System.out.println("Enter a numeric value ");
						// Clear sc
						sc.next();
					}
				}
				
				sc.nextLine(); // Consume newline left-over
				
				AdminControl.setCourseSlots(index, newTotalSlots);
				
				System.out.println("Successfully updated total vacancy");

	}

	// TODO: validation class and exit -1
	// update lesson
	private static void updateLesson(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Update Lesson");
		
		
		String courseIDInput ="";
		
		
		// validate courseCode
		
		while (true) 
		{
			System.out.print("Enter the Course ID: (-1 to return): ");
			courseIDInput = sc.nextLine().toUpperCase();
			
			//exit
			if(courseIDInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			// check if the courseID exist in the courseList
			if (Validation.checkIfValidCourseID(courseIDInput)) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course ID: %s \n", courseIDInput);
			}

		}
		
		System.out.println("---------------------------------");
		
		// print the list of lesson
		for (int i=0 ;i<Container.lessonList.size(); i++)
		{
			if(courseIDInput.equals(Container.lessonList.get(i).getCourseID()))
			{
				System.out.println(Container.lessonList.get(i).toString());
				System.out.println("---------------------------------");
			}
			
		}
		
		int lessonID;
		String storeSelectedLessonID = "";
		String[] splitLessonIDInfoString;
		
		// validate the correct ID listed in Lesson.txt
		boolean isID = false;
		while(true)
		{
			System.out.print("Enter the ID as shown above you want to modify: (-1 to return): ");
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
						
						storeSelectedLessonID = Container.lessonList.get(i).toString();
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
				// Clear sc
				sc.next();
			}
		
		}
		
		sc.nextLine(); // Consume newline left-over
		
		
		splitLessonIDInfoString = storeSelectedLessonID.split("\\|");
		
		boolean isthereStudent = false;
		
		int choice = -1;
		
		
		String[] splitTimeDay = splitLessonIDInfoString[4].split("\\,");
		String startTimeValue = splitTimeDay[0];
		String endTimeValue = splitTimeDay[1];
		String dayValue = splitTimeDay[2];
		
		String isOnlineValue;
		
		// convert coding names
		if(splitLessonIDInfoString[5].equals("true"))
		{
			isOnlineValue = "YES";
		}
		else
		{
			isOnlineValue = "NO";
		}
		
		String location;
		
		// convert coding names
		if(splitLessonIDInfoString[6].equals("null~null~null"))
		{
			location = "No address detail";
		}
		else
		{
			location = splitLessonIDInfoString[6];
		}
		
		// validate if there is student in the that ID
		// check through if the lesson ID exist in the CoursePlan
		
		// 2 different menu will appear according to whether is there student register in that index
		if(Validation.checkIfIsThereStudent(courseIDInput, lessonID) || Validation.checkIfThereAreLessonIDInCoursePlan(courseIDInput, lessonID)  )
		{
			do 
			{
				System.out.println("There are Students and lesson are tag in ID "+ splitLessonIDInfoString[0] + " you are only allow to modify these 2 option.");
				System.out.println("Which Selection in ID "+ splitLessonIDInfoString[0] + " you want to modify?");
				System.out.println("(1) Is the lesson online: " + isOnlineValue);
				System.out.println("(2) Location: " + location);
				System.out.println("(3) Quit");
				System.out.print("Enter the your choice: ");
				
				// validate the choice input
				if (sc.hasNextInt()) 
				{
					choice = sc.nextInt();
					
					breakSwitch:
					switch (choice) {
					
					case 1: //(1) Is the lesson online: 
						sc.nextLine(); // Consume newline left-over
						
						// this is to clear location if the lesson is online
						Location lessonLocationClean = new Location();
						
						// validate isonline input
						String isOnlineInput; // temp storage for user input gender
						boolean isOnline = false;
						
						while (true) 
						{
							System.out.print("Is the lesson online Y/N: (-1 to return): ");
							isOnlineInput = sc.nextLine().toUpperCase();
							
							//exit
							if(isOnlineInput.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							else if(isOnlineInput.equals("Y")) 
							{
								isOnline = true;
								break;
							} 
							else if(isOnlineInput.equals("N")){
								isOnline = false;
								break;
							}
							else
							{
								System.out.println("Invalid input ");
							}
						}

						AdminControl.setLessonIsOnline(lessonID, isOnline);
						
						System.out.println("Successfully updated lesson status.");
						
						if(isOnline == true)
						{
							isOnlineValue = "YES";
							splitLessonIDInfoString[5] = "true";
							//update the display for Location
							splitLessonIDInfoString[6] = "null~null~null";
							location = splitLessonIDInfoString[6];
						}
						else
						{
							isOnlineValue = "NO";
							splitLessonIDInfoString[5] = "false";
						}
						
						
						break;
						
					case 2:
						
						sc.nextLine(); // Consume newline left-over
						
						Location lessonLocation = new Location();
						//int lessonID = Container.lessonList.size() + 1 ; // increase the index of lesson ID
						
						String locationName;
						String locationExtName;
						String locationAddress;
						
						
						if(splitLessonIDInfoString[5].equals("true"))
						{
							// online lesson do not need location names
							System.out.println("This Lesson is held online.");
						}
						else
						{
							System.out.print("Enter the Location Name (E.g Hardware Lab 2): (-1 to return): ");
							locationExtName = sc.nextLine();
							
							//exit
							if(locationExtName.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							//TODO: auto caps name
							locationExtName = WordUtils.capitalizeFully(locationExtName);
							
							System.out.print("Enter the Location short form name (E.g HWLAB 2): (-1 to return): ");
							locationName = sc.nextLine().toUpperCase();
							
							//exit
							if(locationName.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							System.out.print("Enter the Location Address (E.g N4-01B-05): (-1 to return): ");
							locationAddress = sc.nextLine().toUpperCase();
							
							//exit
							if(locationAddress.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							lessonLocation = new Location(locationName, locationExtName, locationAddress);
							
							AdminControl.setLessonLocation(lessonID, lessonLocation);
							//update the display for Location
							splitLessonIDInfoString[6] = lessonLocation.toString();
							
							System.out.println("Successfully updated lesson address.");
						}
						
						
						
						if(splitLessonIDInfoString[6].equals("null~null~null"))
						{
							location = "No address detail";
						}
						else
						{
							location = splitLessonIDInfoString[6];
						}

						break;
						
					default:
						System.out.println("Invalid choice. Please Enter Choice from 1 to 3");
				
					}
				}
				else
				{
					System.out.println("Invalid choice. Please Enter Choice from 1 to 3");
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
				
				System.out.println("Which Selection in ID "+ splitLessonIDInfoString[0] + " you want to modify?");
				System.out.println("(1) Course ID: " + splitLessonIDInfoString[1]);
				System.out.println("(2) Lesson Type: " + splitLessonIDInfoString[2]);
				System.out.println("(3) Week Type: " + splitLessonIDInfoString[3]);
				System.out.printf("(4) Lesson Period From: %s To: %s  \n", splitTimeDay[0], splitTimeDay[1] );
				System.out.println("(5) Lesson Day: " + splitTimeDay[2]);
				System.out.println("(6) Is the lesson online: " + isOnlineValue);
				System.out.println("(7) Location: " + location);
				System.out.println("(8) Quit");
				System.out.print("Enter the your choice: ");
	
	
				// validate the choice input
				if (sc.hasNextInt()) 
				{
					choice = sc.nextInt();
					
					breakSwitch:
					switch (choice) {
					
					case 1: //(1) Course ID:
						
						sc.nextLine(); // Consume newline left-over
						
						//String newCourseID;
						String existingCourseID;
						
						boolean isNewCode = false;
						
						// validate the CourseID
						while(true)
						{
							System.out.print("Modify Course ID " + splitLessonIDInfoString[1] + " to:  (-1 to return): ");
							existingCourseID = sc.nextLine().toUpperCase();
							
							//exit
							if(existingCourseID.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							// check if there exist courseID in courseList
							if (Validation.checkIfValidCourseID(existingCourseID)) {
								break;
							} 
							else 
							{
								System.out.printf("Course ID %s is Invalid.\n", existingCourseID);
								
							}
						}
						
						AdminControl.setLessonCourseID(lessonID, existingCourseID);
						splitLessonIDInfoString[1] = existingCourseID;
						
						System.out.println("Successfully updated course ID.");
						
						break;
						
					case 2: //(2) Lesson Type:
						
						sc.nextLine(); // Consume newline left-over
						
						String lessonTypeInput;
						LessonType lessonType;
						
						// validate Lesson Type
						//boolean isLessonType = false;
						while (true) 
						{
							System.out.print("Modify lesson type " + splitLessonIDInfoString[2] + " to (e.g Lecture/Lec, Tutorial/Tut, Lab):  (-1 to return):  ");
							lessonTypeInput = sc.nextLine().toUpperCase();
							//exit
							if(lessonTypeInput.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							lessonType = Validation.checkIfValidLessonType(lessonTypeInput);
							
							if(lessonType.equals(LessonType.DEFAULT))
							{
								System.out.println("Invalid input ");
							}
							else
							{
								break;
							}

						}
						
						AdminControl.setLessonLessonType(lessonID, lessonType);
						splitLessonIDInfoString[2] = lessonType.toString();
						
						System.out.println("Successfully updated lesson type.");
						
						break;
						
					case 3: //(3) Week Type:

						sc.nextLine(); // Consume newline left-over
						
						String weekTypeInput;
						WeekType weekType;
						
						// validate Week Type
						while (true) {
							System.out.print("Modify Week Type " + splitLessonIDInfoString[3] + " to (e.g ODD, EVEN, WEEKLY): (-1 to return): ");
							weekTypeInput = sc.nextLine().toUpperCase();
							
							//exit
							if(weekTypeInput.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							weekType = Validation.checkIfValidWeekType(weekTypeInput);
							
							if(weekType.equals(WeekType.DEFAULT))
							{
								System.out.println("Invalid input ");
							}
							else
							{
								break;
							}

						}
						
						AdminControl.setLessonWeeklyType(lessonID, weekType);
						splitLessonIDInfoString[3] = weekType.toString();
						
						System.out.println("Successfully updated week type.");
						
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
							System.out.print("Modify Lesson Start Time from " + splitTimeDay[0] +" to Format(HH:MM): (-1 to return): ");
							timeInput = sc.nextLine();

							// exit
							if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
							{
								break breakSwitch;
								
							} 
							else if (Validation.checkIfValidTime(timeInput)) 
							{
								String[] splitStartTime = timeInput.split("\\:");

								startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
								break;
							} 
							else 
							{
								System.out.println("Invalid Input Time Format(HH:MM) ");
							}
						}
						
						//Update the display of start time
						splitTimeDay[0] = timeInput;
						
						// Validate End time
						while (true) 
						{
							System.out.print("Modify Lesson End Time from " + splitTimeDay[1] +" to Format(HH:MM): (-1 to return): ");
							timeInput = sc.nextLine();

							// exit
							if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
							{
								return;
								
							} 
							else if (Validation.checkIfValidTime(timeInput)) 
							{
								String[] splitEndTime = timeInput.split("\\:");

								endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
								break;
							} 
							else 
							{
								System.out.println("Invalid Input Time Format(HH:MM) ");
							}
						}
						
						//Update the display of end time
						splitTimeDay[1] = timeInput;
						
						// required to convert String from txt file to ENUM
						Day lessonDayDefault = Day.DEFAULT;
						
						lessonDayDefault = Validation.checkIfValidDay(dayValue);
						
						// lesson Period include day constructor
						lessonPeriod = new Period(startTime, endTime, lessonDayDefault);
						
						//update the Lesson Period
						AdminControl.setLessonPeriod(lessonID, lessonPeriod);
						
						System.out.println("Successfully updated lesson period.");
						
						break;
						
					case 5: //(5) Lesson Day:
						
						sc.nextLine(); // Consume newline left-over
						
						String dayInput;
						Day lessonDay = Day.DEFAULT;
						
						// validate Day
						while (true) {
							System.out.print("Enter the Lesson Day (e.g MONDAY/MON, TUESDAY/TUE): ");
							dayInput = sc.nextLine().toUpperCase();
							
							//exit
							if(dayInput.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							lessonDay = Validation.checkIfValidDay(dayInput);
							
							if(lessonDay.equals(Day.DEFAULT))
							{
								System.out.println("Invalid input ");
							}
							else
							{
								break;
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
						
						System.out.println("Successfully updated lesson period.");

						break;
						
					case 6: //(6) Is the lesson online:
						
						sc.nextLine(); // Consume newline left-over
						
						// this is to clear location if the lesson is online
						Location lessonLocationClean = new Location();
						
						// validate isonline input
						String isOnlineInput; // temp storage for user input gender
						boolean isOnline = false;
						
						while (true) 
						{
							System.out.print("Is the lesson online Y/N: (-1 to return): ");
							isOnlineInput = sc.nextLine().toUpperCase();
							
							//exit
							if(isOnlineInput.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							else if(isOnlineInput.equals("Y")) 
							{
								isOnline = true;
								break;
							} 
							else if(isOnlineInput.equals("N")){
								isOnline = false;
								break;
							}
							else
							{
								System.out.println("Invalid input ");
							}
						}

						AdminControl.setLessonIsOnline(lessonID, isOnline);
						
						System.out.println("Successfully updated lesson status.");
						
						if(isOnline == true)
						{
							isOnlineValue = "YES";
							splitLessonIDInfoString[5] = "true";
							//update the display for Location
							splitLessonIDInfoString[6] = "null~null~null";
							location = splitLessonIDInfoString[6];
						}
						else
						{
							isOnlineValue = "NO";
							splitLessonIDInfoString[5] = "false";
						}
						
						
						break;
						
					case 7: //(7) Location:
						
						sc.nextLine(); // Consume newline left-over
						
						Location lessonLocation = new Location();
						//int lessonID = Container.lessonList.size() + 1 ; // increase the index of lesson ID
						
						String locationName;
						String locationExtName;
						String locationAddress;
						
						
						if(splitLessonIDInfoString[5].equals("true"))
						{
							// online lesson do not need location names
							System.out.println("This Lesson is held online.");
						}
						else
						{
							System.out.print("Enter the Location Name (E.g Hardware Lab 2): (-1 to return): ");
							locationExtName = sc.nextLine();
							
							//exit
							if(locationExtName.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							//TODO: auto caps name
							locationExtName = WordUtils.capitalizeFully(locationExtName);
							
							System.out.print("Enter the Location short form name (E.g HWLAB 2): (-1 to return): ");
							locationName = sc.nextLine().toUpperCase();
							
							//exit
							if(locationName.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							System.out.print("Enter the Location Address (E.g N4-01B-05): (-1 to return): ");
							locationAddress = sc.nextLine().toUpperCase();
							
							//exit
							if(locationAddress.equals(Integer.toString(Container.BREAK_MENU)))
							{
								break breakSwitch;
							}
							
							lessonLocation = new Location(locationName, locationExtName, locationAddress);
							
							AdminControl.setLessonLocation(lessonID, lessonLocation);
							//update the display for Location
							splitLessonIDInfoString[6] = lessonLocation.toString();
							
							System.out.println("Successfully updated lesson address.");
						}
						
						if(splitLessonIDInfoString[6].equals("null~null~null"))
						{
							location = "No address detail";
						}
						else
						{
							location = splitLessonIDInfoString[6];
						}
						
						break;
						
					case 8: // (8) Quit

						System.out.println("Quit...");
						break;
						
					default:
						System.out.println("Invalid choice. Please Enter Choice from 1 to 5");
	

					}
				}
				else
				{
					System.out.println("Invalid choice. Please Enter Choice from 1 to 8");
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
		String[] splitCouseInfoString;
		
		// validate Index
		boolean isIndex = false;
		
		while (true) {
			
			System.out.print("Enter the index: (-1 to return): ");
			// user input the index section to modify
			if(sc.hasNextInt()) {
				index = sc.nextInt();
				
				//exit
				if(index == Container.BREAK_MENU)
				{
					return;
				}

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
				System.out.println("Please enter numeric value");
				// Clear sc
				sc.next();
			}

		}
		sc.nextLine(); // Consume newline left-over
		
		


		splitCouseInfoString = storeCourseInfo.split("\\|");

		int choice = -1;

		

		do 
		{

			System.out.println("Which Selection in index " + splitCouseInfoString[2] + " you want to modify?");
			System.out.println("(1) Group ID: " + splitCouseInfoString[1]);
			System.out.println("(2) Index: " + splitCouseInfoString[2]);
			System.out.println("(3) Re-enter Lesson List");
			System.out.println("(4) Add Lesson to List");
			System.out.println("(5) Delete Lesson");
			System.out.println("(6) Quit");
			System.out.print("Enter the your choice: ");

			// validate the choice input
			if (sc.hasNextInt()) 
			{
				choice = sc.nextInt();
				
				breakSwitch:
				switch (choice) 
				{

				case 1: // (1) Update Group Name:
					sc.nextLine(); // Consume newline left-over

					// System.out.print("Modify Course name " + splitString[1] + " to: ");
					String newGroupID;

					// validate Group ID
					boolean isGroupID = false;
					while (true) 
					{
						System.out.print("Modify Group ID " + splitCouseInfoString[1] + " to: (-1 to return): ");
						newGroupID = sc.nextLine().toUpperCase();
						
						//exit
						if(newGroupID.equals(Integer.toString(Container.BREAK_MENU)))
						{
							break breakSwitch;
						}
						// check if there exist a GroupID in coursPlantList
						else if (Validation.checkIfValidGroupID(splitCouseInfoString[0], newGroupID)) 
						{
							System.out.printf("Course Group: %s is used.\n", newGroupID);

						} 
						else 
						{
							break;
						}

					}

					// update txt file
					AdminControl.setCoursePlanGroupID(index, newGroupID);

					splitCouseInfoString[1] = newGroupID; // update the listing
					
					System.out.println("Successfully updated Group ID.");

					break;

				case 2: // (2) Update Index:

					int newIndex;

					// validate Index
					boolean isNewIndex = false;
					while (true) 
					{
						System.out.print("Modify Index " + splitCouseInfoString[2] + " to: (-1 to return): ");

						if (sc.hasNextInt()) 
						{
							newIndex = sc.nextInt();
							
							//exit
							if(newIndex == Container.BREAK_MENU)
							{
								break breakSwitch;
							}
							// check if there exist a index in the coursePlantList
							if(String.valueOf(newIndex).length() == 5)
							{
								if (Validation.checkIfValidIndex(newIndex)) 
								{
									System.out.printf("Course Index: %d is used.\n", newIndex);
	
								} 
								else 
								{
									break;
								}
							}
							else
							{
								System.out.println("Please enter 5 digits");
								
							}

						} 
						else 
						{
							System.out.println("Please enter numeric value");
							// Clear sc
							sc.next();
						}
					}
					sc.nextLine(); // Consume newline left-over

					// update txt file
					AdminControl.setCoursePlanIndex(index, newIndex);

					splitCouseInfoString[2] = Integer.toString(newIndex); // update the listing

					index = newIndex; // update the listing
					
					System.out.println("Successfully updated Index.");

					break;

				case 3: // (3) Re-enter Lesson List

					// check is there student in the course Slots
					if(Validation.checkIfValidIsCourseSlotEmpty(index))
					{
						
						int countNumLessonID = 0;
					
					System.out.println("Re-enter Lesson List to Index: " + splitCouseInfoString[2]);
					System.out.println("Here are the Lesson you can add to Index: " + splitCouseInfoString[2]);
					System.out.println("---------------------------------");
					// Display list of lesson related to the course ID
					for (int i = 0; i < Container.lessonList.size(); i++) 
					{

						if (Container.lessonList.get(i).getCourseID().equals(splitCouseInfoString[0]))
						{
							System.out.println(Container.lessonList.get(i).toString());
							System.out.println("---------------------------------");
							countNumLessonID++;
						}

					}

					int totalNumberAdd;
					int count = 0;
					int lessonIndex;
					CoursePlan addCoursePlanList = new CoursePlan();
					
					
					while(true)
					{
					System.out.print("Enter the toal number of lesson you want to add: (-1 to return): ");
					
					if (sc.hasNextInt()) 
					{
						// total number of the lesson user want to add
						totalNumberAdd = sc.nextInt();
						
						//exit
						if(totalNumberAdd == Container.BREAK_MENU)
						{
							break breakSwitch;
						}
						else if(totalNumberAdd <= countNumLessonID)
						{
							
						
							sc.nextLine(); // Consume newline left-over
	
							// loop the number of times the user want to add
							while (count < totalNumberAdd) 
							{
	
								System.out.print("Enter the lesson ID you want to add: (-1 to return): ");
								// user enter the ID from the lesson.txt
								if (sc.hasNextInt()) 
								{
									lessonIndex = sc.nextInt();
									sc.nextLine(); // Consume newline left-over
									
									//exit
									if(lessonIndex == Container.BREAK_MENU)
									{
										break breakSwitch;
									}
									
									
	
									// search for the ID in the Lesson.txt
									for (int i = 0; i < Container.lessonList.size(); i++) 
									{
										// validate the courseID and the Lesson ID
										if (Container.lessonList.get(i).getCourseID().equals(splitCouseInfoString[0])
												&& Container.lessonList.get(i).getLessonID() == lessonIndex) 
										{
	
											// remove and add the lesson ID to CoursePlan.txt
											addCoursePlanList.getLessons().add(Container.lessonList.get(i));
	
											// increase counter
											count++;
	
											System.out.println(count + " lesson added.");
										}

									}
									if(count < totalNumberAdd)
									{
										
										System.out.println("Invalid lesson ID");
										
									}
									
	
								} 
								else 
								{
									System.out.println("Please enter numeric value");
									// Clear sc
									sc.next();
								}
							}
							
							break;
							
						}
						else
						{
							System.out.println("Total number of lesson cannot be less than the total number of lesson");
						}

					} 
					else 
					{
						System.out.println("Please enter numeric value");
						// Clear sc
						sc.next();
					}
					}

					// update txt file
					AdminControl.setCoursePlanLesson(index, addCoursePlanList.getLessons());

					System.out.println("Successfully overwritten lesson in course plan.");
					}
					else
					{
						System.out.println("There is student registered in this index. No overwriting is allowed.");
					}
					
					break;

				case 4: // (4) Add Lesson to List

					
					int countNumLessonID = 0;
					
					System.out.println("Add Lesson List to Index: " + splitCouseInfoString[2]);
					System.out.println("Here are the Lesson you can add to Index: " + splitCouseInfoString[2]);
					System.out.println("---------------------------------");
					
					// Display list of lesson related to the course ID
					for (int i = 0; i < Container.lessonList.size(); i++) 
					{

						if (Container.lessonList.get(i).getCourseID().equals(splitCouseInfoString[0]))
						{
							System.out.println(Container.lessonList.get(i).toString());
							System.out.println("---------------------------------");
							countNumLessonID++;
						}

					}

					int totalNumberAdd_1;
					int count_1 = 0;
					int lessonIndex_1;
					CoursePlan addCoursePlanList_1 = new CoursePlan();

					while(true)
					{
						System.out.print("Enter the toal number of lesson you want to add: (-1 to return): ");
	
						if (sc.hasNextInt()) {
							// total number of the lesson user want to add
							totalNumberAdd_1 = sc.nextInt();
							
							//exit
							if(totalNumberAdd_1 == Container.BREAK_MENU)
							{
								break breakSwitch;
							}
							else if(totalNumberAdd_1 <= countNumLessonID)
							{
							sc.nextLine(); // Consume newline left-over
	
							// loop the number of times the user want to add
							while (count_1 < totalNumberAdd_1) 
							{
	
								System.out.print("Enter the lesson ID you want to add: (-1 to return): ");
								// user enter the ID from the lesson.txt
								if (sc.hasNextInt()) 
								{
									lessonIndex_1 = sc.nextInt();
									sc.nextLine(); // Consume newline left-over
	
									//exit
									if(lessonIndex_1 == Container.BREAK_MENU)
									{
										break breakSwitch;
									}
									else if(Validation.checkIfExistLessonID(lessonIndex_1, Integer.parseInt(splitCouseInfoString[2])) == false)
									{
									
									
									// search for the ID in the Lesson.txt
									for (int i = 0; i < Container.lessonList.size(); i++) 
									{
										// validate the courseID and the Lesson ID
										if (Container.lessonList.get(i).getCourseID().equals(splitCouseInfoString[0])
												&& Container.lessonList.get(i).getLessonID() == lessonIndex_1) 
										{
											// Note: this is to prevent overwrite of lesson at CoursePlan.txt
											// Extract out the list from Course Plan.txt
											for (int j = 0; j < Container.coursePlanList.size(); j++) 
											{
												if (Container.coursePlanList.get(j).getCourseID().equals(splitCouseInfoString[0])
														&& Container.coursePlanList.get(j).getIndex() == Integer.parseInt(splitCouseInfoString[2])) 
												{
													// Extract out the list of Lesson from CoursePlan.txt
													for (int k = 0; k < Container.coursePlanList.get(j).getLessons().size(); k++) 
													{
														// System.out.println(Container.coursePlanList.get(j).getLessons().get(k));
														// Add the previous Lessons from CoursePlan.txt
														addCoursePlanList_1.getLessons().add(Container.coursePlanList.get(j).getLessons().get(k));
													}
												}
												
											}
											
	
											// add the lesson ID to CoursePlan.txt
											addCoursePlanList_1.getLessons().add(Container.lessonList.get(i));
	
											// increase counter
											count_1++;
	
											System.out.println(count_1 + " lesson added.");
										}
									}
									if(count_1 < totalNumberAdd_1)
									{
										
										System.out.println("Invalid lesson ID");
										
									}
									}
									else
									{
										System.out.printf("%d exist in the Lesson List.\n",lessonIndex_1 );
									}
	
								} 
								else 
								{
									System.out.println("Please enter numeric value");
									// Clear sc
									sc.next();
								}
							}
							
							break;
							}
							else
							{
								System.out.println("Total number of lesson cannot be less than the total number of lesson");
							}
	
						} 
						else 
						{
							System.out.println("Please enter numeric value");
							// Clear sc
							sc.next();
						}
					}

					// update txt file
					AdminControl.setCoursePlanLesson(index, addCoursePlanList_1.getLessons());

					System.out.println("Successfully added lesson to course plan.");
					
					break;

				case 5: // (5) Delete Lesson

					sc.nextLine(); // Consume newline left-over

					// check is there student in the course Slots
					if(Validation.checkIfValidIsCourseSlotEmpty(index))
					{
					
					CoursePlan deleteLesson = new CoursePlan();

					System.out.println("Delete Lesson index " + splitCouseInfoString[2]);

					String answer;
					// valide the answer Y or N
					while (true) 
					{
						System.out.print("Are you sure you want to delete Lesson from index " + splitCouseInfoString[2] + " ?(Y/N): (-1 to return): ");

						answer = sc.nextLine().toUpperCase();
						
						//exit
						if(answer.equals(Integer.toString(Container.BREAK_MENU)))
						{
							break breakSwitch;
						}
						else if (answer.equals("Y")) 
						{
							// send default value of the lesson Array list
							// update txt file
							AdminControl.setCoursePlanLesson(index, deleteLesson.getLessons());
							System.out.println("Lesson deleted in course plan.");
							break;
						} 
						else if (answer.equals("N")) 
						{
							System.out.println("No lesson is deleted in course plan.");
							break;
						} 
						else 
						{
							System.out.println("Please enter Y/N");
						}
					}
					}
					else
					{
						System.out.println("There is student registered in this index. No deleting is allowed.");
					}
					break;

				case 6:
					System.out.println("Quit...");
					
					break;

				default:
					System.out.println("Invalid choice. Please Enter Choice from 1 to 5");

				}
			} 
			else 
			{
				System.out.println("Invalid choice. Please Enter Choice from 1 to 5");
				// Clear sc
				sc.next();
			}

		} while (choice != 6);
		
	}



	// TODO validate Class and exit -1
	// Update Course Information
	private static void updateCourseInfo(Scanner sc) {
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Update a Course Information");
		
		String courseID;
		String storeCourseInfo ="";
		
		
		// validate courseCode
		boolean isCourseID = false;
		while (true) {
			System.out.print("Enter the Course ID: (-1 to return): ");
			courseID = sc.nextLine().toUpperCase();
			
			//exit
			if(courseID.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}

			// check if the courseID exist in the courseList
			for (int i = 0; i < Container.courseList.size(); i++) 
			{
				if (courseID.equals(Container.courseList.get(i).getCourseID())) 
				{
					// store the selected Course information from Course.txt
					storeCourseInfo = Container.courseList.get(i).toString();
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
				System.out.printf("Invalid Course ID: %s \n", courseID);
			}

		}


		String[] splitString = storeCourseInfo.split("\\|");

		int choice = -1;

		

		do 
		{

			System.out.println("Which Selection you want to modify?");
			System.out.println("(1) Course Name: " + splitString[0]);
			System.out.println("(2) School Name: " + splitString[1]);
			System.out.println("(3) Course ID: " + splitString[2]);
			System.out.println("(4) Course AU: " + splitString[3]);
			System.out.println("(5) Course Type: " + splitString[4]);
			System.out.println("(6) Quit");
			System.out.print("Enter the your choice: ");
			
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				breakSwitch: switch (choice) {

				case 1: // (1) Update Course name:

					sc.nextLine(); // Consume newline left-over

					System.out.print("Modify Course name " + splitString[0] + " to: (-1 to return): ");
					String newCourseName;

					newCourseName = sc.nextLine();

					// exit
					if (newCourseName.equals(Integer.toString(Container.BREAK_MENU))) 
					{
						break breakSwitch;
					}
					
					//TODO: auto caps letter
					newCourseName = WordUtils.capitalizeFully(newCourseName);

					// update txt file
					AdminControl.setCourseName(courseID, newCourseName);

					splitString[0] = newCourseName; // update the listing
					
					System.out.println("Update course name successful.");

					break;

				case 2: // (2) Update School Name:

					sc.nextLine(); // Consume newline left-over

					System.out.print("Modify School name " + splitString[1] + " to: (-1 to return): ");

					String newSchoolName;

					newSchoolName = sc.nextLine().toUpperCase();

					// exit
					if (newSchoolName.equals(Integer.toString(Container.BREAK_MENU))) 
					{
						break breakSwitch;
					}

					// update txt file
					AdminControl.setSchoolName(courseID, newSchoolName);

					splitString[1] = newSchoolName; // update the listing
					
					System.out.println("Update school name successful.");

					break;

				case 3: // (3) Update Course ID:

					sc.nextLine(); // Consume newline left-over

					String newCourseID;

					// validate the CourseID
					while (true) 
					{
						System.out.print("Modify Course ID " + splitString[2] + " to: (-1 to return): ");
						newCourseID = sc.nextLine().toUpperCase();

						// exit
						if (newCourseID.equals(Integer.toString(Container.BREAK_MENU)))
						{
							break breakSwitch;
						}
						// check if there exist courseID in courseList
						else if (Validation.checkIfValidCourseID(newCourseID)) 
						{
							System.out.printf("Course ID %s is used.\n", courseID);
						} 
						else 
						{
							break;
						}
					}

					// update txt file
					AdminControl.setCourseID(courseID, newCourseID);

					splitString[2] = newCourseID; // update the listing
					courseID = newCourseID; // update the listing

					System.out.println("Update course ID successful.");
					
					break;

				case 4: // (4) Update Course AU:

					sc.nextLine(); // Consume newline left-over

					int newAU;

					// validate course AU
					while (true) 
					{
						System.out.print("Modify Course AU " + splitString[3] + " to: (-1 to return): ");
						if (sc.hasNextInt()) 
						{
							newAU = sc.nextInt();

							// exit
							if (newAU == Container.BREAK_MENU) 
							{
								break breakSwitch;
							}

							sc.nextLine(); // Consume newline left-over
							break;
						} 
						else 
						{
							System.out.println("Please enter numeric value.");
							sc.next();
						}

					}

					// update txt file
					AdminControl.setCourseAU(courseID, newAU);

					splitString[3] = Integer.toString(newAU); // update the listing

					System.out.println("Update AU successful.");
					
					break;

				case 5: // (5) Update Course Type:

					sc.nextLine(); // Consume newline left-over

					System.out.print("Modify Course Type " + splitString[4] + " to: (-1 to return): ");

					String courseTypeInput;
					CourseType newCourseType;

					// validate Course type
					while (true) 
					{

						courseTypeInput = sc.nextLine().toUpperCase();
						// exit
						if (courseTypeInput.equals(Integer.toString(Container.BREAK_MENU))) 
						{
							break breakSwitch;
						}

						newCourseType = Validation.checkIfValidCourseType(courseTypeInput);

						if (newCourseType.equals(CourseType.DEFAULT)) 
						{
							System.out.println("Invalid input ");
						} 
						else 
						{
							break;
						}

					}

					// update txt file
					AdminControl.setCourseType(courseID, newCourseType);

					splitString[3] = newCourseType.toString(); // update the listing

					System.out.println("Update course type successful.");
					
					break;

				case 6:
					System.out.println("Quit...");
					
					break;

				default:
					System.out.println("Invalid choice. Please Enter Choice from 1 to 5");

				}
			} 
			else 
			{
				System.out.println("Invalid choice. Please Enter Choice from 1 to 5");
				// Clear sc
				sc.next();
			}

		} while (choice != 6);

	}


	// TODO validate Class and exit -1
	// Add Lesson
	private static void addLesson(Scanner sc) {

		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Add a lesson for a course");
		
		String courseID;
		
		
		// validate courseCode
		while (true) {
			System.out.print("Enter the Course ID: (-1 to return): ");
			courseID = sc.nextLine().toUpperCase();
			
			//exit
			if(courseID.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			if (Validation.checkIfValidCourseID(courseID)) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course ID: %s \n", courseID);
			}

		}
		
		String lessonTypeInput;
		LessonType lessonType;
		
		// validate Lesson Type
		//boolean isLessonType = false;
		while (true) {
			System.out.print("Enter the Lesson Type (e.g Lecture/Lec, Tutorial/Tut, Lab): (-1 to return): ");
			lessonTypeInput = sc.nextLine().toUpperCase();

			//exit
			if(lessonTypeInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			lessonType = Validation.checkIfValidLessonType(lessonTypeInput);
			
			if(lessonType.equals(LessonType.DEFAULT))
			{
				System.out.println("Invalid input ");
			}
			else
			{
				break;
			}
		}
		
		
		String weekTypeInput;
		WeekType weekType;
		
		// validate Week Type
		while (true) {
			System.out.print("Enter the Week Type (e.g ODD, EVEN, WEEKLY): (-1 to return): ");
			weekTypeInput = sc.nextLine().toUpperCase();

			//exit
			if(weekTypeInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			weekType = Validation.checkIfValidWeekType(weekTypeInput);
			
			if(weekType.equals(WeekType.DEFAULT))
			{
				System.out.println("Invalid input ");
			}
			else
			{
				break;
			}
		}
		
		
		String timeInput;
		Time startTime;
		Time endTime;
		Period lessonPeriod;
		
		// Validate Start time
		while (true) 
		{
			System.out.print("Enter Lesson Start Time Format(HH:MM): (-1 to return): ");
			timeInput = sc.nextLine();

			// exit
			if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
			{
				return;
				
			} 
			else if (Validation.checkIfValidTime(timeInput)) 
			{
				String[] splitStartTime = timeInput.split("\\:");

				startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
				break;
			} 
			else 
			{
				System.out.println("Invalid Input Time Format(HH:MM) ");
			}
		}
		
		// Validate End time
		while (true) 
		{
			System.out.print("Enter Lesson End Time Format(HH:MM): (-1 to return): ");
			timeInput = sc.nextLine();

			// exit
			if (timeInput.equals(Integer.toString(Container.BREAK_MENU))) 
			{
				return;
				
			} 
			else if (Validation.checkIfValidTime(timeInput)) {
				String[] splitEndTime = timeInput.split("\\:");

				endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
				break;
			} 
			else 
			{
				System.out.println("Invalid Input Time Format(HH:MM) ");
			}
		}
				
		String dayInput;
		Day lessonDay = Day.DEFAULT;
		
		// validate Day
		while (true) {
			System.out.print("Enter the Lesson Day (e.g MONDAY/MON, TUESDAY/TUE): (-1 to return): ");
			dayInput = sc.nextLine().toUpperCase();

			//exit
			if(dayInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			lessonDay = Validation.checkIfValidDay(dayInput);
			
			if(lessonDay.equals(Day.DEFAULT))
			{
				System.out.println("Invalid input ");
			}
			else
			{
				break;
			}
		}

		// lesson Period include day constructor
		lessonPeriod = new Period(startTime, endTime, lessonDay);
		

		// validate isonline input
		String isOnlineInput; // temp storage for user input gender
		boolean isOnline = false;
		
		while (true) 
		{
			System.out.print("Is the lesson online Y/N: (-1 to return): ");
			isOnlineInput = sc.nextLine().toUpperCase();
			
			
			if(isOnlineInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				//exit
				return;
			}
			else if(isOnlineInput.equals("Y")) 
			{
				isOnline = true;
				break;
			} 
			else if(isOnlineInput.equals("N")){
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
			System.out.print("Enter the Location Name (E.g Hardware Lab 2): (-1 to return): ");
			locationExtName = sc.nextLine();
			//exit
			if(locationExtName.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			//TODO: auto caps name
			locationExtName = WordUtils.capitalizeFully(locationExtName);
			
			System.out.print("Enter the Location short form name (E.g HWLAB 2): (-1 to return): ");
			locationName = sc.nextLine().toUpperCase();
			//exit
			if(locationName.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			System.out.print("Enter the Location Address (E.g N4-01B-05): (-1 to return): ");
			locationAddress = sc.nextLine().toUpperCase();
			//exit
			if(locationAddress.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			lessonLocation = new Location(locationName, locationExtName, locationAddress);
			
			AdminControl.addLessonLocation(lessonID, courseID, lessonType, weekType, lessonPeriod, isOnline, lessonLocation);
			
		}
		
		System.out.printf("Succesfully added %s to %s.\n", lessonType.toString(), courseID);
		

	}

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
		while (true) {
			System.out.print("Enter the Course ID: (-1 to return): ");
			courseID = sc.nextLine().toUpperCase();
			
			//exit
			if(courseID.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			//check if the courseID exist in the courseList
			if (Validation.checkIfValidCourseID(courseID)) {
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course ID: %s \n", courseID);
			}

		}
		
		
		// validate Group ID
		while (true) {
			System.out.print("Enter a new "+ courseID + " group ID (e.g SSP1) (-1 to return): ");
			groupID = sc.nextLine().toUpperCase();

			//exit
			if(groupID.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			// Check if the groupID is being used
			if (Validation.checkIfValidGroupID(courseID ,groupID)) {
				System.out.printf("Course Group: %s is used.\n", groupID);
				
			} 
			else 
			{
				break;
			}

		}
		
		// validate Index
		while (true) {
			System.out.print("Enter a new "+ courseID + " index: (-1 to return): ");
			
			if(sc.hasNextInt())
			{
				index = sc.nextInt();
				
				//exit
				if(index == Container.BREAK_MENU)
				{
					return;
				}
				
				if(String.valueOf(index).length() == 5)
				{

					if (Validation.checkIfValidIndex(index)) {
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
				System.out.println("Please enter numeric value ");
				// Clear sc
				sc.next();
			}
		}

		sc.nextLine(); // Consume newline left-over
		
		// validate total slot input
		while(true)
		{
			System.out.print("Enter total slot for "+ courseID + " index: "+ index + " (-1 to return): ");
			if(sc.hasNextInt())
			{
				totalSlots = sc.nextInt();
				
				//exit
				if(totalSlots == Container.BREAK_MENU)
				{
					return;
				}
				break;
			}
			else
			{
				System.out.println("Please enter numeric value");
				// Clear sc
				sc.next();
			}
		}
		
		
		AdminControl.addIndex(courseID, groupID, index);
		
		AdminControl.addSlots(totalSlots, courseIndex);
		
		System.out.printf("Successfully added Index:%d to %s with total vacancy of %d.\n", index, courseID, totalSlots);
		
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
		
		System.out.print("Enter the Course Name: (-1 to return): ");
		courseName = sc.nextLine();
		//exit
		if(courseName.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		//TODO: auto caps letter
		courseName = WordUtils.capitalizeFully(courseName);
		
		System.out.print("Enter the Course School: (-1 to return): ");
		school = sc.nextLine().toUpperCase();
		//exit
		if(school.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		
		// validate courseCode
		//boolean isCourseCode = false; 
		while(true)
		{
			System.out.print("Enter the Course ID: (-1 to return): ");
			courseID = sc.nextLine().toUpperCase();
			
			//exit
			if(courseID.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			// Check if there a used courseID
			if(Validation.checkIfValidCourseID(courseID))
			{
				System.out.printf("Course ID: %s is used.\n", courseID);
			}
			else
			{
				break;
			}
			
		}
		
		
		// validate course AU
		while(true)
		{
			System.out.print("Enter the Course total AU: (-1 to return): ");
			if(sc.hasNextInt())
			{
				au = sc.nextInt();
				//exit
				if(au == Container.BREAK_MENU)
				{
					return;
				}
				sc.nextLine(); // Consume newline left-over
				break;
			}
			else
			{
				System.out.println("Please enter numeric value ");
				// Clear sc
				sc.next();
			}
			
		}
		
		// validate Course type
		String courseTypeInput; // temp storage for user input 
		while (true) 
		{
			System.out.print("Enter Course type: ");
			courseTypeInput = sc.nextLine().toUpperCase();
			
			//exit
			if(courseTypeInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			courseType = Validation.checkIfValidCourseType(courseTypeInput);
			
			if(courseType.equals(CourseType.DEFAULT))
			{
				System.out.println("Invalid input ");
			}
			else
			{
				
				break;
			}
		}
		
		AdminControl.addCourse(courseName, school, courseID, au, courseType);
		
		System.out.printf("Successfully added %s to the course list. \n", courseName);

	}


	//TODO: add student Acc
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

		System.out.print("Enter new student name: (-1 to return): ");
		name = sc.nextLine();
		// exit
		if(name.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		//TODO: auto caps letter
		name = WordUtils.capitalizeFully(name);
		//System.out.println(name);
		
		// Validate Username
		while(true)
		{
			System.out.print("Enter new student username: (-1 to return): ");
			userName = sc.nextLine().toLowerCase();
			
			// exit
			if(userName.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			// check if the student username is being used
			else if(Validation.checkIfValidStudentUserName(userName))
			{
				System.out.printf("Username: %s is used.\n", userName);
			}
			else
			{
				break;
			}

			
		}
		
		System.out.println("Default password: 123");
		hashPassword = AccountControl.encryptThisString(password);
		
		System.out.println("Account Type: STUDENT");
		acctype = AccountType.STUDENT;
		
		// validate Matric number
		//boolean isMatric = false;
		while(true)
		{
			System.out.print("Enter new student matric number: (-1 to return): ");
			matricNo = sc.nextLine().toUpperCase();
			// exit
			if(matricNo.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			//check if the matric number is being used
			else if(Validation.checkIfValidMatricNo(matricNo))
			{
				System.out.printf("Matric Number: %s is used.\n", matricNo);
			}
			else
			{
				break;
			}

			
		}


		// validate gender input
		String genderInput; // temp storage for user input gender
		while (true) 
		{
			System.out.print("Enter new student gender M/F: (-1 to return): ");
			genderInput = sc.nextLine().toUpperCase();
			
			// exit
			if(genderInput.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			
			if(genderInput.equals("M")) {
				gender = Gender.MALE;
				break;
			} 
			else if(genderInput.equals("F")){
				gender = Gender.FEMALE;
				break;
			}
			else
			{
				System.out.println("Invalid input ");
			}
		}
		
		System.out.print("Enter new student nationality: (-1 to return): ");
		nationality = sc.nextLine();
		// exit
		if(nationality.equals(Integer.toString(Container.BREAK_MENU)))
		{
			return;
		}
		
		//TODO: auto caps letter
		nationality = WordUtils.capitalizeFully(nationality);
		
		System.out.println("Student default Max AU: 21 ");
		maxAU = 21;

		
		// Validate date
		while(true)
		{
			// Date and time need validation
			System.out.print("Enter Student Access Date Format(YYYY-MM-DD): (-1 to return): ");
			accessDate = sc.nextLine();
			
			// exit
			if (accessDate.equals(Integer.toString(Container.BREAK_MENU))) 
			{
				return;
			} 
			else if (Validation.checkIfValidDate(accessDate)) 
			{
				String[] splitDate = accessDate.split("\\-");
	
				date = new Date(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]),
						Integer.parseInt(splitDate[2]));
				break;
	
			}
			else
			{
				System.out.println("Invalid Input Date Format(YYYY-MM-DD)");
			}
		
		}
		
		// Validate Start time
		while(true)
		{
			System.out.print("Enter Student Access Start Time Format(HH:MM): (-1 to return): ");
			accessTime = sc.nextLine();

			// exit
			if(accessTime.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			else if(Validation.checkIfValidTime(accessTime))
			{
				String[] splitStartTime = accessTime.split("\\:");
				startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
				break;
			}
			else
			{
				System.out.println("Invalid Input Time Format(HH:MM)");
			}
		}
		
		// Validate End time
		while(true)
		{
			System.out.print("Enter Student Access End Time Format(HH:MM): (-1 to return): ");
			accessTime = sc.nextLine();
			
			// exit
			if(accessTime.equals(Integer.toString(Container.BREAK_MENU)))
			{
				return;
			}
			else if(Validation.checkIfValidTime(accessTime))
			{
				String[] splitEndTime = accessTime.split("\\:");
				endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
				break;
			}
			else
			{
				System.out.println("Invalid Input Time Format(HH:MM)");
			}
		}
		
		period = new Period(startTime, endTime);
		
		
		
		// Add student Email
		//System.out.print("Enter new student email: ");
		//email = sc.nextLine();
		email = "ntu20.cz2002@gmail.com";
		System.out.printf("Auto Genternated Email: %s\n",email);
		
		
		
		//Validate if the student have exempted module
		String isExemptedInput; // temp storage for user input gender
		boolean isExempted = false;
		
		while(true)
		{
			System.out.printf("Does the student %s have exempted module? Y/N: (-1 to return): ", name);
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
				
				System.out.printf("Enter the total number of module %s is exempted: (-1 to return): ", name);
				
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
						
						while (true) {
							System.out.print("Enter the Course ID: ");
							courseID = sc.nextLine().toUpperCase();
							
							//exit
							if(courseID.equals(Integer.toString(Container.BREAK_MENU)))
							{
								return;
							}
							else if (Validation.checkIfValidCourseID(courseID)) {
								// course ID exist
								//add the course ID to a list
								exemptedList.add(courseID);
								count++;
								System.out.printf("%d Course added \n", count);
								break;
							} 
							else 
							{
								System.out.printf("Invalid Course ID: %s \n", courseID);
							}

						}
						
						
					}
					
					System.out.printf("Successfully added %d exempted Courses.\n", count);
					break;
				}
				else
				{
					System.out.println("Please enter numeric value");
					// Clear sc
					sc.next();
				}

			}
			// add to txt file
			AdminControl.createStudAcc(name, userName,
					hashPassword, acctype,
					matricNo, gender, nationality, maxAU, period, date, email, exemptedList);
			
		}

		System.out.printf("Successfully added %s to the student list.\n", name);

	}

}
