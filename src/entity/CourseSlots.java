package entity;

import java.io.*;
import java.util.*;
import control.Container;
import entity.AllEnums.*;

/**
 * Details of the slots for a particular course index.
 */
public class CourseSlots implements IOData<CourseSlots> {
	/**
	 * The total number of slots for the course plan. Modifiable by Admin accounts
	 * E.g. 10
	 */
	private int totalSlots;
	/**
	 * The Course plan that this course slot belongs to.
	 */
	private CoursePlan coursePlan;
	/**
	 * The list that stores the students waiting for available slots. Stores student
	 * matriculation number
	 */
	private List<String> waitingList;
	/**
	 * The list that stores the students that already had a slot. Stores student
	 * matriculation number.
	 */
	private List<String> slotList;

	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public CourseSlots() {
		this.totalSlots = -1;
		this.coursePlan = null;
		this.waitingList = new ArrayList<String>();
		this.slotList = new ArrayList<String>();
	}

	/**
	 * Class constructor that specifies the objects to create.
	 * 
	 * @param totalSlots The total number of slots for the course plan.
	 * @param coursePlan The Course plan that this course slot belongs to
	 */
	public CourseSlots(int totalSlots, CoursePlan coursePlan) {
		this.totalSlots = totalSlots;
		this.coursePlan = coursePlan;
		this.waitingList = new ArrayList<String>();
		this.slotList = new ArrayList<String>();
	}

	/**
	 * Gets the total number of slots for this course slot.
	 * 
	 * @return The total number of slots.
	 */
	public int getTotalSlots() {
		return this.totalSlots;
	}

	/**
	 * Sets the total number of slots for this course slots.
	 * 
	 * @param totalSlots The total number of slots for the course plan.
	 */
	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	/**
	 * Gets the course plan that this course slot belongs to.
	 * 
	 * @return The course plan.
	 */
	public CoursePlan getCoursePlan() {
		return this.coursePlan;
	}

	/**
	 * Gets the list of students that are waiting for a slot.
	 * 
	 * @return The waiting list.
	 */
	public List<String> getWaitingList() {
		return this.waitingList;
	}

	/**
	 * Gets the list of students that are already assigned a slot.
	 * 
	 * @return The slot list.
	 */
	public List<String> getSlotList() {
		return this.slotList;
	}

	/**
	 * Gets all information on the course slot in String format.
	 */
	public String toString() {
		return totalSlots + "|" + coursePlan.getIndex() + "|" + waitingList + "|" + slotList;
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
	 * @return The course slot's information.
	 */
	@Override
	public CourseSlots readDataFile(String fileLine) {
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st, "|"); // pass in the string to the string tokenizer using
																// delimiter ","

		this.totalSlots = Integer.parseInt(star.nextToken().trim());
		int index = Integer.parseInt(star.nextToken().trim());

		// Course
		for (int i = 0; i < Container.coursePlanList.size(); i++) {
			if (Container.coursePlanList.get(i).getIndex() == index) {
				this.coursePlan = Container.coursePlanList.get(i);
				break;
			}
		}

		String waitingList = star.nextToken().trim();
		waitingList = waitingList.substring(1, waitingList.length() - 1); // remove the first and last char, which are
																			// the [ ]

		if (!waitingList.isBlank()) {
			List<String> tempWaitingList = Arrays.asList(waitingList.split(",", -1)); // store as [1,2,3,...]
			for (int i = 0; i < tempWaitingList.size(); i++) {
				this.waitingList.add(tempWaitingList.get(i).trim());
			}
		}

		String slotList = star.nextToken().trim();
		slotList = slotList.substring(1, slotList.length() - 1);
		if (!slotList.isBlank()) {
			List<String> tempSlotList = Arrays.asList(slotList.split(",", -1)); // store as [1,2,3,...]
			for (int i = 0; i < tempSlotList.size(); i++) {
				this.slotList.add(tempSlotList.get(i).trim());
			}
		}
		return this;
	}
}
