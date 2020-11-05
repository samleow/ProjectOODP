package entity;

import entity.AllEnums.*;
import java.io.*;
import java.util.*;

import control.Container;


// Student account that inherits LoginAccount
public class Student extends LoginAccount implements IOData<Student>
{
	// Matric No - Eg. "U1234567A"
	String matricNo;
	// Gender - Eg. Gender.MALE
	Gender gender;
	// Nationality - Eg. "Singaporean"
	String nationality;
	// Max AU - Eg. 23
	// The max AU he can apply for in a semester
	int maxAU;
	// List of Courses - Courses the Student applied for this semester
	// May need to change to all semesters
	List<CoursePlan> coursePlanList;
	// Access Period - The period Student can access the course application
	// Only uses the Time of the period, Day is not used
	Period accessPeriod;
	// Access Date - The access date of the course application
	Date accessDate;
	
	List<String> exemptedCourseList;
	// ADDITIONAL INFO NEEDED !!! Ignore first
	// Need pursuing degree, current studying year, etc.
	// for Admin to check what access period and date to give

	// overloaded constructor?
	public Student()
	{
		super();
		this.matricNo = "";
		this.nationality = "";
		this.maxAU = -1;
		this.gender = Gender.DEFAULT;;
		coursePlanList = new ArrayList<CoursePlan>();
		this.accessPeriod = null;
		this.accessDate = null;
		exemptedCourseList = new ArrayList<String>();
	}
	
	
	public Student(String name, String userName,
			String password, AccountType type,
			String matricNo, Gender gender,
			String nationality, int maxAU,
			Period accessPeriod, Date accessDate)
	{
		super(name, userName, password, type);
		this.matricNo = matricNo;
		this.nationality = nationality;
		this.maxAU = maxAU;
		this.gender = gender;
		this.accessPeriod = accessPeriod;
		this.accessDate = accessDate;
		coursePlanList = new ArrayList<CoursePlan>();
		exemptedCourseList = new ArrayList<String>();
	}

	public String getMatricNo()
	{
		return this.matricNo;
	}

	public void setMatricNo(String matricNo)
	{
		this.matricNo = matricNo;
	}

	public Gender getGender()
	{
		return this.gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public String getNationality()
	{
		return this.nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public int getMaxAU()
	{
		return this.maxAU;
	}

	public void setMaxAU(int maxAU)
	{
		this.maxAU = maxAU;
	}

	public List<CoursePlan> getCourses()
	{
		return this.coursePlanList;
	}

	public void setCourses(List<CoursePlan> coursePlan)
	{
		this.coursePlanList = coursePlan;
	}

	public Period getAccessPeriod()
	{
		return this.accessPeriod;
	}

	public void setAccessPeriod(Period accessPeriod)
	{
		this.accessPeriod = accessPeriod;
	}

	public Date getAccessDate()
	{
		return this.accessDate;
	}

	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}
	
	public List<String> getExemptedCourseList()
	{
		return this.exemptedCourseList;
	}

	public void setExemptedCourseList(List<String> exemptedCourseList)
	{
		this.exemptedCourseList = exemptedCourseList;
	}
	
	public String toString()
	{
		List<Integer> strList = new ArrayList<Integer>();
		for(int i = 0; i < coursePlanList.size(); i++) 
		{
			strList.add(coursePlanList.get(i).getIndex());
		}
		
		

		return super.getName() + "|" + super.getUserName() + "|" + super.getType()
		+ "|" +  matricNo  + "|" + nationality  + "|" +  maxAU + "|" + gender 
		+ "|" + strList  + "|" + exemptedCourseList + "|" + accessPeriod.toTimeString()
		+ "|" + accessDate + "|" + super.getPassword();
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
	public Student readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		super.setName(star.nextToken().trim());
		super.setUserName(star.nextToken().trim());
		super.setType(AccountType.valueOf(star.nextToken().trim()));
		this.matricNo = star.nextToken().trim();
		this.nationality = star.nextToken().trim();
		this.maxAU = Integer.parseInt(star.nextToken().trim());
		this.gender = Gender.valueOf(star.nextToken().trim());

		
		String str  = star.nextToken().trim();
        str = str.substring(1, str.length() - 1); //remove the first and last char, which are the [ ]
        
        if(!str.isBlank()) {
        	List<String> coursePlanIndex = Arrays.asList(str.split(",",-1)); // store as [1,2,3,...]
    		
    		for(int i = 0;i < coursePlanIndex.size(); i++) 
    		{
    			for (int k=0;k < Container.coursePlanList.size();k++)
    			{
    				if(Integer.parseInt(coursePlanIndex.get(i).trim()) == Container.coursePlanList.get(k).getIndex()) {
    					coursePlanList.add(Container.coursePlanList.get(k));
    				}
    			}
    		}
        }

		String exemptedCourseID  = star.nextToken().trim();
		exemptedCourseID = exemptedCourseID.substring(1, exemptedCourseID.length() - 1); //remove the first and last char, which are the [ ]
        
        if(!exemptedCourseID.isBlank()) {
        	List<String> exemptedCourseIDList = Arrays.asList(exemptedCourseID.split(",",-1)); // store as [1,2,3,...]
    		
    		for(int i = 0;i < exemptedCourseIDList.size(); i++) 
    		{
    			this.exemptedCourseList.add(exemptedCourseIDList.get(i).trim());
    		}
        }

		String[] apdata  = star.nextToken().trim().split(",");
		String[] stdata = apdata[0].split(":");
		String[] etdata = apdata[1].split(":");
		Time[] tdata = {null,null};
		tdata[0] = new Time(Integer.parseInt(stdata[0]),Integer.parseInt(stdata[1]));
		tdata[1] = new Time(Integer.parseInt(etdata[0]),Integer.parseInt(etdata[1]));
		Day day = Day.DEFAULT;
		this.accessPeriod = new Period(tdata[0], tdata[1], day);

		String[] addata  = star.nextToken().trim().split(",");
		this.accessDate = new Date(Integer.parseInt(addata[0]),Integer.parseInt(addata[1]),Integer.parseInt(addata[2]));
        
		super.setPassword(star.nextToken().trim());
		
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
