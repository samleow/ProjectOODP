package test_cases;
import java.io.*;

import control.HashingPassword;

public class PreLoadedAccount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		UserAccount[] listUser = getUser();

		PrintWriter createTxt = createFile("User.txt");

		for (int i = 0; i < listUser.length; i++) {
			createUser(listUser[i], createTxt);
		}

		// Closes the connection to the PrintWriter

		createTxt.close();
		
		getFileInfo();
		*/
	}
	
	public PreLoadedAccount()
	{
		UserAccount[] listUser = getUser();

		PrintWriter createTxt = createFile("User.txt");

		for (int i = 0; i < listUser.length; i++) {
			createUser(listUser[i], createTxt);
		}

		// Closes the connection to the PrintWriter

		createTxt.close();
		
		getFileInfo();
	}

	// class that defines all the fields for User Account

	private static class UserAccount {

		public String userName, password;

		// constructor that's called when a customer is made

		public UserAccount(String userName, String password) {

			this.userName = userName;
			this.password = password;

		}

	}

	// Creates an array of preload user account

	private static UserAccount[] getUser() {

		UserAccount[] userAcc = new UserAccount[3];

		userAcc[0] = new UserAccount("jo", HashingPassword.encryptThisString("123"));
		userAcc[1] = new UserAccount("lee", HashingPassword.encryptThisString("zxc"));
		userAcc[2] = new UserAccount("admin", HashingPassword.encryptThisString("admin"));

		return userAcc;

	}

	// Create the file and the PrintWriter that will write to the file

	private static PrintWriter createFile(String fileName) {

		try {

			// Creates a File object that allows you to work with files on the hardrive

			File listOfNames = new File(fileName);

			// FileWriter is used to write streams of characters to a file
			// BufferedWriter gathers a bunch of characters and then writes
			// them all at one time (Speeds up the Program)
			// PrintWriter is used to write characters to the console, file

			PrintWriter infoToWrite = new PrintWriter(new BufferedWriter(new FileWriter(listOfNames)));
			return infoToWrite;
		}

		// You have to catch this when you call FileWriter

		catch (IOException e) {

			System.out.println("An I/O Error Occurred");

			// Closes the program

			System.exit(0);

		}
		return null;

	}

	// Create a string with the customer info and write it to the file

	private static void createUser(UserAccount user, PrintWriter userOutput) {

		// Create the String that contains the user info
		// "|" mean for separating the username and password in the text file

		String userInfo = user.userName + "|" + user.password;

		// Writes the string to the file

		userOutput.println(userInfo);

	}

	// Read info from the file and write it to the screen

	private static void getFileInfo() {

		System.out.println("Info Written to File\n");

		// Open a new connection to the file

		File listOfNames = new File("User.txt");

		try {

			// FileReader reads character files
			// BufferedReader reads as many characters as possible

			BufferedReader getInfo = new BufferedReader(new FileReader(listOfNames));

			// Reads a whole line from the file and saves it in a String

			String userInfo = getInfo.readLine();

			// readLine returns null when the end of the file is reached

			while (userInfo != null) {

				//System.out.println(userInfo);

				// Break lines into pieces

				String[] indivUserData = userInfo.split("\\|"); // <-- Java | have issue

				System.out.print("Username " + indivUserData[0] + " and password is " + indivUserData[1] + "\n");

				userInfo = getInfo.readLine();

			}

		}

		// Can be thrown by FileReader

		catch (FileNotFoundException e) {

			System.out.println("Couldn't Find the File");
			System.exit(0);
		}

		catch (IOException e) {

			System.out.println("An I/O Error Occurred");
			System.exit(0);

		}

	}

}
