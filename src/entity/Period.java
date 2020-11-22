package entity;

import entity.AllEnums.Day;
/**
 * Contains the duration details including the starting time and ending time.
 */
public class Period
{
	// Start Time - The starting time of the period
	
	/**
	 * The starting time of a period.
	 */
	private Time startTime;
	/**
	 *  The ending time of a period.
	 */
	private Time endTime;	
	/**
	 * The day of the week of this period.
	 */
	private Day day;
		
	/**
	 * Class constructor that specifies the course objects to create.
	 * @param startTime The starting time of a period.
	 * @param endTime The ending time of a period.
	 * @param day The day of the week of this period.
	 */
	public Period(Time startTime, Time endTime, Day day)
	{
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
	}
	
	/**
	 * Class constructor for creating student account.
	 * @param startTime The starting time of a period.
	 * @param endTime The ending time of a period.
	 */
	public Period(Time startTime, Time endTime)
	{
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
	/**
	 * Checks if this period clashes with any other period.
	 * @param p The period that we want to check.
	 * @return Boolean value on whether it clashes.
	 */
	public boolean clashWith(Period p)
	{
		// days are different
		if(!this.day.equals(p.day))
			return false;
		
		return !( ( this.startTime.compareWith(p.startTime) < 0 && this.endTime.compareWith(p.startTime) <= 0 ) || 
				( this.startTime.compareWith(p.endTime) >= 0 && this.endTime.compareWith(p.endTime) > 0 ) );
	}
	
	/**
	 * Checks if time is within start time and end time.
	 * @param time The Time input value.
	 * @return Boolean value whether time input is within start and end time.
	 */
	public boolean withinPeriod(Time time)
    {
        if(time.compareWith(this.startTime) == -1
                || time.compareWith(this.endTime) == 1)
            return false;
        else return true;
    }
	
	/**
	 * Gets all information on the period in String format.
	 */
	@Override
	public String toString()
	{
		return startTime + "," + endTime + "," + day;
	}
	
	/**
	 * Gets all information on the time in String format.
	 */
	public String toTimeString()
	{
		return startTime + "," + endTime;
	}
}
