package entity;

import java.io.*;
import java.util.StringTokenizer;

import entity.AllEnums.*;

// Details of a particular lesson block
public class Lesson implements IOData<Lesson>
{
	// Lesson ID - Eg. 1
	private int lessonID;
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
	
	public Lesson()
	{
		this.lessonID = -1;
		this.courseID = "";
		this.type = LessonType.DEFAULT;
		this.weekly = WeekType.DEFAULT;
		this.lessonPeriod = null;
		this.isOnline = false;
		this.location = null;
	}
	
	// To preload the data to create the text file, can be removed later on
	public Lesson(int lessonID, String courseID, LessonType type, WeekType weekly,
			Period lessonPeriod, boolean isOnline, Location location)
	{
		this.lessonID = lessonID;
		this.courseID = courseID;
		this.type = type;
		this.weekly = weekly;
		this.lessonPeriod = lessonPeriod;
		this.isOnline = isOnline;
		this.location = location;
	}
	
	public int getLessonID() {
		return this.lessonID;
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
	public String toString()
	{
		return lessonID + "|" + courseID + "|" + type + "|" + weekly + "|"
				+ lessonPeriod + "|" + isOnline + "|" + location;
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
	public Lesson readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		this.lessonID = Integer.parseInt(star.nextToken().trim());
		this.courseID = star.nextToken().trim();
		this.type = LessonType.valueOf(star.nextToken().trim());
		this.weekly = WeekType.valueOf(star.nextToken().trim());
		
		String pdatas = star.nextToken().trim();
		
		String[] pdata = pdatas.split(",");
		String[] stdata = pdata[0].split(":");
		String[] etdata = pdata[1].split(":");
		
		Time[] tdata = {null,null};
		tdata[0] = new Time(Integer.parseInt(stdata[0]),Integer.parseInt(stdata[1]));
		tdata[1] = new Time(Integer.parseInt(etdata[0]),Integer.parseInt(etdata[1]));
		Day day = Day.DEFAULT;
		
		day = Day.valueOf(pdata[2]);
		this.lessonPeriod = new Period(tdata[0], tdata[1], day);
		this.isOnline = false;
		if(star.nextToken().trim().equals("true"))
			this.isOnline = true;
		String[] ldata = star.nextToken().trim().split("~");
		this.location = new Location(ldata[0],ldata[1],ldata[2]);
		
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
