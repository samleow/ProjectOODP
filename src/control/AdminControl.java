package control;

import java.io.IOException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

import entity.Date;

import entity.Course;
import entity.CoursePlan;
import entity.CourseSlots;
import entity.Date;
import entity.Lesson;
import entity.Location;

import entity.Period;
import entity.Student;

import test_cases.CreateStudentAccount;

import entity.AllEnums.AccountType;
import entity.AllEnums.CourseType;
import entity.AllEnums.Gender;
import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;

import control.Container;

public class AdminControl {
//	, Period period, Date date
//	10:30,14:30,DEFAULT
//	2020,7,25
	// Admin feature: 1
	public static void setStudentAccessPeriod(String matricNo, Period period, Date date) {
		
		for(int i = 0; i < Container.studentList.size(); i++) {
			if(Container.studentList.get(i).getMatricNo().equals(matricNo)) {
				//System.out.println(Container.studentList.get(i).getName());
				Container.studentList.get(i).setAccessDate(date);
				Container.studentList.get(i).setAccessPeriod(period);
			}
			if(i==0) {
				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",true);
			} else {
				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",false);
			}
			
		}
	}
	
	public static boolean checkIfValidDate(String strAccessDate) {
		
		if(!strAccessDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return false;
		}
		
		StringTokenizer star = new StringTokenizer(strAccessDate , "-");
//		Date accessDate = new Date(Integer.parseInt(star.nextToken().trim()),Integer.parseInt(star.nextToken().trim())
//				,Integer.parseInt(star.nextToken().trim()));
		String year = star.nextToken().trim();
		String month = star.nextToken().trim();
		String day = star.nextToken().trim();
//		boolean check = false;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		String currentDate = dtf.format(now);
		StringTokenizer currentStar = new StringTokenizer(currentDate, "-");
		
		
		String currentYear = currentStar.nextToken().trim();
		String currentMonth = currentStar.nextToken().trim();
		String currentDay = currentStar.nextToken().trim();
		
		if(currentMonth.length() < 2) {
			currentMonth = "0" + currentMonth;
        }
        if(currentDay.length() < 2) {
        	currentDay = "0" + currentDay;
        }
		
		int intCurrentDate = Integer.parseInt(currentYear+currentMonth+currentDay);

		
		try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(strAccessDate);

            if(month.length() < 2) {
            	month = "0" + month;
            }
            if(day.length() < 2) {
            	day = "0" + day;
            }
            int intAccessDate = Integer.parseInt(year+month+day);
            //System.out.println(intAccessDate);
            //int length = (int)(Math.log10(intAccessDate)+1);
            
            if(intAccessDate < intCurrentDate) {
            	System.out.println("Date entered cannot be in the past.");
            	return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
		}
	}
	
	public static boolean checkIfValidTime(String time) {
		boolean check = true;
		if (!time.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
//		     System.out.println("Invalid time string: " + time);
			check= false;
		} 
		
		return check;
	}
	
	public static boolean checkIfValidMatricNo(String matricNo) {
		boolean check = false;
		for(int i = 0; i < Container.studentList.size(); i++) {
			if(Container.studentList.get(i).getMatricNo().equals(matricNo)) {
				check = true;
				break;
			}
		}
		
		return check;
	}
	
	//Admin Feature: 2
	public static void createStudAcc(String name, String userName,
			String password, AccountType type,
			String matricNo, Gender gender,

//			String nationality, int maxAU, Period accessPeriod, Date accessDate ) // please help to add gender etc
//	{
//    	CreateStudentAccount studDB = new CreateStudentAccount();
//    	String filename = "StudentAccount.txt" ;
//
//		try {
//			// read file containing Student records and store into a list
//			ArrayList studList = studDB.readStudents(filename);
			
			//Displaying the current data in the StudentAccount.txt
			/*
			 * for (int i = 0 ; i < studList.size() ; i++) { Student student =
			 * (Student)studList.get(i); System.out.println("Matric No " +
			 * student.getMatricNo()); System.out.println("Name " + student.getName());
			 * System.out.println("Gender " + student.getGender());
			 * System.out.println("Nationality " + student.getNationality());
			 * System.out.println("Max AU " + student.getMaxAU()); }
			 */
			
			
			//create student obj to add into the studList

			String nationality, int maxAU, Period accessPeriod, Date accessDate) {

		Student createStudAcc = new Student(name, userName, password, type, matricNo, gender, nationality, maxAU,
				accessPeriod, accessDate);

		createStudAcc.writeDataToFile("StudentAccount.txt", false);

		try {
			Container.readStudentFile("StudentAccount.txt", Container.studentList);
		} 
		catch (IOException e) {
		}
		
		for (int i = 0; i < Container.studentList.size(); i++) {
			// System.out.println(Container.studentList.get(i).toString());

		}
		
		
		
//    	CreateStudentAccount studDB = new CreateStudentAccount();
//    	String filename = "StudentAccount.txt" ;
//
//		try {
//			// read file containing Student records and store into a list
//			ArrayList studList = studDB.readStudents(filename);
//			
//			//Displaying the current data in the StudentAccount.txt
//			/*
//			 * for (int i = 0 ; i < studList.size() ; i++) { Student student =
//			 * (Student)studList.get(i); System.out.println("Matric No " +
//			 * student.getMatricNo()); System.out.println("Name " + student.getName());
//			 * System.out.println("Gender " + student.getGender());
//			 * System.out.println("Nationality " + student.getNationality());
//			 * System.out.println("Max AU " + student.getMaxAU()); }
//			 */
//			
//			
//			//create student obj to add into the studList

//			Student addStud = new Student(name, userName,
//					password, type,
//					matricNo, gender,
//					nationality, maxAU);
//			
//			studList.add(addStud);
//			
//			// resave the whole studList to the txt file
//			studDB.saveStudent(filename, studList);


//			
//		}
//		catch (IOException e) {
//			System.out.println("IOException > " + e.getMessage());
//		}
		
	}
	
	// Admin Feature: 3 Add course
	public static void addCourse(String name, String school, String courseID, int courseAU, CourseType courseType)
	{
		//System.out.println(name);
		
		Course add_Course = new Course(name, school, courseID, courseAU, courseType);
		add_Course.writeDataToFile("Course.txt", false);
		

		for (int i=0 ;i<Container.courseList.size(); i++)
		{
			System.out.println(Container.courseList.get(i).toString());
			
		}
	}
	
	// Admin Feature: 3 Add index
	public static void addIndex(String courseID, String groupID, int index)
	{
		
		
		CoursePlan add_index = new CoursePlan(courseID, groupID, index);
		add_index.writeDataToFile("CoursePlan.txt", false);
		

		for (int i=0 ;i<Container.coursePlanList.size(); i++)
		{
			System.out.println(Container.coursePlanList.get(i).toString());

			
		}
	}
	
	// Admin Feature: 3 Add Slots
	public static void addSlots(int totalSlots, CoursePlan courseIndex)
	{
		
		
		CourseSlots add_Slots = new CourseSlots(totalSlots, courseIndex);
		add_Slots.writeDataToFile("CourseSlots.txt", false);
		

		for (int i=0 ;i<Container.courseSlotsList.size(); i++)
		{
			System.out.println(Container.courseSlotsList.get(i).toString());
			
		}
	}
	
	
	
	// Admin Feature: 3 Add Lesson
	public static void addLessonLocation(int lessonID, String courseID, LessonType type, WeekType weekly,
			Period lessonPeriod, boolean isOnline, Location location)
	{
		
		
		Lesson add_LessonLocation = new Lesson(lessonID, courseID, type, weekly,
			lessonPeriod, isOnline, location);
		add_LessonLocation.writeDataToFile("Lesson.txt", false);
		

		for (int i=0 ;i<Container.lessonList.size(); i++)
		{
			System.out.println(Container.lessonList.get(i).toString());
			
		}
	}
	
	
	// Admin Feature: 3 Update Course Name
	public static void setCourseName(String courseID, String courseName) {
		
		for(int i = 0; i < Container.courseList.size(); i++) {
			if(Container.courseList.get(i).getCourseID().equals(courseID)) {
//				System.out.println(Container.courseList.get(i).getName());
//				System.out.println(i);
				Container.courseList.get(i).setName(courseName);
			}
			if(i==0) {
				Container.courseList.get(i).writeDataToFile("Course.txt",true);
			} else {
				Container.courseList.get(i).writeDataToFile("Course.txt",false);
			}
			
		}
	}
	
	
	// Admin Feature: 3 Update School Name
	public static void setSchoolName(String courseID, String schoolName) {
		
		for(int i = 0; i < Container.courseList.size(); i++) {
			if(Container.courseList.get(i).getCourseID().equals(courseID)) {

				Container.courseList.get(i).setSchool(schoolName);
			}
			if(i==0) {
				Container.courseList.get(i).writeDataToFile("Course.txt",true);
			} else {
				Container.courseList.get(i).writeDataToFile("Course.txt",false);
			}
			
		}
	}
	
	
	// Admin Feature: 3 Update CourseID 
	public static void setCourseID(String courseID, String newCourseID) {
		
		// update Course.txt
		for(int i = 0; i < Container.courseList.size(); i++) {
			if(Container.courseList.get(i).getCourseID().equals(courseID)) {

				Container.courseList.get(i).setCourseID(newCourseID);
			}
			if(i==0) {
				Container.courseList.get(i).writeDataToFile("Course.txt",true);
			} else {
				Container.courseList.get(i).writeDataToFile("Course.txt",false);
			}
			
		}
		
		// update CoursePlan.txt
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getCourseID().equals(courseID)) {

				Container.coursePlanList.get(i).setCourseID(newCourseID);
			}
			if(i==0) {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",false);
			}
			
		}
		
		// update Lesson.txt
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getCourseID().equals(courseID)) {

				Container.lessonList.get(i).setCourseID(newCourseID);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile("Lesson.txt",true);
			} else {
				Container.lessonList.get(i).writeDataToFile("Lesson.txt",false);
			}
			
		}
	}
	
	// Admin Feature: 3 Update Course AU
	public static void setCourseAU(String courseID, int newAU) {
		
		for(int i = 0; i < Container.courseList.size(); i++) {
			if(Container.courseList.get(i).getCourseID().equals(courseID)) {

				Container.courseList.get(i).setCourseAU(newAU);
			}
			if(i==0) {
				Container.courseList.get(i).writeDataToFile("Course.txt",true);
			} else {
				Container.courseList.get(i).writeDataToFile("Course.txt",false);
			}
			
		}
	}
	
	// Admin Feature: 3 Update Course type
	public static void setCourseType(String courseID, CourseType newCourseType) {
		
		for(int i = 0; i < Container.courseList.size(); i++) {
			if(Container.courseList.get(i).getCourseID().equals(courseID)) {

				Container.courseList.get(i).setCourseType(newCourseType);
			}
			if(i==0) {
				Container.courseList.get(i).writeDataToFile("Course.txt",true);
			} else {
				Container.courseList.get(i).writeDataToFile("Course.txt",false);
			}
			
		}
	}
	
	
	// Admin Feature: 3 Update CoursePlan GroupID
	public static void setCoursePlanGroupID(int index, String newGroupID) {
		
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getIndex() == index) {

				Container.coursePlanList.get(i).setGroupID(newGroupID);
			}
			if(i==0) {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",false);
			}
			
		}
	}
	
	
	// Admin Feature: 3 Update CoursePlan Index
	public static void setCoursePlanIndex(int index, int newIndex) {
		
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getIndex() == index) {

				Container.coursePlanList.get(i).setIndex(newIndex);
			}
			if(i==0) {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",false);
			}
			
		}
		
		// update CourseSlots.txt
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				//System.out.println("TEST");
				Container.courseSlotsList.get(i).getCoursePlan().setIndex(newIndex);
			}
			if(i==0) {
				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",true);
			} else {
				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",false);
			}
			
		}
	}
	
	// Admin Feature: 3 Update CoursePlan Lesson List
	public static void setCoursePlanLesson(int index, List<Lesson> newLesson) {
		
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getIndex() == index) {

				Container.coursePlanList.get(i).setLessons(newLesson);
			}
			if(i==0) {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile("CoursePlan.txt",false);
			}
			
		}
	}

	// Admin Feature: 4
	public static int getNoOfSlotsByCourseIndex(int index) {
		int availableSlots = -1;
		
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				availableSlots = Container.courseSlotsList.get(i).getTotalSlots() - Container.courseSlotsList.get(i).getSlotList().size();
			}
		}
		
		return availableSlots;
	}
	
	public static boolean checkIfValidIndex(int index) { // not sure if there's a better way to do this
		
		boolean check = false;
		for(int i = 0; i < Container.coursePlanList.size(); i++) { // took the index from coursePlanList
			if(Container.coursePlanList.get(i).getIndex() == index) {
				check = true;
				break;
			}
		}
		
		return check;
	}
	
	// Admin Feature: 5
	public static List<List<String>> getStudentListByCourseIndex(int index) {
		
		List<String> listString = new ArrayList<String>();
		List<List<String>> listOfListString = new ArrayList<>();
		
		for(int i = 0; i < Container.studentList.size(); i++) {
			for(int k = 0; k < Container.studentList.get(i).getCoursePlan().size();k++) {
				if (Container.studentList.get(i).getCoursePlan().get(k).getIndex() == index) {
					listString.add(Container.studentList.get(i).getName());
					listString.add(Container.studentList.get(i).getGender().toString());
					listString.add(Container.studentList.get(i).getNationality());
				}
			}
			if(!listString.isEmpty()) {
				listOfListString.add(new ArrayList<String>(listString));
			}
			listString.clear();
		}
//		listOfListString.get(i).removeAll(Collections.singleton("[]"));
		
		return listOfListString;
	}
	
	// Admin Feature: 6
	public static List<List<String>> getStudentListByCourseID(String courseID){
		
		List<String> listString = new ArrayList<String>();
		List<List<String>> listOfListString = new ArrayList<>();
		
		for(int i = 0; i < Container.studentList.size(); i++) {
			for(int k = 0; k < Container.studentList.get(i).getCoursePlan().size();k++) {
				if(Container.studentList.get(i).getCoursePlan().get(k).getCourseID().equals(courseID)) {
					listString.add(Container.studentList.get(i).getName());
					listString.add(Container.studentList.get(i).getGender().toString());
					listString.add(Container.studentList.get(i).getNationality());
				}
			}
			if(!listString.isEmpty()) {
				listOfListString.add(new ArrayList<String>(listString));
			}
			listString.clear();
		}
		
		return listOfListString;
	}
	
	public static boolean checkIfValidCourseID(String courseID) {
		boolean check = false;
		
		for(int i = 0; i < Container.courseList.size(); i++) {
			if(Container.courseList.get(i).getCourseID().equals(courseID)) {
				check  = true;
				break;
			}
		}
		
		return check;
	}

}
