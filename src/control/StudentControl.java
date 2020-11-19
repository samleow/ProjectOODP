package control;

import java.io.*;
import java.util.*;
import control.Container;
import entity.AllEnums.*;
import entity.*;

public class StudentControl {
	
	public static Student studentInfo;
	
	public static void saveStudentInfo(String userName)
	{
		for(int i = 0; i < Container.studentList.size(); i++) {
			if(Container.studentList.get(i).getUserName().equals(userName)) {
				studentInfo = Container.studentList.get(i);
			}
		}
	}

	public static void addCourse(int indexno)
	{
		//validation of checking only integer 
		
		boolean checkCoursesTook = false;
		boolean checkCoursesExempted = false;
		boolean timetableClash = false;
		boolean waitingList = false;
		
		//Check whether user got take the Course already a not
		for(int i = 0; i < studentInfo.getCoursePlan().size(); i++) {
			if(studentInfo.getCoursePlan().get(i).getIndex() == indexno) {
				System.out.println("You have took this Course already.\n");
				checkCoursesTook = true;
				break;
			}
		}
		
		//Check whether user is exempted from the Course a not
		outerloop:
		for(int i = 0; i < studentInfo.getExemptedCourseList().size(); i++) {
			for(int j = 0; j < Container.coursePlanList.size(); j++) {
				//Check for same courseID of the Container Course Plan List
				if(studentInfo.getExemptedCourseList().get(i).equals(Container.coursePlanList.get(j).getCourseID())) {
					//Check if indexno same a not
					if(Container.coursePlanList.get(j).getIndex() == indexno) {
						System.out.println("You are exempted from this Course already.\n");
						checkCoursesExempted = true;
						break outerloop;
					}
				}
			}
		}
		

		if(!checkCoursesTook && !checkCoursesExempted) {
			//Get the user input of indexno information
			CoursePlan tempcourseplan = null;
			for(int i = 0; i < Container.coursePlanList.size(); i++) {
				if(Container.coursePlanList.get(i).getIndex() == indexno) {
					tempcourseplan = Container.coursePlanList.get(i);
					break;
				}
			}
			
			//Check for same Lesson ID 
			outerloop:
			for(int i = 0; i < studentInfo.getCoursePlan().size(); i++) {
				for(int j = 0; j < studentInfo.getCoursePlan().get(i).getLessons().size(); j++) {
					for(int z = 0; z < tempcourseplan.getLessons().size(); z++) {
						if(studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonID() == tempcourseplan.getLessons().get(z).getLessonID()) {
							System.out.println("Break at Lesson ID");
							System.out.println("Timetable Clashes. Failed to add the Course\n");
							timetableClash = true;
							break outerloop;
						}
						
						//Check if it is weekly or (old==old or even==even)
						if((studentInfo.getCoursePlan().get(i).getLessons().get(j).getWeekly().equals(WeekType.WEEKLY) ||
							tempcourseplan.getLessons().get(z).getWeekly().equals(WeekType.WEEKLY) ||
							(studentInfo.getCoursePlan().get(i).getLessons().get(j).getWeekly().equals(tempcourseplan.getLessons().get(z).getWeekly())))){

							//Check 
							if(studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().clashWith(tempcourseplan.getLessons().get(z).getLessonPeriod())) {
								System.out.println("Displaying TimeTable Clashes:");
								//Display Start Time
								System.out.println(studentInfo.getCoursePlan().get(i).getLessons().get(j));
								int start = studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime());
								//Display End Time
								System.out.println(tempcourseplan.getLessons().get(z));
								int end = studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime());
								System.out.println("TimeTable Clashes. Failed to add the Course\n");
								timetableClash = true;
								break outerloop;
								
							}
						}	
					}
				}
			}
		}
			
			
			if(!timetableClash) {
			//Add Student Matric No into the CourseSlots slotList
				for (int k=0;k < Container.courseSlotsList.size();k++)
				{
					if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
						//If the Course got no vacancy, put the Student on Waiting List
						if(Container.courseSlotsList.get(k).getSlotList().size()<Container.courseSlotsList.get(k).getTotalSlots()) {
							Container.courseSlotsList.get(k).getSlotList().add(studentInfo.getMatricNo());
							break;
						}
						else {
							Container.courseSlotsList.get(k).getWaitingList().add(studentInfo.getMatricNo());
							System.out.println("No Vacancy Available. You will be put on the Waiting List \n");
							waitingList = true;
							break;
						}
					}
				}
				
				//overwriteCourseSlotsData();

				//If not in waitingList then add the Course in the Student Class CoursePlan List
				if(!waitingList) {
					
					//Adding the Index No
		    		for (int k=0;k < Container.coursePlanList.size();k++)
		    		{
						if(Container.coursePlanList.get(k).getIndex() == indexno) {
							// i dk why adding the course plan into the studentInfo
							// Can also add into the container studentList???
							// MAGIC???
							//It is link and reference
							studentInfo.getCoursePlan().add(Container.coursePlanList.get(k));
							//overwriteStudentAccountData();
							break;
						}
		    		}
		    		System.out.println("Successfully Added the Course \n");
				}
			}
		}
	//}
	
	public static void dropCourse(int indexno)
	{
		boolean checkCoursesTook = false;

		//Do a check
		for(int i = 0; i < studentInfo.getCoursePlan().size(); i++) {
			if(studentInfo.getCoursePlan().get(i).getIndex() == indexno) {
				checkCoursesTook = true;
				break;
			}
		}
		
		if(checkCoursesTook) {
			
			//Remove CoursePlan from List<CoursePlan> in Student class
    		for (int k=0;k < Container.coursePlanList.size();k++)
    		{
				if(Container.coursePlanList.get(k).getIndex() == indexno) {
					studentInfo.getCoursePlan().remove(Container.coursePlanList.get(k));
					//overwriteCourseSlotsData();
					break;
				}
    		}
    		
    		//Need to overwrite first to update the data
    		//overwriteCourseSlotsData();
    		
//    		//Overwriting the StudentAccount txt file with the updated data
//    		for(int i = 0; i < Container.studentList.size(); i++) {
//    			if(i==0) {
//    				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",true);
//    			}
//    			else {
//    				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",false);
//    			}
//    		}
    		
    		//Remove Student Matric No from the CourseSlots slotList
    		for (int k=0;k < Container.courseSlotsList.size();k++)
    		{
    			if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
					Container.courseSlotsList.get(k).getSlotList().remove(studentInfo.getMatricNo());
					//overwriteStudentAccountData();
					break;
    			}
    		}
    		
    		
    		String student = "";
    		
    		for (int k=0;k < Container.courseSlotsList.size();k++)
			{
				//Check if got vacancy, if have then check waiting list got student a not
				if(Container.courseSlotsList.get(k).getSlotList().size()<Container.courseSlotsList.get(k).getTotalSlots()) {
					if(Container.courseSlotsList.get(k).getWaitingList().size() != 0) {
						student = Container.courseSlotsList.get(k).getWaitingList().get(0);
						break;
					}
					break;
				}
			}
    		
    		//Check 
    		if(!student.equals("")) {
    			//check if this student current course clashes with other courses he took
    			
    			
    			
    			//if no clashes then add his matric no in the slotList
    			for (int k=0;k < Container.courseSlotsList.size();k++)
				{
    				if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
						Container.courseSlotsList.get(k).getSlotList().add(student);
						Container.courseSlotsList.get(k).getWaitingList().remove(0);
						
						//Overwrite the text file with the latest update data
						//overwriteCourseSlotsData();
						break;
					}
				}
    			
    			//add index no into List<CoursePlan> in Student class
    			for (int k=0;k < Container.coursePlanList.size();k++)
	    		{
					if(Container.coursePlanList.get(k).getIndex() == indexno) {
						studentInfo.getCoursePlan().add(Container.coursePlanList.get(k));
						//overwriteStudentAccountData();
						break;
					}
	    		}
    			
    			//A notification will be sent to the student
    			//Student class need to have email 
    			
    			
    			
    			
    			
    			
        		//Will update if the student on the waiting list can add the course
//        		//Overwriting the CourseSlots txt file with the updated data
    			//overwriteStudentAccountData();
    			
//        		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
//        			if(i==0) {
//        				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",true);
//        			}
//        			else {
//        				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",false);
//        			}
//        		}
    			

    		
    		}
    		System.out.println("Successfully Removed the Course \n");
		}
		System.out.println("You never take this Course \n");
	}
	
	public static void overwriteCourseSlotsData()
	{
		//Overwriting the CourseSlots txt file with the updated data
		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
			if(i==0) {
				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",true);
			}
			else {
				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",false);
			}
		}
	}
	
	public static void overwriteStudentAccountData()
	{
		//Overwriting the StudentAccount txt file with the updated data
		for(int i = 0; i < Container.studentList.size(); i++) {
			if(i==0) {
				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",true);
			}
			else {
				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",false);
			}
		}
	}

	
	
	public static void displayCourse()
	{
		CourseType courseType = CourseType.DEFAULT;
		int AU = -1;
		System.out.println("Display Current Courses you have registered");
		
		//Need to do like this as studentInfo.getCoursePlan().get(i).getCourse() give me NULL
		for(int i = 0; i < studentInfo.getCoursePlan().size(); i++) 
		{
			for(int j = 0; j < Container.courseList.size(); j++) 
			{
				if(studentInfo.getCoursePlan().get(i).getCourseID().equals(Container.courseList.get(i).getCourseID()))
				{ 
					AU = Container.courseList.get(i).getCourseAU();
					courseType = Container.courseList.get(i).getCourseType();
					break;
				}
			}
			System.out.println("CourseID: " + studentInfo.getCoursePlan().get(i).getCourseID() +  
					" | AU: " + AU  + " | Course Type: " + courseType + 
					" | Index No: " + studentInfo.getCoursePlan().get(i).getIndex());
		}
	}
	
	public static boolean timetableClash(Student s, CoursePlan oldCP, CoursePlan newCP)
	{
		Lesson l1,l2;
		for(int i=0;i<s.getCoursePlan().size();i++)
		{
			if(s.getCoursePlan().get(i).equals(oldCP))
				continue;
			
			for(int j=0;j<s.getCoursePlan().get(i).getLessons().size();j++)
			{
				l1 = s.getCoursePlan().get(i).getLessons().get(j);
				for(int k=0;k<newCP.getLessons().size();k++)
				{
					l2 = newCP.getLessons().get(k);
					if(l1.clashWith(l2))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
