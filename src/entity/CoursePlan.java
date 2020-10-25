package entity;

import java.util.*;

// Details of a particular course index
// Stores the "timetable" of lessons
public class CoursePlan
{
	// Course ID - Eg. "CZ2001"
	private String courseID;
	// Group ID - Eg. "SSP2"
	private String groupID;
	// Index - Eg. 10192
	private int index;
	// List of lessons - Stores the different lessons of this index
	private List<Lesson> lessons;

	public CoursePlan(String courseID, String groupID, int index)
	{
		this.courseID = courseID;
		this.groupID = groupID;
		this.index = index;
		this.lessons = new ArrayList<Lesson>();
	}

	public String getCourseID()
	{
		return this.courseID;
	}

	public void setCourseID(String courseID)
	{
		this.courseID = courseID;
	}

	public String getGroupID()
	{
		return this.groupID;
	}

	public void setGroupID(String groupID)
	{
		this.groupID = groupID;
	}

	public int getIndex()
	{
		return this.index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public List<Lesson> getLessons()
	{
		return this.lessons;
	}

	public void setLessons(List<Lesson> lessons)
	{
		this.lessons = lessons;
	}

}
