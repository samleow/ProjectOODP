package entity;

/**
 *	To store date values in YEAR:MONTH:DAY format
 */
public class Date
{
	/**
	 * The year of the date.
	 */
	private int year;
	/**
	 * The month of the date.
	 */
	private int month;
	/**
	 * The day of the date.
	 */
	private int day;
	
	
	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public Date()
	{
		this.year = -1;
		this.month = -1;
		this.day = -1;
	}
	
	/**
	 * Class constructor that specifies the objects to create.
	 * @param year The year of the date.
	 * @param month The month of the date.
	 * @param day The day of the date.
	 */
	public Date(int year, int month, int day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Gets the date in an array format
	 * In the order of YEAR:MONTH:DAY
	 * @return The date in integer array format.
	 */
	public int[] getDate()
	{
		return new int[] { this.year, this.month, this.day };
	}
	
	/**
	 * Gets the date in String format.
	 */
	@Override
	public String toString()
	{
		return year + "," + month + "," + day;
	}
}
