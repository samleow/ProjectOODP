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
		boolean checkCoursesTook = false;
		boolean checkCoursesExempted = false;
		boolean timetableClash = false;
		boolean waitingList = false;
		
		System.out.println(studentInfo);
		
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
			System.out.println(studentInfo.getCoursePlan());
			
			
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

							//Timetable Checking
							//Check day if same then do a comparison else timetableclash = false
							if(studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getDay().equals(tempcourseplan.getLessons().get(z).getLessonPeriod().getDay())) {
								//Compare start time and end time
								if((studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime()) == -1
								&& studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime()) == 1)
								|| (studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime()) == 1
								&& studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime()) == -1)
								
								//If same end time and studentInfo startTime > temp startTime
								|| (studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime()) == 1
								&& studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().toString().equals(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime().toString()))
								
								//If same start time and studentInfo endTime > temp endTime
								|| (studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime()) == 1
								&& studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().toString().equals(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime().toString()))
								
								//Check if starTime and endTime equals
								|| (studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().toString().equals(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime().toString())
								&& studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().toString().equals(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime().toString())
								)){
									System.out.println("Break at Start time and End time");
									System.out.println("Displaying TimeTable Clashes:");
									
									//Display Start Time
									System.out.println(studentInfo.getCoursePlan().get(i).getLessons().get(j));
//									int start = studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getStartTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getStartTime());
//									System.out.println("Start : " + start);
									//Display End Time
									System.out.println(tempcourseplan.getLessons().get(z));
//									int end = studentInfo.getCoursePlan().get(i).getLessons().get(j).getLessonPeriod().getEndTime().compareWith(tempcourseplan.getLessons().get(z).getLessonPeriod().getEndTime());
//									System.out.println("end : " + end);
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
			
    		
    		//Overwriting the CourseSlots txt file with the updated data
//    		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
//    			if(i==0) {
//    				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",true);
//    			}
//    			else {
//    				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",false);
//    			}
//    		}
    		
    		
    		
				if(!waitingList) {
					
					//Adding the Index No
		    		for (int k=0;k < Container.coursePlanList.size();k++)
		    		{
						if(Container.coursePlanList.get(k).getIndex() == indexno) {
							// i dk why adding the course plan into the studentInfo
							// Can also add into the container studentList???
							// MAGIC???
							studentInfo.getCoursePlan().add(Container.coursePlanList.get(k));
							break;
						}
		    		}
		    		
		    		//Overwriting the StudentAccount txt file with the updated data
	//	    		for(int i = 0; i < Container.studentList.size(); i++) {
	//	    			if(i==0) {
	//	    				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",true);
	//	    			}
	//	    			else {
	//	    				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",false);
	//	    			}
	//	    		}
		    		System.out.println("Successfully Added the Course \n");
				}
			}
		}
	}
	
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
		if(!checkCoursesTook) {
			System.out.println("You never take this Course \n");
		}
		
		if(checkCoursesTook) {
    		for (int k=0;k < Container.coursePlanList.size();k++)
    		{
				if(Container.coursePlanList.get(k).getIndex() == indexno) {
					studentInfo.getCoursePlan().remove(Container.coursePlanList.get(k));
					break;
				}
    		}
    		
//    		//Overwriting the StudentAccount txt file with the updated data
    		for(int i = 0; i < Container.studentList.size(); i++) {
    			if(i==0) {
    				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",true);
    			}
    			else {
    				Container.studentList.get(i).writeDataToFile("StudentAccount.txt",false);
    			}
    		}
    		
    		//Remove Student Matric No from the CourseSlots slotList
    		for (int k=0;k < Container.courseSlotsList.size();k++)
    		{
    			if(Container.courseSlotsList.get(k).getCoursePlan().getIndex() == indexno) {
					Container.courseSlotsList.get(k).getSlotList().remove(studentInfo.getMatricNo());
					
    			}
    		}
    		
    		//Overwriting the CourseSlots txt file with the updated data
    		for(int i = 0; i < Container.courseSlotsList.size(); i++) {
    			if(i==0) {
    				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",true);
    			}
    			else {
    				Container.courseSlotsList.get(i).writeDataToFile("CourseSlots.txt",false);
    			}
    		}
    		System.out.println("Successfully Removed the Course \n");
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
	
	
	

	
	
	
	
	
	
}
