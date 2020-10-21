package entity;

import entity.AllEnums.Day;

public class Period {
	private Time startTime;
	private Time endTime;
	private Day day;
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public Day getDay() {
		return this.day;
	}
	
	public void setDay(Day day) {
		this.day = day;
	}
}
