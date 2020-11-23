package entity;

/**
 * To store date values in YEAR:MONTH:DAY format
 */
public class Date {
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
	public Date() {
		this.year = -1;
		this.month = -1;
		this.day = -1;
	}

	/**
	 * Class constructor that specifies the objects to create.
	 * 
	 * @param year  The year of the date.
	 * @param month The month of the date.
	 * @param day   The day of the date.
	 */
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Gets the date in an array format In the order of YEAR:MONTH:DAY
	 * 
	 * @return The date in integer array format.
	 */
	public int[] getDate() {
		return new int[] { this.year, this.month, this.day };
	}

	// sets the date using an array
	// in the order HR:MIN:SEC
	public void setDate(int[] date) {
		date[0] = this.year;
		date[1] = this.month;
		date[2] = this.day;
	}

	// Compare the given Date to this Date
	// Returns -1 if this Date is earlier than given Date
	// Returns 1 if this Date is later than given Date
	// Returns 0 if this Date is the same as given Date
	public int compareWith(Date date) {
		if (this.year < date.getDate()[0])
			return -1;
		else if (this.year > date.getDate()[0])
			return 1;
		else {
			if (this.month < date.getDate()[1])
				return -1;
			else if (this.month > date.getDate()[1])
				return 1;
			else {
				if (this.day < date.getDate()[2])
					return -1;
				else if (this.day > date.getDate()[2])
					return 1;
				else
					return 0;
			}
		}
	}

	/**
	 * Compares integer inputs to this date.
	 * 
	 * @param year  year to compare with in int.
	 * @param month month to compare with in int.
	 * @param day   day to compare with in int.
	 * @return true if same date, false if different.
	 */
	public boolean sameDate(int year, int month, int day) {
		return this.year == year && this.month == month && this.day == day;
	}

	
	/**
	 * Gets all information on the date in String format. 
	 */
	@Override
	public String toString() {
		return year + "," + month + "," + day;
	}
}
