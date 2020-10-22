package entity;

import java.util.List;

import entity.AllEnums.Day;

public class Course {
	private String name;
	private String courseID;
	private int courseAU;
	private CoursePlan coursePlan;
	
	//To preload the data to create the text file, can be removed later on
	public Course(String name, String courseID, int courseAU,CoursePlan coursePlan) {
		this.name = name;
		this.courseID = courseID;
		this.courseAU = courseAU;
		this.coursePlan = coursePlan;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCourseID() {
		return this.courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public int getCourseAU() {
		return this.courseAU;
	}
	
	public void setCourseAU(int courseAU) {
		this.courseAU = courseAU;
	}
	
	public CoursePlan getCoursePlan() {
		return this.coursePlan;
	}
	
	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}
	
}	
