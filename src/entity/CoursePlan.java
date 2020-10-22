package entity;

import java.util.List;

public class CoursePlan {
	private String courseID;
	private String groupID;
	private int index; // should it be int instead?
	private List<Lesson> lessons;
	
	public CoursePlan(String courseID, String groupID, int index) {
		this.courseID = courseID;
		this.groupID = groupID;
		this.index = index;
	}
	
	public String getCourseID() {
		return this.courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public String getGroupID() {
		return this.groupID;
	}
	
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public List<Lesson> getLessons() {
		return this.lessons;
	}
	
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	
}
