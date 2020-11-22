package entity;

import entity.AllEnums.*;

/**
 * The base class for any login accounts
 */
public class LoginAccount
{
	/**
	 * The name of the user who owns the account.
	 * E.g. "Phua Chu Kang"
	 */
	private String name;
	/**
	 * The user name for the account
	 * E.g. "PCK123"
	 */
	private String userName;
	/**
	 * The password for the account.
	 * Will be stored with the password's hash value
	 */
	private String password;
	/**
	 * The type of account.
	 * E.g. "ADMIN"
	 */
	private AccountType type;

	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public LoginAccount()
	{
		this.name = "";
		this.userName =  "";
		this.password =  "";
		this.type = AccountType.DEFAULT;
	}
	
	/**
	 * Class constructor that specifies the course objects to create.
	 * @param name The name of the user who owns the account.
	 * @param userName The user name for the account
	 * @param password The password for the account.
	 * @param type The type of account.
	 */
	public LoginAccount(String name, String userName,
			String password, AccountType type)
	{
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.type = type;
	}

	/**
	 * Gets the name of the user who owns the account.
	 * @return The user's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name for the user.
	 * @param name The name of the user who owns the account.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the username of the account.
	 * @return The account's username.
	 */
	public String getUserName()
	{
		return userName;
	}
	
	/**
	 * Sets the username for the account.
	 * @param userName The username of the account.
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * Gets the password for the account.
	 * @return The account's password.
	 */
	public String getPassword()
	{
		return password;
	}

	
	/**
	 * Sets the password for the login account
	 * @param password The password for the account.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Gets the type of account.
	 * @return The account's type.
	 */
	public AccountType getType()
	{
		return type;
	}

	
	/**
	 * Sets the type of account.
	 * @param type The type of account.
	 */
	public void setType(AccountType type)
	{
		this.type = type;
	}

}
