package entity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import entity.AllEnums.AccountType;

/**
 * Contains information for all admin accounts.
 * Inherits from parent class LoginAccount.
 */
public class Admin extends LoginAccount implements IOData<Admin>
{
	
	/**
	 * Class constructor.
	 * Inherits from parent class LoginAccount
	 */
	public Admin() 
	{
		super();
		
	}
	
	/**
	 * Class constructor that creates an admin and inherits the name, username, password and account type from parent account.
	 * @param name The name of this admin.
	 * @param userName The user name of this admin.
	 * @param password The password of this admin.
	 * @param type The account type of this user.
	 * 			   Account Type should be admin.
	 */
	public Admin(String name, String userName,
			String password, AccountType type)
	{
		super(name, userName, password, type);
		
	}

	/**
	 * Get all information about this admin in String format.
	 */
	@Override
	public String toString()
	{
		return super.getName() + "|" + super.getUserName() + "|" + super.getType() + "|"
		+ super.getPassword();
	}
	
	/**
	 * For writing/overwriting into the text file.
	 * @param fileName The name of the file to write to.
	 * @param overwrite To indicate whether to overwrite the file or to simply append at the bottom of the text file.
	 * @return Boolean value to confirm if writing is successful.
	 */
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
			System.out.println("File for overwriting Admin data not found!");
			return false;
		}
	}
	
	/**
	 * To read each line of data from the text file and sets the admin information accordingly.
	 * @param fileLine To indicate which line of code to read from.
	 * @param st To store fileLine.
	 * @param star To store individual 'fields' of the string separated by a separator.
	 * @return The admin's information.
	 */
	@Override
	public Admin readDataFile(String fileLine)
	{
		String st = fileLine;
		// get individual 'fields' of the string separated by SEPARATOR
		StringTokenizer star = new StringTokenizer(st , "|");	// pass in the string to the string tokenizer using delimiter ","

		super.setName(star.nextToken().trim());
		super.setUserName(star.nextToken().trim());
		super.setType(AccountType.valueOf(star.nextToken().trim()));
		super.setPassword(star.nextToken().trim()); 
		
		return this;
	}
}
