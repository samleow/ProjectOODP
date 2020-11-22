package entity;

import java.io.*;
import java.util.*;
import control.Container;
import entity.AllEnums.*;

/**
 * Details of Course, along with Course Plan, assigned to Student.
 */
public class Course implements IOData<Course> {
	/**
	 * The name of the Course. E.g. "Algorithms"
	 */
	private String name;
	/**
	 * The name of the school for the course. E.g. "SCSE"
	 */
	private String school;
	/**
	 * The ID of the course or the course code. E.g. "CZ2001"
	 */
	private String courseID;
	/**
	 * The AU for the course. E.g. 21
	 */
	private int courseAU;
	/**
	 * The type of course. E.g. "CORE"
	 */
	private CourseType courseType;

	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public Course() {
		this.name = "";
		this.school = "";
		this.courseID = "";
		this.courseAU = -1;
		this.courseType = CourseType.DEFAULT;
	}

	/**
	 * Class constructor that specifies the course objects to create.
	 * 
	 * @param name       The name of the course.
	 * @param school     The name of the school for the course.
	 * @param courseID   The ID of the course or the course code.
	 * @param courseAU   The AU for the course.
	 * @param courseType The type of course.
	 */
	public Course(String name, String school, String courseID, int courseAU, CourseType courseType) {
		this.name = name;
		this.school = school;
		this.courseID = courseID;
		this.courseAU = courseAU;
		this.courseType = courseType;
	}

	/**
	 * Sets the name for the course.
	 * 
	 * @param name The name of the course.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the name of the school for the course.
	 * 
	 * @param school The name of the course's school name.
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * Gets the ID of the course.
	 * 
	 * @return This course's ID.
	 */
	public String getCourseID() {
		return this.courseID;
	}

	/**
	 * Sets the ID for the course.
	 * 
	 * @param courseID This course's ID
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * Gets the AU of the course.
	 * 
	 * @return This course's AU.
	 */
	public int getCourseAU() {
		return this.courseAU;
	}

	/**
	 * Sets the AU for the course
	 * 
	 * @param courseAU The AU for the course.
	 */
	public void setCourseAU(int courseAU) {
		this.courseAU = courseAU;
	}

	/**
	 * Gets the type of the course.
	 * 
	 * @return The course's type.
	 */
	public CourseType getCourseType() {
		return this.courseType;
	}

	/**
	 * Sets the type for the course.
	 * 
	 * @param courseType The type of course.
	 */
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	/**
	 * Gets all information on the course in String format.
	 */
	public String toString() {
		return name + "|" + school + "|" + courseID + "|" + courseAU + "|" + courseType;
	}

	/**
	 * For writing/overwriting into the text file.
	 * 
	 * @param fileName  The name of the file to write to.
	 * @param overwrite To indicate whether to overwrite the file or to simply
	 *                  append at the bottom of the text file.
	 * @return Boolean value to confirm if writing is successful.
	 */
	@Override
	public boolean writeDataToFile(String fileName, boolean overwrite) {
		try {
			FileWriter fw = new FileWriter(fileName, !overwrite);
			fw.write(toString() + "\n");
			fw.close();
			return true;
		} catch (IOException e) {
			System.out.println("File for overwriting Course data not found!");
			return false;
		}
	}

	/**
	 * To read each line of data from the text file.
	 * 
	 * @param fileLine To indicate which line of code to read from.
	 * @return The course information.
	 */
	@Override
	public Course readDataFile(String fileLine) {
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st, "|"); // pass in the string to the string tokenizer using
																// delimiter ","

		this.name = star.nextToken().trim();
		this.school = star.nextToken().trim();
		this.courseID = star.nextToken().trim();
		this.courseAU = Integer.parseInt(star.nextToken().trim());
		this.courseType = CourseType.valueOf(star.nextToken().trim());
		return this;
	}
}
