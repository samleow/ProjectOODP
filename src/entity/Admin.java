package entity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import entity.AllEnums.AccountType;
import entity.AllEnums.Day;
import entity.AllEnums.LessonType;
import entity.AllEnums.WeekType;

// Admin account that inherits LoginAccount
public class Admin extends LoginAccount implements IOData
{
	
	private int adminID;
	
	// Admin Name - Eg. "Mr Tan"
	private String name;
	
	// The Username for the Admin - Eg. "tan123"
	private String userName;

	// Type - Eg. AccountType.ADMIN
	private AccountType type;
	
	// Password - Eg. "hfWFIOAFohiwfhaWAWOH902HWJ0912WADdawaW"
	// Store password's hash value
	private String password;
	
	public Admin() 
	{
		super();
		
	}
	
	
	public Admin(String name, String userName,
			String password, AccountType type)
	{
		super(name, userName, password, type);
		
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.type = type;
		
	}


	public int getAdminID() {
		return adminID;
	}


	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public AccountType getType() {
		return type;
	}


	public void setType(AccountType type) {
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return adminID + "|" + name + "|" + userName + "|" + type + "|"
				+ password ;
	}
	
	// this may be for Add new Student
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
			System.out.println("File for overwriting Lesson data not found!");
			return false;
		}
	}

	@Override
	public Admin readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		this.adminID = Integer.parseInt(star.nextToken().trim());
		this.name = star.nextToken().trim();
		this.userName = star.nextToken().trim();
		this.type = AccountType.valueOf(star.nextToken().trim());
		this.password = star.nextToken().trim();
		
		
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
