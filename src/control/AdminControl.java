package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.Student;
import test_cases.CreateStudentAccount;
import entity.AllEnums.AccountType;
import entity.AllEnums.Gender;

public class AdminControl {
	
	public void setStudentAccessPeriod(int matricNo) {
		
	}
	
	public static void createStudAcc(String name, String userName,
			String password, AccountType type,
			String matricNo, Gender gender,
			String nationality, int maxAU) // please help to add gender etc
	{
    	CreateStudentAccount studDB = new CreateStudentAccount();
    	String filename = "StudentAccount.txt" ;

		try {
			// read file containing Student records and store into a list
			ArrayList studList = studDB.readStudents(filename);
			
			//Displaying the current data in the StudentAccount.txt
			/*
			 * for (int i = 0 ; i < studList.size() ; i++) { Student student =
			 * (Student)studList.get(i); System.out.println("Matric No " +
			 * student.getMatricNo()); System.out.println("Name " + student.getName());
			 * System.out.println("Gender " + student.getGender());
			 * System.out.println("Nationality " + student.getNationality());
			 * System.out.println("Max AU " + student.getMaxAU()); }
			 */
			
			
			//create student obj to add into the studList
			Student addStud = new Student(name, userName,
					password, type,
					matricNo, gender,
					nationality, maxAU);
			
			studList.add(addStud);
			
			// resave the whole studList to the txt file
			studDB.saveStudent(filename, studList);
			
		}
		catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
		
	}
}
