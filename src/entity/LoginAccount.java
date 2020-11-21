package entity;

import entity.AllEnums.*;

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
	
	public LoginAccount()
	{
		this.name = "";
		this.userName =  "";
		this.password =  "";
		this.type = AccountType.DEFAULT;
	}
	
	
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

}
