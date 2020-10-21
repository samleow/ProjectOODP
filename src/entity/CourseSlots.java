package entity;

import java.util.List;

public class CourseSlots {
	private CoursePlan coursePlan;
	private int totalSlots;
	private List<String> waitingList;
	private List<String> slotList;
	
	public CoursePlan getCoursePlan() {
		return this.coursePlan;
	}
	
	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}
	
	public int getTotalSlots() {
		return this.totalSlots;
	}
	
	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}
	
	public List<String> getWaitingList() {
		return this.waitingList;
	}
	
	// I don't think need to set waiting list
//	public void setWaitingList(List<String> waitingList) {
//		this.waitingList = waitingList;
//	}
	
	public List<String> getSlotList() {
		return this.slotList;
	}
	
	public void setSlotList(List<String> slotList) {
		this.slotList = slotList;
	}
}
