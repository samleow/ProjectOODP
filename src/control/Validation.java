package control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

public class Validation {
	
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
}
