package entity;

import java.util.List;

public class CoursePlan {
	private String courseID;
	private String groupID;
	private String index; // should it be int instead?
	private List<Lesson> lessons;
	
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
	
	public String getIndex() {
		return this.index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public List<Lesson> getLessons() {
		return this.lessons;
	}
	
	public void setLessons(List<Lesson> index) {
		this.lessons = lessons;
	}
	
}
