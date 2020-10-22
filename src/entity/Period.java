package entity;

import entity.AllEnums.Day;

public class Period {
	private String startTime;
	private String endTime;
	private Day day;
	
	//To preload the data to create the text file, can be removed later on
	public Period(String startTime, String endTime, Day day) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
	}
	
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Day getDay() {
		return this.day;
	}
	
	public void setDay(Day day) {
		this.day = day;
	}
}
