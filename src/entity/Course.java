package entity;

import java.util.List;

import entity.AllEnums.Day;

// Course along with CoursePlan assigned to Student
public class Course
{
	// Course name - Eg. "Algorithms"
	private String name;
	// School - Eg. "SCSE"
	String school;
	// Course ID - Eg. "CZ2001"
	private String courseID;
	// Course AU - Eg. 3
	private int courseAU;
	// Course plan - Stores details of particular course index
	private CoursePlan coursePlan;
	// May need to save semester details of this course
	// Eg. AY 2020 Sem 1

	// To preload the data to create the text file, can be removed later on
	public Course(String name, String school, String courseID, int courseAU, CoursePlan coursePlan)
	{
		this.name = name;
		this.school = school;
		this.courseID = courseID;
		this.courseAU = courseAU;
		this.coursePlan = coursePlan;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSchool()
	{
		return this.school;
	}

	public void setSchool(String school)
	{
		this.school = school;
	}

	public String getCourseID()
	{
		return this.courseID;
	}

	public void setCourseID(String courseID)
	{
		this.courseID = courseID;
	}

	public int getCourseAU()
	{
		return this.courseAU;
	}

	public void setCourseAU(int courseAU)
	{
		this.courseAU = courseAU;
	}

	public CoursePlan getCoursePlan()
	{
		return this.coursePlan;
	}

	public void setCoursePlan(CoursePlan coursePlan)
	{
		this.coursePlan = coursePlan;
	}

}
