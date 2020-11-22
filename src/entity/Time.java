package entity;

/**
 * Class to store time values in 24HR format.
 */
public class Time
{
	
	/**
	 * The hour values of time.
	 */
	private int hour;
	/**
	 * The minute values of time.
	 */
	private int minute;
	
	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public Time()
	{
		this.hour = -1;
		this.minute = -1;
	}
	
	/**
	 * Class constructor that specifies the course objects to create.
	 * @param hour The hour values of time.
	 * @param minute The minute values of time.
	 */
	public Time(int hour, int minute)
	{
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * Gets the time.
	 * @return The array of time in the order HR:MIN.
	 */
	public int[] getTime()
	{
		return new int[] { this.hour, this.minute};
	}
	
	/**
	 * Compare the given time to this time
	 * @param time 
	 * @return An integer value that represents the comparison of time.
	 */
	public int compareWith(Time time)
	{
		if (this.hour < time.getTime()[0])
			return -1;
		else if (this.hour > time.getTime()[0])
			return 1;
		else
		{
			if (this.minute < time.getTime()[1])
				return -1;
			else if (this.minute > time.getTime()[1])
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * Gets all information on this time in String format.
	 */
	@Override
	public String toString()
	{
		return hour + ":" + minute;
	}

}
