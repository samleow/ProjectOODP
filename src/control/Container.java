package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.*;

/**
 * Container class to store constant variables and data.
 */
public class Container {
	/**
	 * Private constructor to make class static.
	 */
	private Container() {
	}

	/**
	 * Set to true if running on IDE, else set to false if running on console.
	 */
	public static final boolean DEBUG_MODE = false;
	/**
	 * Input integer for user to return to previous menu.
	 */
	public static final int BREAK_MENU = -1;
	/**
	 * Location and name of the admin data text file.
	 */
	public static final String ADMIN_FILE = "Admin.txt";
	/**
	 * Location and name of the course data text file.
	 */
	public static final String COURSE_FILE = "Course.txt";
	/**
	 * Location and name of the course plan data text file.
	 */
	public static final String COURSEPLAN_FILE = "CoursePlan.txt";
	/**
	 * Location and name of the course slots data text file.
	 */
	public static final String COURSESLOT_FILE = "CourseSlots.txt";
	/**
	 * Location and name of the lesson data text file.
	 */
	public static final String LESSON_FILE = "Lesson.txt";
	/**
	 * Location and name of the student data text file.
	 */
	public static final String STUDENT_FILE = "StudentAccount.txt";
	/**
	 * Location and name of the sender email credentials text file.
	 */
	public static final String SENDER_EMAIL_FILE = "senderEmail.txt";

	/**
	 * List of Lesson data.
	 */
	public static ArrayList<Lesson> lessonList = new ArrayList<Lesson>();
	/**
	 * List of CoursePlan data.
	 */
	public static ArrayList<CoursePlan> coursePlanList = new ArrayList<CoursePlan>();
	/**
	 * List of Student data.
	 */
	public static ArrayList<Student> studentList = new ArrayList<Student>();
	/**
	 * List of Course data.
	 */
	public static ArrayList<Course> courseList = new ArrayList<Course>();
	/**
	 * List of CourseSlots data.
	 */
	public static ArrayList<CourseSlots> courseSlotsList = new ArrayList<CourseSlots>();
	/**
	 * List of Admin data.
	 */
	public static ArrayList<Admin> adminList = new ArrayList<Admin>();

	/**
	 * Reads the data file, extracts the Lesson data and stores it into a list.
	 * 
	 * @param fileName The filename of the data file.
	 * @param list     The list of Lesson data to be updated.
	 * @return Returns true if read from file is successful.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static boolean readLessonFile(String fileName, ArrayList<Lesson> list) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(fileName);

		for (int i = 0; i < stringArray.size(); i++) {
			if (!stringArray.get(i).toString().isBlank()) {
				Lesson l = new Lesson();
				l.readDataFile((String) stringArray.get(i));
				list.add(l);
			}
		}
		return true;
	}

	/**
	 * Reads the data file, extracts the Course data and stores it into a list.
	 * 
	 * @param fileName The filename of the data file.
	 * @param list     The list of Course data to be updated.
	 * @return Returns true if read from file is successful.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static boolean readCourseFile(String fileName, ArrayList<Course> list) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(fileName);

		for (int i = 0; i < stringArray.size(); i++) {
			if (!stringArray.get(i).toString().isBlank()) {
				Course l = new Course();
				l.readDataFile((String) stringArray.get(i));
				list.add(l);
			}
		}

		return true;
	}

	/**
	 * Reads the data file, extracts the CoursePlan data and stores it into a list.
	 * 
	 * @param fileName The filename of the data file.
	 * @param list     The list of CoursePlan data to be updated.
	 * @return Returns true if read from file is successful.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static boolean readCoursePlanFile(String fileName, ArrayList<CoursePlan> list) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(fileName);

		for (int i = 0; i < stringArray.size(); i++) {
			if (!stringArray.get(i).toString().isBlank()) {
				CoursePlan l = new CoursePlan();
				l.readDataFile((String) stringArray.get(i));
				list.add(l);
			}
		}
		return true;
	}

	/**
	 * Reads the data file, extracts the CourseSlots data and stores it into a list.
	 * 
	 * @param fileName The filename of the data file.
	 * @param list     The list of CourseSlots data to be updated.
	 * @return Returns true if read from file is successful.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static boolean readCourseSlotsFile(String fileName, ArrayList<CourseSlots> list) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(fileName);

		for (int i = 0; i < stringArray.size(); i++) {
			if (!stringArray.get(i).toString().isBlank()) {
				CourseSlots l = new CourseSlots();
				l.readDataFile((String) stringArray.get(i));
				list.add(l);
			}
		}
		return true;
	}

	/**
	 * Reads the data file, extracts the Student data and stores it into a list.
	 * 
	 * @param fileName The filename of the data file.
	 * @param list     The list of Student data to be updated.
	 * @return Returns true if read from file is successful.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static boolean readStudentFile(String fileName, ArrayList<Student> list) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(fileName);

		for (int i = 0; i < stringArray.size(); i++) {
			if (!stringArray.get(i).toString().isBlank()) {
				Student l = new Student();
				l.readDataFile((String) stringArray.get(i));
				list.add(l);
			}
		}
		return true;
	}

	/**
	 * Reads the data file, extracts the Admin data and stores it into a list.
	 * 
	 * @param fileName The filename of the data file.
	 * @param list     The list of Admin data to be updated.
	 * @return Returns true if read from file is successful.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static boolean readAdminFile(String fileName, ArrayList<Admin> list) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(fileName);

		for (int i = 0; i < stringArray.size(); i++) {
			if (!stringArray.get(i).toString().isBlank()) {
				Admin a = new Admin();
				a.readDataFile((String) stringArray.get(i));
				list.add(a);
			}
		}

		return true;
	}

	/**
	 * Overwrites the data file with the list of data specified.
	 * 
	 * @param fileName The filename of the data file.
	 * @param l        The list of the data to be stored.
	 * @return Returns true if overwrite is successful.
	 */
	public static boolean overwriteFileWithData(String fileName, ArrayList<? extends IOData> l) {

		for (int i = 0; i < l.size(); i++) {
			if (i == 0) {
				l.get(i).writeDataToFile(fileName, true);
			} else {
				l.get(i).writeDataToFile(fileName, false);
			}
		}
		return true;
	}

	/**
	 * Reads the contents of the given file.
	 * 
	 * @param fileName The filename of the data file.
	 * @return Returns a list of String of data from file.
	 * @throws IOException Throws an exception when the read from file failed.
	 */
	public static List read(String fileName) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}

	/**
	 * Returns a CourseSlots with the given index.
	 * 
	 * @param index The index of the Course.
	 * @return Returns the CourseSlots found, else returns null.
	 */
	public static CourseSlots getCourseSlotByIndex(int index) {
		for (int i = 0; i < courseSlotsList.size(); i++) {
			if (courseSlotsList.get(i).getCoursePlan().getIndex() == index) {
				return courseSlotsList.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a CoursePlan with the given index.
	 * 
	 * @param index The index of the Course.
	 * @return Returns the CoursePlan found, else returns null.
	 */
	public static CoursePlan getCoursePlanByIndex(int index) {
		for (int i = 0; i < coursePlanList.size(); i++) {
			if (coursePlanList.get(i).getIndex() == index) {
				return coursePlanList.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a CoursePlan with the given index from a given list.
	 * 
	 * @param index The index of the Course.
	 * @param l     The list of CoursePlan to be searched.
	 * @return Returns the CoursePlan found, else returns null.
	 */
	public static CoursePlan getCoursePlanByIndex(int index, List<CoursePlan> l) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getIndex() == index) {
				return l.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a Student with the given username.
	 * 
	 * @param username The username of the Student.
	 * @return Returns the Student found, else return null.
	 */
	public static Student getStudentByUsername(String username) {
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getUserName().equals(username)) {
				return studentList.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a Student with the given matric number.
	 * 
	 * @param matricno The matric number of the Student.
	 * @return Returns the Student found, else return null.
	 */
	public static Student getStudentByMatricNo(String matricno) {
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getMatricNo().equals(matricno)) {
				return studentList.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns an Admin with the given username.
	 * 
	 * @param username The username of the Admin.
	 * @return Returns the Admin found, else return null.
	 */
	public static Admin getAdminByUsername(String username) {
		for (int i = 0; i < adminList.size(); i++) {
			if (adminList.get(i).getUserName().equals(username)) {
				return adminList.get(i);
			}
		}
		return null;
	}
}
