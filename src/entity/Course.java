package entity;

import java.util.List;

public class Course {
	private String name;
	private String courseID;
	private int courseAU;
	private CoursePlan coursePlan;
	
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
