package entity;

import entity.AllEnums.AccountType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Base class for any login accounts
public class LoginAccount
{
	// Name - Eg. "Phua Chu Kang"
	private String name;
	// User Name - Eg. "PCK123"
	private String userName;
	// Password - Eg. "hfWFIOAFohiwfhaWAWOH902HWJ0912WADdawaW"
	// Store password's hash value
	private String password;
	// Type - Eg. AccountType.ADMIN
	private AccountType type;
	
	public LoginAccount(String name, String userName,
			String password, AccountType type)
	{
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public AccountType getType()
	{
		return type;
	}

	public void setType(AccountType type)
	{
		this.type = type;
	}
	
	// Read info from the file and write it to the screen
	// To move to a controller class handling the login
	public static boolean getFileInfo(String userName, String passwordHashed)
	{

		boolean correctInfo = false;

		System.out.println("Loading....");

		// Open a new connection to the file

		// File listOfNames = new File("User.txt");
		File listOfNames = new File("StudentAccount.txt");

		try
		{

			// FileReader reads character files
			// BufferedReader reads as many characters as possible

			BufferedReader getInfo = new BufferedReader(new FileReader(listOfNames));

			// Reads a whole line from the file and saves it in a String

			String userInfo = getInfo.readLine();

			// readLine returns null when the end of the file is reached

			while (userInfo != null)
			{

				// Break lines into pieces

				String[] indivUserData = userInfo.split("\\|"); // <-- Java | have issue

				// .equal() in java check only values while == check memory location
				if (userName.equals(indivUserData[0]) && passwordHashed.equals(indivUserData[5])) // indivUserData[4]
																									// rmb to change to
																									// different
																									// posistion
				{
					correctInfo = true;
					// break while loop once username and password found
					break;
				}

				userInfo = getInfo.readLine();

			}

		}

		// Can be thrown by FileReader

		catch (FileNotFoundException e)
		{

			System.out.println("Couldn't Find the File");
			System.exit(0);
		}

		catch (IOException e)
		{

			System.out.println("An I/O Error Occurred");
			System.exit(0);

		}

		return correctInfo;

	}

}
