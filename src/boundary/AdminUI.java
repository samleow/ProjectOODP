package boundary;

import java.util.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import control.AdminControl;
import control.Container;
import control.HashingPassword;

import entity.Course;
import entity.CoursePlan;
import entity.Date;
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
					System.out.println("\nEnter student matric number: ");
					matricNo = sc.next();
					if(AdminControl.checkIfValidMatricNo(matricNo)) {
						System.out.println("\nEnter Access Date (e.g. YYYY-MM-DD): ");
						String strAccessDate = sc.next();
						
						if(AdminControl.checkIfValidDate(strAccessDate)) {
							StringTokenizer star = new StringTokenizer(strAccessDate , "-");
							accessDate = new Date(Integer.parseInt(star.nextToken().trim()),Integer.parseInt(star.nextToken().trim())
									,Integer.parseInt(star.nextToken().trim()));
							
							System.out.println("\nEnter starting time (e.g. HH:MM): ");
							String strStartTime = sc.next();
							if(AdminControl.checkIfValidTime(strStartTime)) {
								StringTokenizer startT = new StringTokenizer(strStartTime , ":");
								System.out.println("\nEnter ending time (e.g. HH:MM): ");
								String strEndTime = sc.next();
								
								if(AdminControl.checkIfValidTime(strEndTime)) {
									StringTokenizer endT = new StringTokenizer(strEndTime , ":");
									
									accessPeriod = new Period(new Time(Integer.parseInt(startT.nextToken().trim()),Integer.parseInt(startT.nextToken().trim())), 
											new Time(Integer.parseInt(endT.nextToken().trim()),Integer.parseInt(endT.nextToken().trim())), Day.DEFAULT);

									AdminControl.setStudentAccessPeriod(matricNo,accessPeriod,accessDate);
									System.out.println("\nUpdated " + matricNo + " access period");
								} else {
									System.out.println("\nTime is invalid.");
								}
							} else {
								System.out.println("\nTime is invalid.");
							}
						} else {
							System.out.println("\nDate is invalid.");
						}
						
					} else {
						System.out.println("\nStudent does not exist currently.");
					}
					
					break;
				case 2: /* (2) Add a student (matric, name, number, gender, nationality, Max AU, Password  etc) */
					

					addStudentAcc(sc);


					break;
				case 3: /* (3) Add/Update a course (course code, school, its index numbers and vacancy).*/

					
					add_update_course(sc);
					
					
					break;
				case 4: /* (4) Check available slot for an index number (vacancy in a class)*/
					System.out.println("\nEnter the index number for the Course you would like to check: ");
					if(sc.hasNextInt()) { 
						index = sc.nextInt();
						if(AdminControl.checkIfValidIndex(index)) {
							int availableSlots = AdminControl.getNoOfSlotsByCourseIndex(index);
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
	
	
	
	private static void add_update_course(Scanner sc) 
	{
		int choice;
		boolean run = true;
		
		do
		{
			System.out.println("(1) Add a Course");
			System.out.println("(2) Add Index of a course.");
			System.out.println("(3) Set Vacancy for a course.");
			System.out.println("(4) Add Lesson for a course.");
			System.out.println("(5) Update a Course");
			System.out.println("(6) Quit");
			System.out.print("Enter the your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) 
			{
				choice = sc.nextInt();
				switch (choice) {
				
				case 1: //(1) Add a Course
					
					addCourse(sc);
				
				case 2: //(2) Add Index of a course.
				
					addCourseIndex(sc);
				
				case 3: // (3) Set Vacancy for a course.
					
					addVacancy(sc);
					
				case 4: // (4) Add Lesson for a course.
					
					addLesson(sc);
					
				
				}
			} 
			else 
			{
				System.out.println("Please Enter Choice from 1 to 7");
				// Clear sc
				sc.next();
			}
		}while(run);
		
		
	}



	private static void addLesson(Scanner sc) {
		// TODO Auto-generated method stub
		
		
		
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
		
		
		String startTimeInput;
		Time startTime;
		Time endTime;
		Period lessonPeriod;
		
		// Time need to validate
		System.out.print("Enter Student Access Start Time Format(HH:MM): ");
		startTimeInput = sc.nextLine();
		String[] splitStartTime = startTimeInput.split("\\:");


		startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
		
		
		
		System.out.print("Enter Student Access End Time Format(HH:MM): ");
		startTimeInput = sc.nextLine();
		String[] splitEndTime = startTimeInput.split("\\:");
		

		endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));

				
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



	private static void addVacancy(Scanner sc) {
		// TODO Auto-generated method stub
		
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Set Vacancy of a course");
		
		String courseID;
		
		int index;
		
		CoursePlan courseIndex = new CoursePlan();
		
		int totalSlots;
		
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
		
		
		boolean isIndex = false;
		while (true) {
			System.out.print("Enter a new "+ courseID + " index: ");
			index = sc.nextInt();

			for (int i = 0; i < Container.coursePlanList.size(); i++) 
			{
				if (index == Container.coursePlanList.get(i).getIndex()) 
				{
					courseIndex.setIndex(index);
					isIndex = true;
					break;
				} 
				else 
				{
					isIndex = false;
				}
			}
			if (isIndex) {
				
				break;
			} 
			else 
			{
				System.out.printf("Invalid Course Index: %d.\n", index);
				
			}

		}
		
		sc.nextLine(); // Consume newline left-over
		
		// need to do int validation
		System.out.print("Enter total slot for "+ courseID + " index: "+ index + " :");
		totalSlots = sc.nextInt();
		
		sc.nextLine(); // Consume newline left-over
		
		AdminControl.addSlots(totalSlots, courseIndex);
		
	}



	private static void addCourseIndex(Scanner sc) {
		// TODO Auto-generated method stub
		
		sc.nextLine(); // Consume newline left-over
		
		System.out.println("Add Index of a course");
		
		String courseID;
		
		String groupID;
		
		int index;
		

		
		
		
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
		
		
		// validate Group ID
		boolean isGroupID = false;
		while (true) {
			System.out.print("Enter a new "+ courseID + " group: ");
			groupID = sc.nextLine().toUpperCase();

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
			index = sc.nextInt();

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
			else 
			{
				break;
			}

		}
		sc.nextLine(); // Consume newline left-over
		
		
		AdminControl.addIndex(courseID, groupID, index);
		
		
		
	}



	private static void addCourse(Scanner sc) {
		// TODO Auto-generated method stub
		
		sc.nextLine(); // Consume newline left-over

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
		
		
		System.out.print("Enter the Course School: ");
		school = sc.nextLine().toUpperCase();
		
		
		
		// validate courseCode
		boolean isCourseCode = false; 
		while(true)
		{
			System.out.print("Enter the Course Code: ");
			courseID = sc.nextLine().toUpperCase();
			
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
				System.out.println("Invalid input ");
			}
			
		}
		
		// validate Course type
		String c; // temp storage for user input gender
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
		
		
		String hashPassword;
//		try
//		{
//			Container.readStudentFile("StudentAccount.txt", Container.studentList);
//		}catch(IOException e) {};
		
		System.out.print("Enter new student name: ");
		name = sc.nextLine();
		
		// Validate User Name
		boolean isUserName = false;
		while(true)
		{
			System.out.print("Enter new student user name: ");
			userName = sc.nextLine().toLowerCase();
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
		
		System.out.println("Student default Max AU: 23 ");
		maxAU = 23;

		
		// Date and time need validation
		System.out.print("Enter Student Access Date Format(YYYY/MM/DD): ");
		accessDate = sc.nextLine();
		String[] splitDate = accessDate.split("\\/");
		
	
		date = new Date(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
		
		

		System.out.print("Enter Student Access Start Time Format(HH:MM): ");
		accessTime = sc.nextLine();
		String[] splitStartTime = accessTime.split("\\:");


		startTime = new Time(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
		
		
		
		System.out.print("Enter Student Access End Time Format(HH:MM): ");
		accessTime = sc.nextLine();
		String[] splitEndTime = accessTime.split("\\:");
		

		endTime = new Time(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));

		
		period = new Period(startTime, endTime);
		
		
		AdminControl.createStudAcc(name, userName,
				hashPassword, acctype,
				matricNo, gender, nationality, maxAU, period, date);
	}
	
	

}
