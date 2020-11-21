package control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

import entity.*;

import entity.AllEnums;
import entity.AllEnums.CourseType;
import entity.AllEnums.Day;
import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;


public class Validation {
	
	// Validate CoursID
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
	
	// Validate course Index
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
	
	//Validate Time
	public static boolean checkIfValidTime(String time) {
		boolean check = true;
		if (!time.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
//		     System.out.println("Invalid time string: " + time);
			check= false;
		} 
		return check;
	}
	
	

	// Validate Matric No
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
	

	//Validate Date
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
	
	


	//Validate GroupID e.g SSP1
	public static boolean checkIfValidGroupID(String groupIDInput) {
		boolean check = false;
		for(int i = 0; i < Container.coursePlanList.size(); i++) {
			if(Container.coursePlanList.get(i).getGroupID().equals(groupIDInput)) {
				check = true;
				break;
			}
		}
		
		return check;
	}
	
		

	
	//Validate Course Type
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
		else if (courseTypeInput.equals(CourseType.UNRESTRICTED_ELECTIVE.toString()) || courseTypeInput.equals("UE"))
		{
			courseType = CourseType.UNRESTRICTED_ELECTIVE;
			
		}
		
		return courseType;
		
	}
	
	//Validate Lesson Type
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
	
	//Validate Week Type
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
	
	//Validate Day
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
	
	
	//Validate UserName
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
	
	// Validate Student took this index
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
				
	
	// Validate Course Exempted
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
	
	// Validate Student In Waiting List
	public static boolean checkIfStudentInWaitingList(int index) {
		boolean check = false; 
		outerloop:
			for (int i = 0; i < Container.courseSlotsList.size(); i++ ) {
				//Specify index
				if(Container.courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
					//Check if the waiting list got students a not
					if(Container.courseSlotsList.get(i).getWaitingList().size() != 0) {
						//Loop through the waiting list
						for(int y = 0; y < Container.courseSlotsList.get(i).getWaitingList().size(); y++) {
							//Check if student matric no matches with the ones in the waiting list
							if(StudentControl.studentInfo.getMatricNo().equals(Container.courseSlotsList.get(i).getWaitingList().get(y))) {
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
