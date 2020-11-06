package entity;

// Class to store time values in 24HR format
public class Time
{
	private int hour;
	private int minute;
	
	public Time(int hour, int minute)
	{
		this.hour = hour;
		this.minute = minute;
	}
	
	// returns the time in an array
	// in the order HR:MIN:SEC
	public int[] getTime()
	{
		return new int[] { this.hour, this.minute};
	}
	
	// sets the time using an array
	// in the order HR:MIN:SEC
	public void setTime(int[] time)
	{
		time[0] = this.hour;
		time[1] = this.minute;
	}
	
	// Compare the given Time to this Time
	// Returns -1 if this Time is earlier than given Time
	// Returns 1 if this Time is later than given Time
	// Returns 0 if this Time is the same as given Time
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
	
	@Override
	public String toString()
	{
		return hour + ":" + minute;
	}

}
