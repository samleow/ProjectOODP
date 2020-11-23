package control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import entity.LoginAccount;

/**
 * Control class to handle Login.
 */
public class AccountControl {
	
	/**
	 * Private constructor of AccountControl.
	 */
	private AccountControl() {
	}
	
	/**
	 * Returns an encrypted string based on given string.
	 * 
	 * @param input The string to encrypt.
	 * @return Returns the encrypted input string.
	 */
	public static String encryptThisString(String input) {
		try {
			// getInstance() method is called with algorithm SHA-512
			MessageDigest md = MessageDigest.getInstance("SHA-512");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			// return the HashText
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Checks if login credentials are valid.
	 * 
	 * @param userName The username of the login account.
	 * @param passwordHashed The encrypted password of the login account.
	 * @param isAdmin Boolean value of whether the account type is an Admin account.
	 * @return Returns true if login is successful.
	 */
	public static boolean accountLoginSuccess(String userName, String passwordHashed, boolean isAdmin) {
		System.out.println("Loading...");
		
		LoginAccount accnt = null;
		if (isAdmin)
			accnt = Container.getAdminByUsername(userName);
		else
			accnt = Container.getStudentByUsername(userName);

		if (accnt == null)
			return false;

		return accnt.getPassword().equals(passwordHashed);
	}

}
