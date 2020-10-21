package entity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginAccount {

	private String userName, password;
	
	public LoginAccount(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
		
	}
	
	// Read info from the file and write it to the screen
	// To move to a controller class handling the login
	public static boolean getFileInfo(String userName,String passwordHashed) {

		boolean correctInfo = false;
		
		System.out.println("Loading....");

		// Open a new connection to the file

		//File listOfNames = new File("User.txt");
		File listOfNames = new File("StudentAccount.txt");

		try {

			// FileReader reads character files
			// BufferedReader reads as many characters as possible

			BufferedReader getInfo = new BufferedReader(new FileReader(listOfNames));

			// Reads a whole line from the file and saves it in a String

			String userInfo = getInfo.readLine();

			// readLine returns null when the end of the file is reached

			while (userInfo != null) {

				

				// Break lines into pieces

				String[] indivUserData = userInfo.split("\\|"); // <-- Java | have issue
				

				// .equal() in java check only values while == check memory location
				if(userName.equals(indivUserData[0]) && passwordHashed.equals(indivUserData[5])) // indivUserData[4] rmb to change to different posistion
				{
					correctInfo = true;
					// break while loop once username and password found
					break;
				}

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
		
		return correctInfo;

	}
	
}
