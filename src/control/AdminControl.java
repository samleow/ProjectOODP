package control;

import java.io.IOException;
import java.util.*;

import entity.*;

import entity.AllEnums.AccountType;
import entity.AllEnums.CourseType;
import entity.AllEnums.Gender;
import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;
import entity.Date;

public class AdminControl {
	
	public static Admin adminInfo;
	
	public static void saveAdminInfo(String userName)
	{
		for(int i = 0; i < Container.adminList.size(); i++) {
			if(Container.adminList.get(i).getUserName().equals(userName)) {
				adminInfo = Container.adminList.get(i);
			}
		}
	}
	
	public static int getLessonList(String courseIDInput) {
		int count = 0;
		
		for (int i=0 ;i<Container.lessonList.size(); i++)
        {
            if(courseIDInput.equals(Container.lessonList.get(i).getCourseID()))
            {
            	count++;
                String location;
                List<String> list = new ArrayList<String>(Arrays.asList(Container.lessonList.get(i).getLocation().toString().split("~")));
                if (!list.get(0).equals("null")) 
                {
                    location = Container.lessonList.get(i).getLocation().toString().replace('~',',');
                    
                } 
                else 
                {
                    location = "No address";
                    
                }
                System.out.println("Lesson ID: " + Container.lessonList.get(i).getLessonID() + 
                         " | Course ID: "+ Container.lessonList.get(i).getCourseID() + 
                         " | Lecture Type: "+ Container.lessonList.get(i).getLessonPeriod() + 
                         " | Online Lesson: "+ Container.lessonList.get(i).getIsOnline() +
                         " | Location: "+ location);
            }
        }
		return count;
	}

	// Admin feature: 1
	/**
	 * Sets the access period for the student.
	 * @param matricNo The matriculation number of the student.
	 * @param period The period in which the student can access the program.
	 * @param date The date in which the student can access the program.
	 */
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
	
	//Admin Feature: 2
	public static void createStudAcc(String name, String userName,
			String password, AccountType type,
			String matricNo, Gender gender,
			String nationality, int maxAU, Period accessPeriod, Date accessDate, String email, List<String> exemptedList) {

		Student createStudAcc = new Student(name, userName, password, type, matricNo, gender, nationality, maxAU,
				accessPeriod, accessDate, email);

		createStudAcc.setExemptedCourseList(exemptedList);


		createStudAcc.writeDataToFile(Container.STUDENT_FILE, false);
		
		// update the list
		Container.studentList.add(createStudAcc);

		try {
			Container.readStudentFile(Container.STUDENT_FILE, Container.studentList);
		} 
		catch (IOException e) {
		}
		
		for (int i = 0; i < Container.studentList.size(); i++) {
			// System.out.println(Container.studentList.get(i).toString());

		}
		
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
		
	
	
	// Admin Feature: 3 Add course
	public static void addCourse(String name, String school, String courseID, int courseAU, CourseType courseType)
	{
		//System.out.println(name);
		
		Course add_Course = new Course(name, school, courseID, courseAU, courseType);
		add_Course.writeDataToFile(Container.COURSE_FILE, false);
		
		Container.courseList.add(add_Course);
		

		for (int i=0 ;i<Container.courseList.size(); i++)
		{
			System.out.println(Container.courseList.get(i).toString());
			
		}
	}
	
	// Admin Feature: 3 Add index
	public static void addIndex(String courseID, String groupID, int index)
	{
		
		
		CoursePlan add_index = new CoursePlan(courseID, groupID, index);
		add_index.writeDataToFile(Container.COURSEPLAN_FILE, false);
		
		// update the list
		Container.coursePlanList.add(add_index);
		

		for (int i=0 ;i<Container.coursePlanList.size(); i++)
		{
			System.out.println(Container.coursePlanList.get(i).toString());

			
		}
	}
	
	// Admin Feature: 3 Add Slots
	public static void addSlots(int totalSlots, CoursePlan courseIndex)
	{
		
		
		CourseSlots add_Slots = new CourseSlots(totalSlots, courseIndex);
		add_Slots.writeDataToFile(Container.COURSESLOT_FILE, false);
		
		// update the list
		Container.courseSlotsList.add(add_Slots);

//		for (int i=0 ;i<Container.courseSlotsList.size(); i++)
//		{
//			System.out.println(Container.courseSlotsList.get(i).toString());
//			
//		}
	}
	
	
	
	// Admin Feature: 3 Add Lesson
	public static void addLessonLocation(int lessonID, String courseID, LessonType type, WeekType weekly,
			Period lessonPeriod, boolean isOnline, Location location)
	{
		
		
		Lesson add_LessonLocation = new Lesson(lessonID, courseID, type, weekly,
			lessonPeriod, isOnline, location);
		add_LessonLocation.writeDataToFile(Container.LESSON_FILE, false);
		
		// update the list
		Container.lessonList.add(add_LessonLocation);

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
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,true);
			} else {
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,false);
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
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,true);
			} else {
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,false);
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
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,true);
			} else {
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,false);
			}
			
		}
		
		// update CoursePlan.txt
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getCourseID().equals(courseID)) {

				Container.coursePlanList.get(i).setCourseID(newCourseID);
			}
			if(i==0) {
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,false);
			}
			
		}
		
		// update Lesson.txt
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getCourseID().equals(courseID)) {

				Container.lessonList.get(i).setCourseID(newCourseID);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
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
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,true);
			} else {
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,false);
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
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,true);
			} else {
				Container.courseList.get(i).writeDataToFile(Container.COURSE_FILE,false);
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
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,false);
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
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,false);
			}
			
		}
		
		// update CourseSlots.txt
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				//System.out.println("TEST");
				Container.courseSlotsList.get(i).getCoursePlan().setIndex(newIndex);
			}
			if(i==0) {
				Container.courseSlotsList.get(i).writeDataToFile(Container.COURSESLOT_FILE,true);
			} else {
				Container.courseSlotsList.get(i).writeDataToFile(Container.COURSESLOT_FILE,false);
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
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,true);
			} else {
				Container.coursePlanList.get(i).writeDataToFile(Container.COURSEPLAN_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Lesson CourseID
	public static void setLessonCourseID(int lessonID, String courseID) {
		
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getLessonID() == lessonID) {

				Container.lessonList.get(i).setCourseID(courseID);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Lesson LessonType
	public static void setLessonLessonType(int lessonID, LessonType type) {
		
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getLessonID() == lessonID) {

				Container.lessonList.get(i).setType(type);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Lesson weeklyType
	public static void setLessonWeeklyType(int lessonID, WeekType weekly) {
		
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getLessonID() == lessonID) {

				Container.lessonList.get(i).setWeekly(weekly);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Lesson Period
	public static void setLessonPeriod(int lessonID, Period lessonPeriod) {
		
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getLessonID() == lessonID) {

				Container.lessonList.get(i).setLessonPeriod(lessonPeriod);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Lesson isOnline
	public static void setLessonIsOnline(int lessonID, boolean isOnline) {
		
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getLessonID() == lessonID) {
				
				if(isOnline == true)
				{
					// clean the location in the txt file
					Location cleanLocation = new Location();
					Container.lessonList.get(i).setLocation(cleanLocation);;
					Container.lessonList.get(i).setIsOnline(isOnline);
				}
				else
				{
					//only update the online status
					Container.lessonList.get(i).setIsOnline(isOnline);
				}
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Lesson Location
	public static void setLessonLocation(int lessonID, Location location) {
		
		for(int i = 0; i < Container.lessonList.size(); i++) {
			if(Container.lessonList.get(i).getLessonID() == lessonID) {

				Container.lessonList.get(i).setLocation(location);
			}
			if(i==0) {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,true);
			} else {
				Container.lessonList.get(i).writeDataToFile(Container.LESSON_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update Course Slots
	public static void setCourseSlots(int index, int totalSlots) {
		String matricNo;
		Student student;
		
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {

				Container.courseSlotsList.get(i).setTotalSlots(totalSlots);
				
				//need to shift waiting list student to the register slots
				while (Container.courseSlotsList.get(i).getSlotList().size() < totalSlots && Container.courseSlotsList.get(i).getWaitingList().isEmpty() == false)
				{
					// get the first person in the waiting list
					matricNo = Container.courseSlotsList.get(i).getWaitingList().get(0);
					student = Container.getStudentByMatricNo(matricNo);
					
					// add it to slotList
					Container.courseSlotsList.get(i).getSlotList().add(matricNo);
					
					// remove the first index of the waiting list
					Container.courseSlotsList.get(i).getWaitingList().remove(0);
					
					System.out.println("Sending email........");
					EmailNotification.getInstance().sendNotification(student, Notification.createMessage(student.getName(), Container.courseSlotsList.get(i).getCoursePlan().getCourseID(), true));
					System.out.println("Sent successfully");					
					
					//update the student Account 
					setStudentAccountRegisteredIndex(matricNo, index);
				}
				
			}
			if(i==0) {
				Container.courseSlotsList.get(i).writeDataToFile(Container.COURSESLOT_FILE,true);
			} else {
				Container.courseSlotsList.get(i).writeDataToFile(Container.COURSESLOT_FILE,false);
			}
			
		}
	}
	
	//Admin Feature: 3 Update StudentAccount registered index
	public static void setStudentAccountRegisteredIndex(String matricNo, int courseIndex)
	{
		for (int i = 0; i < Container.studentList.size(); i++)
		{

			if (Container.studentList.get(i).getMatricNo().equals(matricNo))
			{
				for (int j = 0; j < Container.coursePlanList.size(); j++)
				{
					if (Container.coursePlanList.get(j).getIndex() == courseIndex)
					{
						Container.studentList.get(i).getCoursePlan().add(Container.coursePlanList.get(j));
						break;
					}
				}
			}
			if (i == 0)
			{
				Container.studentList.get(i).writeDataToFile(Container.STUDENT_FILE, true);
			}
			else
			{
				Container.studentList.get(i).writeDataToFile(Container.STUDENT_FILE, false);
			}
		}
	}
	

	// Admin Feature: 4
	/**
	 * Gets the number of available slots by course index.
	 * @param index The course index.
	 * @return The number of available slots in the course index.
	 */
	public static int getNoOfSlotsByCourseIndex(int index) {
		int availableSlots = -1;
		
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				availableSlots = Container.courseSlotsList.get(i).getTotalSlots() - Container.courseSlotsList.get(i).getSlotList().size();
			}
		}
		
		return availableSlots;
	}
	
	/**
	 * Gets total slots in the course index.
	 * @param index The course index.
	 * @return The total slots in the course index.
	 */
	public static int getTotalSlotsByCourseIndex(int index) {
		int totalSlots = -1;
		
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				totalSlots = Container.courseSlotsList.get(i).getTotalSlots();
			}
		}
		return totalSlots;
	}
	
	
	// Admin Feature: 5
	/**
	 * Gets list of students registered in the course index.
	 * @param index The course index.
	 * @return List of students registered in the course index.
	 */
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
	/**
	 * Gets the list of students registered in the course.
	 * @param courseID The ID of the course.
	 * @return The list of students (with relevant information) that are registered in the course.
	 */
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
}
