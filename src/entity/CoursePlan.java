package entity;

import java.io.*;
import java.util.*;
import control.Container;
import entity.AllEnums.*;

// Details of a particular course index
// Stores the "timetable" of lessons
public class CoursePlan implements IOData<CoursePlan>
{
	// Course ID - Eg. "CZ2001"
	private String courseID;
	// Group ID - Eg. "SSP2"
	private String groupID;
	// Index - Eg. 10192
	// Assumption Index is unique among course plan
	private int index;
	// List of lessons - Stores the different lessons of this index
	private List<Lesson> lessons;
	//Course - Eg. "CZ2001"
	private Course course;
	
	public CoursePlan()
	{
		this.courseID = "";
		this.groupID = "";
		this.index = -1;
		this.course = null;
		this.lessons = new ArrayList<Lesson>();		
	}

	public CoursePlan(String courseID, String groupID, int index, Course course)
	{
		this.courseID = courseID;
		this.groupID = groupID;
		this.index = index;
		this.course = course;
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
	public Course getCourse()
	{
		return this.course;
	}

	public void setCourse(Course course)
	{
		this.course = course;
	}
	
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
	
	
	
	@Override
	public boolean updateLineInFile(String fileName, String[] keys)
	{
		// copy ori into temp
		// modify temp
		// save into ori
		
		// for now lessons don't need update
		
		return false;
	}
	
	// don't need for now
	public static void replaceLines(String fileName, String newLine) {
	    try {
	        // input the (modified) file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader(fileName));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        
	        // if line == the line to check
	        // if keys == keys from line
	        while ((line = file.readLine()) != null) {
	            line = newLine; // replace the line here
	            inputBuffer.append(line);
	            inputBuffer.append('\n');
	        }
	        file.close();

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream(fileName);
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}
}
