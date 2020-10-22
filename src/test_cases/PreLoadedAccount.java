package test_cases;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import entity.Course;
import entity.CoursePlan;
import entity.CourseSlots;
import entity.Coordinate;
import entity.Lesson;
import entity.Location;
import entity.Period;
import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;
import control.HashingPassword;
import entity.AllEnums;

public class PreLoadedAccount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * UserAccount[] listUser = getUser();
		 * 
		 * PrintWriter createTxt = createFile("User.txt");
		 * 
		 * for (int i = 0; i < listUser.length; i++) { createUser(listUser[i],
		 * createTxt); }
		 * 
		 * // Closes the connection to the PrintWriter
		 * 
		 * createTxt.close();
		 * 
		 * getFileInfo();
		 */
	}

	public PreLoadedAccount() {
		// Testing
		
		
//		  Lesson[] LessonData =
//		  getLesson(); CoursePlan[] CoursePlanData = getCoursePlan(); CourseSlots[]
//		  CourseSlotsData = getCourseSlots(); Course[] CourseData = getCourse();
//		 
		StudentAccount[] listUser = getUser();
		PrintWriter createUser = createFile("User.txt");
		for (int i = 0; i < listUser.length; i++) { 
			 createUser(listUser[i],createUser); 
		}
		createUser.close(); // Closes the connection to the PrintWriter
		
		
		Coordinate[] CoordinateData = getCoord();
		PrintWriter createCoord = createFile("Coordinate.txt");
		for (int i = 0; i < CoordinateData.length; i++) {
			createCoord(CoordinateData[i], createCoord);
		}
		createCoord.close();
		
		Location[] LocationData = getLocation();
		PrintWriter createLocation = createFile("Location.txt");
		for (int i = 0; i < LocationData.length; i++) {
			createLocation(LocationData[i], createLocation);
		}
		createLocation.close();
		
		Period[] PeriodData = getPeriod();
		PrintWriter createPeriod = createFile("Period.txt");
		for (int i = 0; i < PeriodData.length; i++) {
			createPeriod(PeriodData[i], createPeriod);
		}
		createPeriod.close();
		
		Lesson[] LessonData = getLesson();
		PrintWriter createLesson = createFile("Lesson.txt");
		for (int i = 0; i < LessonData.length; i++) {
			createLesson(LessonData[i], createLesson);
		}
		createLesson.close();
		
		CoursePlan[] CoursePlanData = getCoursePlan();
		PrintWriter createCoursePlan = createFile("CoursePlan.txt");
		for (int i = 0; i < CoursePlanData.length; i++) {
			createCoursePlan(CoursePlanData[i], createCoursePlan);
		}
		createCoursePlan.close();

		
		
		//getFileInfo();
	}

	// class that defines all the fields for User Account

	private static class StudentAccount {

		public String userName, password;

		// constructor that's called when a customer is made

		public StudentAccount(String userName, String password) {
			this.userName = userName;
			this.password = password;
		}
	}

	// Creates an array of preload user account

	private static StudentAccount[] getUser() {
		StudentAccount[] userAcc = new StudentAccount[3];
		userAcc[0] = new StudentAccount("jo", HashingPassword.encryptThisString("123"));
		userAcc[1] = new StudentAccount("test123", HashingPassword.encryptThisString("zxc"));
		userAcc[2] = new StudentAccount("admin", HashingPassword.encryptThisString("admin"));
		return userAcc;
	}

	private static Coordinate[] getCoord() {
		Coordinate[] coordinateList = new Coordinate[4];
		coordinateList[0] = new Coordinate(1.3467, 103.6828);
		coordinateList[1] = new Coordinate(1.3483, 103.6831);
		coordinateList[2] = new Coordinate(1.3437, 103.6824);
		coordinateList[3] = new Coordinate(1.3460, 103.6828);
		return coordinateList;
	}
	
	private static Location[] getLocation() {
		Location[] locationList = new Location[4];
		Coordinate[] CoordinateData = getCoord();
		//For Algo Lab and Tut location
		locationList[0] = new Location("HWLab2", "Hardware Lab 2", "N4-01B-05", CoordinateData[0]);
		locationList[1] = new Location("TR+33", "Tutorial Room + 33", "NS4-05-93", CoordinateData[1]);
		
		//For OOP Lab and Tut location
		locationList[2] = new Location("SPL", "Software Project Lab", "NS4-05-93", CoordinateData[2]);
		locationList[3] = new Location("LHN-TR+17", "Learning Hub North Tutorial Room + 17", "NS4-05-94", CoordinateData[3]);
		return locationList;
	}
	
	private static Period[] getPeriod() {
		Period[] PeriodList = new Period[8];
		//For Algo Timetable
		PeriodList[0] = new Period("10:30", "12:30", AllEnums.Day.MONDAY);
		PeriodList[1] = new Period("12:30", "13:30", AllEnums.Day.MONDAY);
		PeriodList[2] = new Period("12:30", "13:30", AllEnums.Day.WEDNESDAY);
		PeriodList[3] = new Period("10:30", "11:30", AllEnums.Day.FRIDAY);
		
		//For OOP Timetable
		PeriodList[4] = new Period("09:30", "10:30", AllEnums.Day.TUESDAY);
		PeriodList[5] = new Period("10:30", "12:30", AllEnums.Day.WEDNESDAY);
		PeriodList[6] = new Period("15:30", "16:30", AllEnums.Day.WEDNESDAY);
		PeriodList[7] = new Period("11:30", "12:30", AllEnums.Day.FRIDAY);
		return PeriodList;
	}
	
	private static Lesson[] getLesson() {
		Lesson[] lessonList = new Lesson[8];
		Period[] PeriodData = getPeriod();
		Location[] LocationData = getLocation();
		//Algo
		lessonList[0] = new Lesson("CZ2001", AllEnums.LessonType.LAB, AllEnums.WeekType.EVEN, PeriodData[0], false, LocationData[0]);
		lessonList[1] = new Lesson("CZ2001", AllEnums.LessonType.LECTURE, AllEnums.WeekType.EVEN, PeriodData[1], true, null);
		lessonList[2] = new Lesson("CZ2001", AllEnums.LessonType.TUTORIAL, AllEnums.WeekType.WEEKLY, PeriodData[2], false, LocationData[1]);
		lessonList[3] = new Lesson("CZ2001", AllEnums.LessonType.LECTURE, AllEnums.WeekType.ODD, PeriodData[3], true, null);
		
		//OOP
		lessonList[4] = new Lesson("CZ2002", AllEnums.LessonType.LECTURE, AllEnums.WeekType.WEEKLY, PeriodData[4], true, null);
		lessonList[5] = new Lesson("CZ2002", AllEnums.LessonType.LAB, AllEnums.WeekType.WEEKLY, PeriodData[5], false, LocationData[2]);
		lessonList[6] = new Lesson("CZ2002", AllEnums.LessonType.TUTORIAL, AllEnums.WeekType.WEEKLY, PeriodData[6], false , LocationData[3]);
		lessonList[7] = new Lesson("CZ2002", AllEnums.LessonType.LECTURE, AllEnums.WeekType.WEEKLY, PeriodData[7], true, null);
		return lessonList;
	}
	
	
	private static CoursePlan[] getCoursePlan() {
		CoursePlan[] CoursePlanList = new CoursePlan[2];
		Lesson[] LessonData = getLesson();
		CoursePlanList[0] = new CoursePlan("CZ2001", "SSP2", 10192);
		CoursePlanList[1] = new CoursePlan("CZ2002", "SS12", 10209);
		
		List<Lesson> temp = new ArrayList<Lesson>();
		for(int i = 0; i<CoursePlanList.length; i++) {
			for(int j = 0; j<LessonData.length; j++) {
				if(CoursePlanList[i].getCourseID() == LessonData[j].getCourseID()) {
					temp.add(LessonData[j]);
				}
			}
			CoursePlanList[i].setLessons(temp);
			System.out.println(CoursePlanList[i].getLessons());
			temp.clear();
		}
		System.out.println(CoursePlanList[0].getLessons());
		return CoursePlanList;
	}
	


	// Create the file and the PrintWriter that will write to the file

	private static PrintWriter createFile(String fileName) {
		try {

			// Creates a File object that allows you to work with files on the hard drive

			File listOfNames = new File(fileName);

			// FileWriter is used to write streams of characters to a file
			// BufferedWriter gathers a bunch of characters and then writes
			// them all at one time (Speeds up the Program)
			// PrintWriter is used to write characters to the console, file

			PrintWriter infoToWrite = new PrintWriter(new BufferedWriter(new FileWriter(listOfNames)));
			return infoToWrite;
		}

		// You have to catch this when you call FileWriter

		catch (IOException e) {

			System.out.println("An I/O Error Occurred");

			// Closes the program

			System.exit(0);

		}
		return null;

	}

	// Create a string with the customer info and write it to the file

	private static void createUser(StudentAccount user, PrintWriter userOutput) {
		// Create the String that contains the user info
		// "|" mean for separating the username and password in the text file
		String userInfo = user.userName + "|" + user.password;

		// Writes the string to the file
		userOutput.println(userInfo);
	}

	private static void createCoord(Coordinate coord, PrintWriter userOutput) {
		String data = coord.getLatitude() + "|" + coord.getLongtitude();
		userOutput.println(data);
	}
	
	private static void createLocation(Location location, PrintWriter userOutput) {
		String data = location.getName() + "|" + location.getNameExtended() + "|" + location.getAddress() 
		+ "|" + location.getCoord().getLatitude() + "|" + location.getCoord().getLatitude();
		userOutput.println(data);
	}
	
	private static void createPeriod(Period period, PrintWriter userOutput) {
		String data = period.getStartTime() + "|" + period.getEndTime() + "|" + period.getDay();
		userOutput.println(data);
	}
	
	private static void createLesson(Lesson lesson, PrintWriter userOutput) {
		String data;
		if(lesson.getLocation() != null) {
			data = lesson.getCourseID() + "|" + lesson.getType() + "|" + lesson.getWeekly()
			+ "|" + lesson.getLessonPeriod().getStartTime() + "|" + lesson.getLessonPeriod().getEndTime() + "|" + lesson.getLessonPeriod().getDay()
			+ "|" + lesson.getIsOnline() + "|" + lesson.getLocation().getName() + "|" + lesson.getLocation().getNameExtended()
			+ "|" + lesson.getLocation().getAddress();
		}
		else {
			data = lesson.getCourseID() + "|" + lesson.getType() + "|" + lesson.getWeekly()
			+ "|" + lesson.getLessonPeriod().getStartTime() + "|" + lesson.getLessonPeriod().getEndTime() + "|" + lesson.getLessonPeriod().getDay()
			+ "|" + lesson.getIsOnline() + "|" + null + "|" + null
			+ "|" + null;
		}
		userOutput.println(data);
	}
	
	
	private static void createCoursePlan(CoursePlan courseplan, PrintWriter userOutput) {
		String data = courseplan.getCourseID() + "|" + courseplan.getGroupID() + "|" + courseplan.getIndex()
		+ "|" + courseplan.getLessons();
		userOutput.println(data);
	}


	// Read info from the file and write it to the screen

	private static void getFileInfo() {

		System.out.println("Info Written to File\n");

		// Open a new connection to the file

		File listOfNames = new File("User.txt");

		try {

			// FileReader reads character files
			// BufferedReader reads as many characters as possible

			BufferedReader getInfo = new BufferedReader(new FileReader(listOfNames));

			// Reads a whole line from the file and saves it in a String

			String userInfo = getInfo.readLine();

			// readLine returns null when the end of the file is reached

			while (userInfo != null) {

				// System.out.println(userInfo);

				// Break lines into pieces

				String[] indivUserData = userInfo.split("\\|"); // <-- Java | have issue

				System.out.print("Username " + indivUserData[0] + " and password is " + indivUserData[1] + "\n");

				userInfo = getInfo.readLine();

			}

		}

		// Can be thrown by FileReader

		catch (FileNotFoundException e) {

			System.out.println("Couldn't Find the File");
			System.exit(0);
		}

		catch (IOException e) {

			System.out.println("An I/O Error Occurred");
			System.exit(0);

		}

	}

}
