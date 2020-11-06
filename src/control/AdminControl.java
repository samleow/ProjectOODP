package control;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

import entity.Date;
import entity.Period;
import entity.Student;

import test_cases.CreateStudentAccount;

import entity.AllEnums.AccountType;
import entity.AllEnums.Gender;

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
            System.out.println(intAccessDate);
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
	
	public static void createStudAcc(String name, String userName,
			String password, AccountType type,
			String matricNo, Gender gender,
			String nationality, int maxAU, Period accessPeriod, Date accessDate ) // please help to add gender etc
	{
    	CreateStudentAccount studDB = new CreateStudentAccount();
    	String filename = "StudentAccount.txt" ;

		try {
			// read file containing Student records and store into a list
			ArrayList studList = studDB.readStudents(filename);
			
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
//			Student addStud = new Student(name, userName,
//					password, type,
//					matricNo, gender,
//					nationality, maxAU);
//			
//			studList.add(addStud);
//			
//			// resave the whole studList to the txt file
//			studDB.saveStudent(filename, studList);
			
		}
		catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
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
			for(int k = 0; k < Container.studentList.get(i).getCourses().size();k++) {
				if (Container.studentList.get(i).getCourses().get(k).getIndex() == index) {
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
			for(int k = 0; k < Container.studentList.get(i).getCourses().size();k++) {
				if(Container.studentList.get(i).getCourses().get(k).getCourseID().equals(courseID)) {
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
