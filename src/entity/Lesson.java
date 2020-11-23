package entity;

import java.io.*;

import java.util.StringTokenizer;

import entity.AllEnums.*;

/**
 * Details of a particular lesson
 */
public class Lesson implements IOData<Lesson> {
	/**
	 * The ID of the lesson. E.g. 2
	 */
	private int lessonID;
	/**
	 * The ID of the course. E.g. "CZ2001"
	 */
	private String courseID;
	/**
	 * The type of lesson. E.g. LAB
	 */
	private LessonType type;
	/**
	 * The type of weekly basis (Weekly, even or odd). E.G. EVEN
	 */
	private WeekType weekly;
	/**
	 * The start time, end time and the day of the week that this lesson took place
	 * on.
	 */
	private Period lessonPeriod;
	/**
	 * Whether this lesson is online lesson or physical lesson.
	 */
	private boolean isOnline;
	/**
	 * The location of where the lesson took place.
	 */
	private Location location;

	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public Lesson() {
		this.lessonID = -1;
		this.courseID = "";
		this.type = LessonType.DEFAULT;
		this.weekly = WeekType.DEFAULT;
		this.lessonPeriod = null;
		this.isOnline = false;
		this.location = null;
	}

	/**
	 * Class constructor that specifies the course objects to create.
	 * 
	 * @param lessonID     The ID of the lesson.
	 * @param courseID     The ID of the course.
	 * @param type         The type of lesson.
	 * @param weekly       The type of weekly basis.
	 * @param lessonPeriod The start time, end time and the day of the week that
	 *                     this lesson took place on.
	 * @param isOnline     Whether this lesson is online lesson or physical lesson.
	 * @param location     The location of where the lesson took place.
	 */
	public Lesson(int lessonID, String courseID, LessonType type, WeekType weekly, Period lessonPeriod,
			boolean isOnline, Location location) {
		this.lessonID = lessonID;
		this.courseID = courseID;
		this.type = type;
		this.weekly = weekly;
		this.lessonPeriod = lessonPeriod;
		this.isOnline = isOnline;
		this.location = location;
	}

	/**
	 * Checks if this lesson clashes with any other lesson.
	 * 
	 * @param l The Lesson that we want to check.
	 * @return Boolean value on whether it clashes.
	 */
	public boolean clashWith(Lesson l) {
		// check if weekly type have clashes
		if (!(this.weekly.equals(WeekType.WEEKLY) || l.getWeekly().equals(WeekType.WEEKLY)
				|| this.weekly.equals(l.getWeekly())))
			return false;

		// check if lesson periods clash
		if (this.lessonPeriod.clashWith(l.getLessonPeriod()))
			return true;

		return false;
	}

	/**
	 * Gets the ID of the lesson.
	 * 
	 * @return This lesson's ID.
	 */
	public int getLessonID() {
		return this.lessonID;
	}

	/**
	 * Gets the ID of the course.
	 * 
	 * @return This lesson's course ID
	 */
	public String getCourseID() {
		return this.courseID;
	}

	/**
	 * Sets the course ID for the lesson
	 * 
	 * @param courseID The ID of the course.
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * Sets the type for this lesson.
	 * 
	 * @param type The type of lesson.
	 */
	public void setType(LessonType type) {
		this.type = type;
	}

	/**
	 * Gets the type of weekly basis (EVEN, ODD or WEEKLY).
	 * 
	 * @return The type of weekly basis.
	 */
	public WeekType getWeekly() {
		return this.weekly;
	}

	/**
	 * Sets the type of weekly basis (EVEN, ODD or WEEKLY).
	 * 
	 * @param weekly The type of weekly basis.
	 */
	public void setWeekly(WeekType weekly) {
		this.weekly = weekly;
	}

	/**
	 * Gets the start time, end time and the day of the week that this lesson took
	 * place on.
	 * 
	 * @return This lesson's period
	 */
	public Period getLessonPeriod() {
		return this.lessonPeriod;
	}

	/**
	 * Sets when this lesson will take place.
	 * 
	 * @param lessonPeriod The start time, end time and the day of the week that
	 *                     this lesson took place on.
	 */
	public void setLessonPeriod(Period lessonPeriod) {
		this.lessonPeriod = lessonPeriod;
	}

	/**
	 * Sets whether the lesson is online or physical.
	 * 
	 * @param isOnline Whether this lesson is online or physical.
	 */
	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * Get the location where the lesson will take place.
	 * 
	 * @return Location of the lesson.
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Get whether the lesson is online or physical.
	 * 
	 * @return Boolean value on whether this lesson is online or physical.
	 */
	public boolean getIsOnline() {
		return this.isOnline;
	}

	/**
	 * Sets the location where the lesson will take place.
	 * 
	 * @param location The location of where the lesson took place.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Gets all information on the lesson in String format.
	 */
	@Override
	public String toString() {
		return lessonID + "|" + courseID + "|" + type + "|" + weekly + "|" + lessonPeriod + "|" + isOnline + "|"
				+ location;
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
			System.out.println("File for overwriting Lesson data not found!");
			return false;
		}
	}

	/**
	 * To read each line of data from the text file.
	 * 
	 * @param fileLine To indicate which line of code to read from.
	 * @return The lesson information.
	 */
	@Override
	public Lesson readDataFile(String fileLine) {
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st, "|"); // pass in the string to the string tokenizer using
																// delimiter ","

		this.lessonID = Integer.parseInt(star.nextToken().trim());
		this.courseID = star.nextToken().trim();
		this.type = LessonType.valueOf(star.nextToken().trim());
		this.weekly = WeekType.valueOf(star.nextToken().trim());

		String pdatas = star.nextToken().trim();

		String[] pdata = pdatas.split(",");
		String[] stdata = pdata[0].split(":");
		String[] etdata = pdata[1].split(":");

		Time[] tdata = { null, null };
		tdata[0] = new Time(Integer.parseInt(stdata[0]), Integer.parseInt(stdata[1]));
		tdata[1] = new Time(Integer.parseInt(etdata[0]), Integer.parseInt(etdata[1]));
		Day day = Day.DEFAULT;

		day = Day.valueOf(pdata[2]);
		this.lessonPeriod = new Period(tdata[0], tdata[1], day);
		this.isOnline = false;
		if (star.nextToken().trim().equals("true"))
			this.isOnline = true;
		String[] ldata = star.nextToken().trim().split("~");
		this.location = new Location(ldata[0], ldata[1], ldata[2]);

		return this;
	}
}
