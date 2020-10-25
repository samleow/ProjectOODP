package entity;

import java.util.*;

// Details of the slots of a particular course index
public class CourseSlots
{
	// Total Slots - Eg. 40
	// Modifiable by Admin accounts
	private int totalSlots;
	// Course Plan - Contains course index details
	private CoursePlan coursePlan;
	// Waiting List - List of Students waiting for avail slots to add
	// Stores Student matricNo
	private List<String> waitingList;
	// Slot List - List of slots already assigned to Students
	// Stores Student matricNo
	private List<String> slotList;

	public CourseSlots(int totalSlots, CoursePlan coursePlan)
	{
		this.totalSlots = totalSlots;
		this.coursePlan = coursePlan;
		this.waitingList = new ArrayList<String>();
		this.slotList = new ArrayList<String>();
	}

	public int getTotalSlots()
	{
		return this.totalSlots;
	}

	public void setTotalSlots(int totalSlots)
	{
		this.totalSlots = totalSlots;
	}

	public CoursePlan getCoursePlan()
	{
		return this.coursePlan;
	}

	public void setCoursePlan(CoursePlan coursePlan)
	{
		this.coursePlan = coursePlan;
	}

	public List<String> getWaitingList()
	{
		return this.waitingList;
	}

	// I don't think need to set waiting list
//	public void setWaitingList(List<String> waitingList)
//	{
//		this.waitingList = waitingList;
//	}

	public List<String> getSlotList()
	{
		return this.slotList;
	}

	public void setSlotList(List<String> slotList)
	{
		this.slotList = slotList;
	}
}
