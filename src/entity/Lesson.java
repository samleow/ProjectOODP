package entity;

import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;

public class Lesson {
	private String courseID;
	private LessonType type;
	private WeekType weekly;
	private Period lessonPeriod;
	private boolean isOnline;
	private Location location;
	
	public String getCourseID() {
		return this.courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public LessonType getType() {
		return this.type;
	}
	
	public void setType(LessonType type) {
		this.type = type;
	}
	
	public WeekType getWeekly() {
		return this.weekly;
	}
	
	public void setWeekly(WeekType weekly) {
		this.weekly = weekly;
	}
	
	public Period getLessonPeriod() {
		return this.lessonPeriod;
	}
	
	public void setLessonPeriod(Period lessonPeriod) {
		this.lessonPeriod = lessonPeriod;
	}
	
	public boolean getIsOnline() {
		return this.isOnline;
	}
	
	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
}
