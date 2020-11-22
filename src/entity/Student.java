package entity;

import entity.AllEnums.*;
import java.io.*;
import java.util.*;

import control.Container;

/**
 * The student account that inherits the LoginAccount. Stores various
 * information pertaining to Student.
 */
public class Student extends LoginAccount implements IOData<Student> {
	/**
	 * The matriculation number of the student. E.g. "U1234567A"
	 */
	String matricNo;
	/**
	 * The gender of the student. E.g. "MALE"
	 */
	Gender gender;
	/**
	 * The nationality of the student. E.g. "Singaporean"
	 */
	String nationality;
	/**
	 * The maximum AU which the student can apply for in a semester. E.g. 21
	 */
	int maxAU;
	/**
	 * The list of courses that the student has successfully applied for in the
	 * current semester.
	 */
	List<CoursePlan> coursePlanList;
	/**
	 * The period in which the student can access the program.
	 */
	Period accessPeriod;
	/**
	 * The date which the students can access the program.
	 */
	Date accessDate;

	/**
	 * The list of courses that the student is exempted from. Stores the course ID.
	 * E.g. "CZ2001"
	 */
	List<String> exemptedCourseList;

	/**
	 * The email of the student.
	 */
	String email;

	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public Student() {
		super();
		this.matricNo = "";
		this.nationality = "";
		this.maxAU = -1;
		this.gender = Gender.DEFAULT;
		;
		coursePlanList = new ArrayList<CoursePlan>();
		this.accessPeriod = null;
		this.accessDate = null;
		exemptedCourseList = new ArrayList<String>();
		this.email = null;
	}

	/**
	 * Class constructor that specifies the course objects to create.
	 * 
	 * @param name         The name of the Student.
	 * @param userName     The username of the account.
	 * @param password     The password of the account.
	 * @param type         The type of the account.
	 * @param matricNo     The matriculation number of the student.
	 * @param gender       The gender of the student.
	 * @param nationality  The nationality of the student.
	 * @param maxAU        The maximum AU which the student can apply for in a
	 *                     semester.
	 * @param accessPeriod The period in which the student can access the system.
	 * @param accessDate   The date which the students can access the system.
	 * @param email
	 */
	public Student(String name, String userName, String password, AccountType type, String matricNo, Gender gender,
			String nationality, int maxAU, Period accessPeriod, Date accessDate, String email) {
		super(name, userName, password, type);
		this.matricNo = matricNo;
		this.nationality = nationality;
		this.maxAU = maxAU;
		this.gender = gender;
		this.accessPeriod = accessPeriod;
		this.accessDate = accessDate;
		coursePlanList = new ArrayList<CoursePlan>();
		exemptedCourseList = new ArrayList<String>();
		this.email = email;
	}

	/**
	 * Gets the matriculation number of the student
	 * 
	 * @return The student's matriculation number.
	 */
	public String getMatricNo() {
		return this.matricNo;
	}

	/**
	 * Gets the gender of the student.
	 * 
	 * @return The student's gender.
	 */
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * Gets the nationality of the student.
	 * 
	 * @return The student's nationality.
	 */
	public String getNationality() {
		return this.nationality;
	}

	/**
	 * Gets the maximum number of AU the student is allowed.
	 * 
	 * @return The maximum numer of AU
	 */
	public int getMaxAU() {
		return this.maxAU;
	}

	/**
	 * Gets the list of courses that the student has successfully applied for in the
	 * current semester.
	 * 
	 * @return The list of course plan the student has
	 */
	public List<CoursePlan> getCoursePlan() {
		return this.coursePlanList;
	}

	/**
	 * Sets the access period for student to access the program.
	 * 
	 * @param accessPeriod The period in which the student can access the system.
	 */
	public void setAccessPeriod(Period accessPeriod) {
		this.accessPeriod = accessPeriod;
	}

	/**
	 * Sets the access date for students to access the program.
	 * 
	 * @param accessDate The date which the students can access the program.
	 */
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	/**
	 * Gets the access period for the student.
	 * 
	 * @return The student's access period.
	 */
	public Period getAccessPeriod() {
		return this.accessPeriod;
	}

	/**
	 * Gets the access date for the student.
	 * 
	 * @return The student's access date.
	 */
	public Date getAccessDate() {
		return this.accessDate;
	}

	/**
	 * Gets the list of courses which the student is exempted from.
	 * 
	 * @return The student's list of exempted courses
	 */
	public List<String> getExemptedCourseList() {
		return this.exemptedCourseList;
	}

	/**
	 * Sets the the list of courses which the student is exempted from.
	 * 
	 * @param exemptedCourseList The list of courses that the student is exempted
	 *                           from.
	 */
	public void setExemptedCourseList(List<String> exemptedCourseList) {
		this.exemptedCourseList = exemptedCourseList;
	}

	/**
	 * Gets the email address of the student.
	 * 
	 * @return The student's email.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Gets all information on the course in String format.
	 */
	public String toString() {
		List<Integer> strList = new ArrayList<Integer>();
		for (int i = 0; i < coursePlanList.size(); i++) {
			strList.add(coursePlanList.get(i).getIndex());
		}

		return super.getName() + "|" + super.getUserName() + "|" + super.getType() + "|" + matricNo + "|" + nationality
				+ "|" + maxAU + "|" + gender + "|" + strList + "|" + exemptedCourseList + "|"
				+ accessPeriod.toTimeString() + "|" + accessDate + "|" + email + "|" + super.getPassword();
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
			System.out.println("File for overwriting Student data not found!");
			return false;
		}
	}

	/**
	 * To read each line of data from the text file.
	 * 
	 * @param fileLine To indicate which line of code to read from.
	 * @return The student information.
	 */
	@Override
	public Student readDataFile(String fileLine) {
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st, "|"); // pass in the string to the string tokenizer using
																// delimiter ","

		super.setName(star.nextToken().trim());
		super.setUserName(star.nextToken().trim());
		super.setType(AccountType.valueOf(star.nextToken().trim()));
		this.matricNo = star.nextToken().trim();
		this.nationality = star.nextToken().trim();
		this.maxAU = Integer.parseInt(star.nextToken().trim());
		this.gender = Gender.valueOf(star.nextToken().trim());

		String str = star.nextToken().trim();
		str = str.substring(1, str.length() - 1); // remove the first and last char, which are the [ ]

		if (!str.isBlank()) {
			List<String> coursePlanIndex = Arrays.asList(str.split(",", -1)); // store as [1,2,3,...]

			for (int i = 0; i < coursePlanIndex.size(); i++) {
				for (int k = 0; k < Container.coursePlanList.size(); k++) {
					if (Integer.parseInt(coursePlanIndex.get(i).trim()) == Container.coursePlanList.get(k).getIndex()) {
						coursePlanList.add(Container.coursePlanList.get(k));
					}
				}
			}
		}

		String exemptedCourseID = star.nextToken().trim();
		exemptedCourseID = exemptedCourseID.substring(1, exemptedCourseID.length() - 1); // remove the first and last
																							// char, which are the [ ]

		if (!exemptedCourseID.isBlank()) {
			List<String> exemptedCourseIDList = Arrays.asList(exemptedCourseID.split(",", -1)); // store as [1,2,3,...]

			for (int i = 0; i < exemptedCourseIDList.size(); i++) {
				this.exemptedCourseList.add(exemptedCourseIDList.get(i).trim());
			}
		}

		String[] apdata = star.nextToken().trim().split(",");
		String[] stdata = apdata[0].split(":");
		String[] etdata = apdata[1].split(":");
		Time[] tdata = { null, null };
		tdata[0] = new Time(Integer.parseInt(stdata[0]), Integer.parseInt(stdata[1]));
		tdata[1] = new Time(Integer.parseInt(etdata[0]), Integer.parseInt(etdata[1]));
		Day day = Day.DEFAULT;
		this.accessPeriod = new Period(tdata[0], tdata[1], day);

		String[] addata = star.nextToken().trim().split(",");
		this.accessDate = new Date(Integer.parseInt(addata[0]), Integer.parseInt(addata[1]),
				Integer.parseInt(addata[2]));
		this.email = star.nextToken().trim();
		super.setPassword(star.nextToken().trim());

		return this;
	}
}
