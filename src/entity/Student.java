package entity;

import java.util.*;

import entity.AllEnums.AccountType;
import entity.AllEnums.Gender;

// Student account that inherits LoginAccount
public class Student extends LoginAccount
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
	List<Course> courses;
	// Access Period - The period Student can access the course application
	Period accessPeriod;
	// Access Date - The access date of the course application
	Date accessDate;
	
	// ADDITIONAL INFO NEEDED !!!
	// Need pursuing degree, current studying year, etc.
	// for Admin to check what access period and date to give

	// overloaded constructor?
	public Student(String name, String userName,
			String password, AccountType type,
			String matricNo, Gender gender,
			String nationality, int maxAU)
	{
		super(name, userName, password, type);
		this.matricNo = matricNo;
		this.nationality = nationality;
		this.maxAU = maxAU;
		this.gender = gender;
		courses = new ArrayList<Course>();
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

	public List<Course> getCourses()
	{
		return this.courses;
	}

	public void setCourses(List<Course> courses)
	{
		this.courses = courses;
	}

	public Period getAccessPeriod()
	{
		return this.accessPeriod;
	}

	public void setAccessPeriod(Period accessPeriod)
	{
		this.accessPeriod = accessPeriod;
	}
}
