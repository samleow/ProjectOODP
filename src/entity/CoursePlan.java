package entity;

import java.io.*;
import java.util.*;
import control.Container;
import entity.AllEnums.*;

/**
 * Details of a particular course index
 * Stores the 'Time Table' of lessons
 */
public class CoursePlan implements IOData<CoursePlan>
{
	/**
	 * The ID of the course.
	 * E.g. "CZ2001"
	 */
	private String courseID;
	/**
	 * The ID of the group.
	 * E.g. "SSP2"
	 */
	private String groupID;
	/**
	 * The course index.
	 * Assumption: All index numbers are unique among course plans.
	 * E.g. "10102"
	 */
	private int index;
	/**
	 * List of Lessons that stores the different lessons for this course plan.
	 */
	private List<Lesson> lessons;
	/**
	 * The course that this course plan belongs to.
	 */
	private Course course;
	
	
	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public CoursePlan()
	{
		this.courseID = "";
		this.groupID = "";
		this.index = -1;
		this.course = null;
		this.lessons = new ArrayList<Lesson>();		
	}
	
	/**
	 * Class constructor for AdminControl class to specify the objects to create.
	 * @param courseID The ID of the course.
	 * @param groupID The ID of the group.
	 * @param index The course index.
	 */
	public CoursePlan(String courseID, String groupID, int index)
	{
		this.courseID = courseID;
		this.groupID = groupID;
		this.index = index;
		this.lessons = new ArrayList<Lesson>();	
	}

	
	/**
	 * Class constructor that specifies the objects to create.
	 * @param courseID The ID of the course.
	 * @param groupID The ID of the group.
	 * @param index The course index.
	 * @param course The course that this course plan belongs to.
	 */
	public CoursePlan(String courseID, String groupID, int index, Course course)
	{
		this.courseID = courseID;
		this.groupID = groupID;
		this.index = index;
		this.course = course;
		this.lessons = new ArrayList<Lesson>();	
	}

	/**
	 * Gets the ID of the course.
	 * @return The course's ID
	 */
	public String getCourseID()
	{
		return this.courseID;
	}

	/**
	 * Sets the ID for the course.
	 * @param courseID The ID of the course.
	 */
	public void setCourseID(String courseID)
	{
		this.courseID = courseID;
	}

	/**
	 * Gets the ID of the group.
	 * @return The group's ID.
	 */
	public String getGroupID()
	{
		return this.groupID;
	}

	/**
	 * Sets the ID for the group.
	 * @param groupID The ID of the group.
	 */
	public void setGroupID(String groupID)
	{
		this.groupID = groupID;
	}

	/**
	 * Gets the course index.
	 * @return The course index.
	 */
	public int getIndex()
	{
		return this.index;
	}

	/**
	 * Sets the course index.
	 * @param index The course index.
	 */
	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * Gets the list of lessons that this course plan has.
	 * @return The list of lessons.
	 */
	public List<Lesson> getLessons()
	{
		return this.lessons;
	}

	/**
	 * Sets the lessons that this course plan has.
	 * @param lessons List of Lessons that stores the different lessons for this course plan.
	 */
	public void setLessons(List<Lesson> lessons)
	{
		this.lessons = lessons;
	}
	
	/**
	 * Gets all information on the lessons in this course plan in String format.
	 */
	@Override
	public String toString()
	{
		List<Integer> strList = new ArrayList<Integer>();
		for(int i = 0; i < lessons.size(); i++) 
		{
			strList.add(lessons.get(i).getLessonID());
		}
		
		return courseID + "|" + groupID + "|" + index + "|" + strList;
	}
	
	/**
	 * For writing/overwriting into the text file.
	 * @param fileName The name of the file to write to.
	 * @param overwrite To indicate whether to overwrite the file or to simply append at the bottom of the text file.
	 * @return Boolean value to confirm if writing is successful.
	 */
	@Override
	public boolean writeDataToFile(String fileName, boolean overwrite)
	{
		try {
			FileWriter fw = new FileWriter(fileName,!overwrite);
			fw.write(toString()+"\n");
			fw.close();
			return true;
		}
		catch(IOException e)
		{
			System.out.println("File for overwriting Lesson data not found!");
			return false;
		}
	}

	
	/**
	 * To read each line of data from the text file.
	 * @param fileLine To indicate which line of code to read from.
	 * @return The course's information.
	 */
	@Override
	public CoursePlan readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		this.courseID = star.nextToken().trim();
		this.groupID = star.nextToken().trim();
		this.index = Integer.parseInt(star.nextToken().trim());
		
		//Course
		for(int i = 0; i < Container.courseList.size(); i++) 
		{
			if(Container.courseList.get(i).getCourseID() == courseID)
			{
				this.course = Container.courseList.get(i);
				break;
			}
		}
		
		String str  = star.nextToken().trim();
        str = str.substring(1, str.length() - 1); //remove the first and last char, which are the [ ]
        
        if(str.isBlank()) {
        	return this;
        }
        

        List<String> lessonIDs = Arrays.asList(str.split(",",-1)); // store as [1,2,3,...]
		
		for(int i = 0;i < lessonIDs.size(); i++) 
		{
			for (int k=0;k < Container.lessonList.size();k++)
			{
				//Container.lessonList.get(k).toString()
				if(Integer.parseInt(lessonIDs.get(i).trim()) == Container.lessonList.get(k).getLessonID()) {
					lessons.add(Container.lessonList.get(k));
				}
			}
		}

		return this;
	}
}
