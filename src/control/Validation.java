package control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

import entity.AllEnums.*;


public class Validation {
	
	/**
	 * Checks whether the course ID currently exist.
	 * @param courseID The ID of the course.
	 * @return boolean value on whether the course ID is valid.
	 */
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
	
	/**
	 * Checks if the course index currently exist.
	 * @param index The course index.
	 * @return boolean value on whether the course ID is valid.
	 */
	public static boolean checkIfValidIndex(int index) {
		
		boolean check = false;
		for(int i = 0; i < Container.coursePlanList.size(); i++) { // took the index from coursePlanList
			if(Container.coursePlanList.get(i).getIndex() == index) {
				check = true;
				break;
			}
		}
		return check;
	}
	
	/**
	 * Checks if the time is a valid time, following the HH:MM format
	 * @param time The time values.
	 * @return boolean value on whether the time is valid
	 */
	public static boolean checkIfValidTime(String time) {
		boolean check = true;
		if (!time.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
//		     System.out.println("Invalid time string: " + time);
			check= false;
		} 
		return check;
	}
	
	/**
	 * Checks if the matriculation number currently exist.
	 * @param matricNo The matriculation number value.
	 * @return Boolean value on whether the matriculation number currently exist.
	 */
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
	

	/**
	 * Checks if the date is valid, following the YYYY-MM-DD format.
	 * @param strAccessDate Access date in the String format.
	 * @return boolean value of whether the date is a valid date.
	 */
	public static boolean checkIfValidDate(String strAccessDate) {
		
		if(!strAccessDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return false;
		}
		
		StringTokenizer star = new StringTokenizer(strAccessDate , "-");
		String year = star.nextToken().trim();
		String month = star.nextToken().trim();
		String day = star.nextToken().trim();
		
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
            
            if(intAccessDate < intCurrentDate) {
            	System.out.println("Date entered cannot be in the past.");
            	return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
		}
	}
	
	//Validate GroupID e.g SSP1
	/**
	 * Checks if the group id is valid. 
	 * @param courseID The course id value. 
	 * @param groupIDInput The group id value.
	 * @return Boolean value on whether the group id is valid.
	 */
	public static boolean checkIfValidGroupID(String courseID, String groupIDInput) {
		boolean check = false;
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getGroupID().equals(groupIDInput) && Container.coursePlanList.get(i).getCourseID().equals(courseID)) {
				check = true;
				break;
			}
		}
		
		return check;
	}
	
	/**
	 * Checks if the course slot is empty. 
	 * @param index The course index.
	 * @return Boolean value on whether the course slot is empty.
	 */
	public static boolean checkIfValidIsCourseSlotEmpty(int index) {
		boolean check = false;
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(Container.courseSlotsList.get(i).getSlotList().isEmpty()) {
				check = true;
				break;
			}
		}
		
		return check;
	}	
	
	//Validate if Lesson ID exist in course plan using index (update course plan)
	public static boolean checkIfExistLessonID(int lessonID, int index) {
		boolean check = false;
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getIndex() == index )
			{
			for(int j = 0; j < Container.coursePlanList.get(i).getLessons().size(); j++) {
				if(Container.coursePlanList.get(i).getLessons().get(j).getLessonID() == lessonID) {
					check = true;
					break;
			}
			}
			}
		}
		
		return check;
	}	
	
	//Validate if there are lesson ID in course Plan (update lesson)
	public static boolean checkIfThereAreLessonIDInCousePlan(String courseID, int lessonID) {
		boolean check = false;
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getCourseID().equals(courseID))
			{
			for(int j = 0; j < Container.coursePlanList.get(i).getLessons().size(); j++) {
				if(Container.coursePlanList.get(i).getLessons().get(j).getLessonID() == lessonID) {
					check = true;
					break;
			}
			}
			}
		}
		
		return check;
	}	

	
	//Validate student exist in course plan using courseID (update lesson)
	public static boolean checkIfIsThereStudent(String courseID, int lessonID ) {
		
		boolean check = false;
		for (int i=0 ;i<Container.coursePlanList.size(); i++)
		{
			
			for(int j= 0; j<Container.coursePlanList.get(i).getLessons().size(); j++)
			{
				
				// if the lesson ID exist in coursePlanList
				if(courseID.equals(Container.coursePlanList.get(i).getCourseID()) && lessonID == Container.coursePlanList.get(i).getLessons().get(j).getLessonID())
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
								check = true;
								
							}
							// once found the CoursePlanIndex, end the loop
							break;
							
						}

					}
					
				}
				
			}
		}
		return check;
		
	}
	
	

	/**
	 * Checks if course type is valid. 
	 * @param courseTypeInput The course type value.
	 * @return The value of the course type.
	 */
	public static CourseType checkIfValidCourseType(String courseTypeInput)
	{
		CourseType courseType = CourseType.DEFAULT;
		if (courseTypeInput.equals(CourseType.CORE.toString())) 
		{
			courseType = CourseType.CORE;
			
		} 
		else if (courseTypeInput.equals(CourseType.GER.toString())) {
			courseType = CourseType.GER;
			
		} 
		else if(courseTypeInput.equals(CourseType.PRESCRIBED.toString()))
		{
			courseType = CourseType.PRESCRIBED;
			
			
		}
		else if (courseTypeInput.equals(CourseType.UNRESTRICTED_ELECTIVE.toString()) || courseTypeInput.equals("UE") || courseTypeInput.equals("UNRESTRICTED ELECTIVE") )
		{
			courseType = CourseType.UNRESTRICTED_ELECTIVE;
			
		}
		
		return courseType;
		
	}
	
	/**
	 * Checks if lesson type is valid. 
	 * @param lessonTypeInput The lesson type value.
	 * @return The value of the lesson type. 
	 */
	public static LessonType checkIfValidLessonType(String lessonTypeInput)
	{
		LessonType lessonType = LessonType.DEFAULT;
		if (lessonTypeInput.equals(LessonType.LAB.toString())) 
		{
			lessonType = LessonType.LAB;
			
		} 
		else if (lessonTypeInput.equals(LessonType.LECTURE.toString()) || lessonTypeInput.equals("LEC")) {
			lessonType = LessonType.LECTURE;
			
		} 
		else if(lessonTypeInput.equals(LessonType.TUTORIAL.toString())|| lessonTypeInput.equals("TUT"))
		{
			lessonType = LessonType.TUTORIAL;

		}
		
		return lessonType;
	}

	/**
	 * Check if the week type is valid.
	 * @param weekTypeInput The week type value. 
	 * @return The value of the week type. 
	 */
	public static WeekType checkIfValidWeekType(String weekTypeInput)
	{
		WeekType weekType = WeekType.DEFAULT;
		if (weekTypeInput.equals(WeekType.ODD.toString())) 
		{
			weekType = WeekType.ODD;
		} 
		else if (weekTypeInput.equals(WeekType.EVEN.toString())) {
			weekType = WeekType.EVEN;
		} 
		else if(weekTypeInput.equals(WeekType.WEEKLY.toString()))
		{
			weekType = WeekType.WEEKLY;
		}
		
		return weekType;
		
	}
	

	/**
	 * Check if the day is valid.
	 * @param dayInput The lesson day value.
	 * @return The value of the lesson day. 
	 */
	public static Day checkIfValidDay(String dayInput)
	{
		Day lessonDay = Day.DEFAULT;
		if (dayInput.equals(lessonDay.MONDAY.toString()) || (dayInput.equals("MON"))) 
		{
			lessonDay = lessonDay.MONDAY;
		} 
		else if (dayInput.equals(lessonDay.TUESDAY.toString())|| (dayInput.equals("TUE"))) 
		{
			lessonDay = lessonDay.TUESDAY;
		} 
		else if(dayInput.equals(lessonDay.WEDNESDAY.toString())|| (dayInput.equals("WED")))
		{
			lessonDay = lessonDay.WEDNESDAY;
		}
		else if(dayInput.equals(lessonDay.THURSDAY.toString())|| (dayInput.equals("THU")))
		{
			lessonDay = lessonDay.THURSDAY;
		}
		else if(dayInput.equals(lessonDay.FRIDAY.toString())|| (dayInput.equals("FRI")))
		{
			lessonDay = lessonDay.FRIDAY;
		}
		else if(dayInput.equals(lessonDay.SATURDAY.toString())|| (dayInput.equals("SAT")))
		{
			lessonDay = lessonDay.SATURDAY;
		}
		else if(dayInput.equals(lessonDay.SUNDAY.toString())|| (dayInput.equals("SUN")))
		{
			lessonDay = lessonDay.SUNDAY;
		}
		
		return lessonDay;
		
	}
	

	/**
	 * Checks whether the student username currently exist.
	 * @param userName The student username.
	 * @return Boolean value on whether the student username is valid. 
	 */
	public static boolean checkIfValidStudentUserName(String userName) {
		boolean check = false;
		for(int i = 0; i < Container.studentList.size(); i++) {
			if(Container.studentList.get(i).getUserName().equals(userName)) {
				check = true;
				break;
			}
		}
		
		return check;
	}
	

	/**
	 * Checks if the student got take this course.
	 * @param index The course index.
	 * @return Boolean value on whether the student got take this course.
	 */
	public static boolean checkIfStudentTookThisCourse(int index) {
		boolean check = false;
		for(int i = 0; i < StudentControl.studentInfo.getCoursePlan().size(); i++) {
			for(int j = 0; j < Container.coursePlanList.size(); j++) {
				if(StudentControl.studentInfo.getCoursePlan().get(i).getCourseID().equals(Container.coursePlanList.get(j).getCourseID())) {
					if(Container.coursePlanList.get(j).getIndex() == index) {
						check = true;
						break;
					}
				}
			}
		}
		return check;
	}
	
	
	/**
	 * Checks if the student got take this course index.
	 * @param index The course index.
	 * @return Boolean value on whether the student got take this course index.
	 */
	public static boolean checkIfStudentTookThisIndex(int index) {
		boolean check = false;
		for(int i = 0; i < StudentControl.studentInfo.getCoursePlan().size(); i++) {
			if(StudentControl.studentInfo.getCoursePlan().get(i).getIndex() == index) {
				check = true;
				break;
			}
		}
		return check;
	}
				
	
	/**
	 * Checks if the student exempted from the course.
	 * @param index The course index.
	 * @return Boolean value on whether the student exempted from the course.
	 */
	public static boolean checkIfCourseExempted(int index) {
		boolean check = false;
		outerloop:
			for(int i = 0; i < StudentControl.studentInfo.getExemptedCourseList().size(); i++) {
				for(int j = 0; j < Container.coursePlanList.size(); j++) {
					//Check for same courseID of the Container Course Plan List
					if(StudentControl.studentInfo.getExemptedCourseList().get(i).equals(Container.coursePlanList.get(j).getCourseID())) {
						//Check if indexno same a not
						if(Container.coursePlanList.get(j).getIndex() == index) {
							check = true;
							break outerloop;
						}
					}
				}
			}
		return check;
	}
	
	/**
	 * Checks if the student courses exist in the waiting list.
	 * @param index The course index.
	 * @return Boolean value on whether the student courses exist in the waiting list.
	 */
	public static boolean checkIfStudentInCourseWaitingList(int index) {
		boolean check = false; 
		outerloop:
		for (int i = 0; i < Container.coursePlanList.size(); i++ ) {
			if(Container.coursePlanList.get(i).getIndex() == index) {
				for(int y = 0; y < Container.courseSlotsList.size(); y++) {
					if(Container.coursePlanList.get(i).getCourseID().equals(Container.courseSlotsList.get(y).getCoursePlan().getCourseID())) {
						if(Container.courseSlotsList.get(y).getWaitingList().size() != 0) {
							//Loop through the waiting list
							for(int z = 0; z < Container.courseSlotsList.get(y).getWaitingList().size(); z++) {
								//Check if student matric no matches with the ones in the waiting list
								if(StudentControl.studentInfo.getMatricNo().equals(Container.courseSlotsList.get(y).getWaitingList().get(z))) {
									check = true;
									break outerloop; 
								}
							}
						}
					}
				}
			}
		}
		return check;
	}
	

	/**
	 * Checks if the student courses index exist in the waiting list.
	 * @param index The course index.
	 * @return Boolean value on whether the student courses index exist in the waiting list.
	 */
	public static boolean checkIfStudentinIndexWaitingList(int index) {
		boolean check = false; 
		outerloop:
		for (int i = 0; i < Container.courseSlotsList.size(); i++ ) {
			if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				if(Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
					//Loop through the waiting list
					for(int z = 0; z < Container.courseSlotsList.get(i).getWaitingList().size(); z++) {
						//Check if student matric no matches with the ones in the waiting list
						if(StudentControl.studentInfo.getMatricNo().equals(Container.courseSlotsList.get(i).getWaitingList().get(z))) {
							check = true;
							break outerloop; 
						}
					}
				}
			}
		}
		return check;
	}

}
