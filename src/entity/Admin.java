package entity;

import entity.AllEnums.AccountType;

// Admin account that inherits LoginAccount
public class Admin extends LoginAccount
{
	public Admin(String name, String userName,
			String password, AccountType type)
	{
		super(name, userName, password, type);
	}

}
