package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.Student;
import test_cases.CreateStudentAccount;

public class Admin {
	
	public void setStudentAccessPeriod(int matricNo) {
		
	}
	
	public static void createStudAcc(String matricNo, String name, String nationality, int maxAU,  String password) // please help to add gender etc
	{
    	CreateStudentAccount studDB = new CreateStudentAccount();
    	String filename = "StudentAccount.txt" ;
    	
		try {
			// read file containing Student records and store into a list
			ArrayList studList = CreateStudentAccount.readStudents(filename) ;
			
			/*
			for (int i = 0 ; i < studList.size() ; i++) {
					Student student = (Student)studList.get(i);
					
					System.out.println("Name " + student.getName() );
					System.out.println("Contact " + student.getContact() );
			}
			*/
			
			//create student obj to add into the studList
			Student addStud = new Student(matricNo, name, nationality, maxAU, password);
			
			studList.add(addStud);
			
			// resave the whole studList tp the txt file
			studDB.saveStudent(filename, studList);
			
		}
		catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
		
	}
}
