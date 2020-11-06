package entity;

import java.io.*;
import java.util.*;
import control.Container;
import entity.AllEnums.*;

// Details of the slots of a particular course index
public class CourseSlots implements IOData<CourseSlots>
{
	// Total Slots - Eg. 40
	// Modifiable by Admin accounts
	private int totalSlots;
	// Course Plan - Contains course index details
	private CoursePlan coursePlan;
	// Waiting List - List of Students waiting for avail slots to add
	// Stores Student matricNo
	private List<String> waitingList;
	// Slot List - List of slots already assigned to Students
	// Stores Student matricNo
	private List<String> slotList;
	
	
	public CourseSlots()
	{
		this.totalSlots = -1;
		this.coursePlan = null;
		this.waitingList = new ArrayList<String>();
		this.slotList = new ArrayList<String>();
	}
	

	public CourseSlots(int totalSlots, CoursePlan coursePlan)
	{
		this.totalSlots = totalSlots;
		this.coursePlan = coursePlan;
		this.waitingList = new ArrayList<String>();
		this.slotList = new ArrayList<String>();
	}

	public int getTotalSlots()
	{
		return this.totalSlots;
	}

	public void setTotalSlots(int totalSlots)
	{
		this.totalSlots = totalSlots;
	}

	public CoursePlan getCoursePlan()
	{
		return this.coursePlan;
	}

	public void setCoursePlan(CoursePlan coursePlan)
	{
		this.coursePlan = coursePlan;
	}

	public List<String> getWaitingList()
	{
		return this.waitingList;
	}

	// I don't think need to set waiting list
//	public void setWaitingList(List<String> waitingList)
//	{
//		this.waitingList = waitingList;
//	}

	public List<String> getSlotList()
	{
		return this.slotList;
	}

	public void setSlotList(List<String> slotList)
	{
		this.slotList = slotList;
	}
	
	
	public String toString()
	{		

		return totalSlots + "|" + coursePlan.getIndex() + "|" + waitingList + "|" + slotList;
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
	public CourseSlots readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		this.totalSlots = Integer.parseInt(star.nextToken().trim());
		int index = Integer.parseInt(star.nextToken().trim());

		//Course
		for(int i = 0; i < Container.coursePlanList.size(); i++) 
		{
			if(Container.coursePlanList.get(i).getIndex() == index)
			{
				this.coursePlan = Container.coursePlanList.get(i);
				break;
			}
		}

		String waitingList = star.nextToken().trim();
		waitingList = waitingList.substring(1, waitingList.length() - 1); //remove the first and last char, which are the [ ]
	    
		if(!waitingList.isBlank()) {
	    	List<String> tempWaitingList = Arrays.asList(waitingList.split(",",-1)); // store as [1,2,3,...]
	    	for(int i = 0;i < tempWaitingList.size(); i++) 
			{
				this.waitingList.add(tempWaitingList.get(i).trim());
			}
	    }

	    
		String slotList = star.nextToken().trim();
		slotList = slotList.substring(1, slotList.length() - 1);
	    if(!slotList.isBlank()) {
	    	List<String> tempSlotList = Arrays.asList(slotList.split(",",-1)); // store as [1,2,3,...]
			for(int i = 0;i < tempSlotList.size(); i++) 
			{
				this.slotList.add(tempSlotList.get(i).trim());
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
