package entity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import control.Container;
import entity.AllEnums.Day;

// Course along with CoursePlan assigned to Student
public class Course implements IOData<Course>
{
	// Course name - Eg. "Algorithms"
	private String name;
	// School - Eg. "SCSE"
	private String school;
	// Course ID - Eg. "CZ2001"
	private String courseID;
	// Course AU - Eg. 3
	private int courseAU;

	public Course()
	{
		this.name = "";
		this.school = "";
		this.courseID = "";
		this.courseAU = 0;
	}

	// To preload the data to create the text file, can be removed later on
	public Course(String name, String school, String courseID, int courseAU)
	{
		this.name = name;
		this.school = school;
		this.courseID = courseID;
		this.courseAU = courseAU;
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
	
	public String toString()
	{
		return name + "|" + school + "|" + courseID + "|" + courseAU;
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
			System.out.println("File for overwriting Course data not found!");
			return false;
		}
	}

	@Override
	public Course readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		this.name = star.nextToken().trim();
		this.school = star.nextToken().trim();
		this.courseID = star.nextToken().trim();
		this.courseAU = Integer.parseInt(star.nextToken().trim());
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
