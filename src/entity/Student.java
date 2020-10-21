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
}
