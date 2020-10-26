package entity;

import java.io.*;

import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;

// Details of a particular lesson block
public class Lesson implements IOData<Lesson>
{
	// Course ID - Eg. "CZ2001"
	private String courseID;
	// Type - Eg. LessonType.LAB
	private LessonType type;
	// Weekly - Eg. WeekType.EVEN
	private WeekType weekly;
	// Lesson Period - Details of the lesson time and duration
	private Period lessonPeriod;
	// Is Online - Is the lesson an online lesson or a physical lesson
	private boolean isOnline;
	// Location - Details of the lesson location
	private Location location;

	// To preload the data to create the text file, can be removed later on
	public Lesson(String courseID, LessonType type, WeekType weekly,
			Period lessonPeriod, boolean isOnline, Location location)
	{
		this.courseID = courseID;
		this.type = type;
		this.weekly = weekly;
		this.lessonPeriod = lessonPeriod;
		this.isOnline = isOnline;
		this.location = location;
	}

	public String getCourseID()
	{
		return this.courseID;
	}

	public void setCourseID(String courseID)
	{
		this.courseID = courseID;
	}

	public LessonType getType()
	{
		return this.type;
	}

	public void setType(LessonType type)
	{
		this.type = type;
	}

	public WeekType getWeekly()
	{
		return this.weekly;
	}

	public void setWeekly(WeekType weekly)
	{
		this.weekly = weekly;
	}

	public Period getLessonPeriod()
	{
		return this.lessonPeriod;
	}

	public void setLessonPeriod(Period lessonPeriod)
	{
		this.lessonPeriod = lessonPeriod;
	}

	public boolean getIsOnline()
	{
		return this.isOnline;
	}

	public void setIsOnline(boolean isOnline)
	{
		this.isOnline = isOnline;
	}

	public Location getLocation()
	{
		return this.location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	@Override
	public String toStringData()
	{
		return courseID + "|" + type + "|" + weekly + "|"
				+ lessonPeriod + "|" + isOnline + "|" + location;
	}
	
	@Override
	public boolean writeDataToFile(String fileName, boolean overwrite)
	{
		try {
			FileWriter fw = new FileWriter(fileName,overwrite);
			fw.write(toStringData());;
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
	public Lesson readDataFile(String fileLine)
	{
		String[] data = fileLine.split("|");
		
		for(int i=0; i<data.length; i++)
			System.out.println(data[i]);
		
		return this;
	}

}
