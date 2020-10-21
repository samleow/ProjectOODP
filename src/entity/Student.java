package entity;

import java.util.List;

import entity.AllEnums.Gender;

public class Student {
	String matricNo;
	Gender gender;
	String nationality;
	int maxAU;
	List<Course> courses;
	Period accessPeriod;
	
	//added
	private String name;
	private String password;
	
	// need to add gender and other etc here
	public Student(String matricNo, String name, Gender gender, String nationality, int maxAU,  String password) {
		this.matricNo = matricNo;
		this.nationality = nationality;
		this.maxAU = maxAU;
		this.gender = gender;
		//this.courses = courses;
		this.name = name;
		this.password = password;
	}

	public String getMatricNo() {
		return this.matricNo;
	}
	
	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getNationality() {
		return this.nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public int getMaxAU() {
		return this.maxAU;
	}
	
	public void setMaxAU(int maxAU) {
		this.maxAU = maxAU;
	}
	
	public List<Course> getCourses() {
		return this.courses;
	}
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	public Period getAccessPeriod() {
		return this.accessPeriod;
	}
	
	public void setAccessPeriod(Period accessPeriod) {
		this.accessPeriod = accessPeriod;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
