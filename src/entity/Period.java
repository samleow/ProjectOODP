package entity;

import entity.AllEnums.Day;

// Contains duration details, 
// from a starting time to an ending time of a day
public class Period
{
	// Start Time - The starting time of the period
	private Time startTime;
	// End Time - The ending time of the period
	private Time endTime;
	// Day - The day of this period
	private Day day;
	
	// For Adding and updating Course
	public Period(Time startTime, Time endTime, Day day)
	{
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
	}
	
	// For creating of student account
	public Period(Time startTime, Time endTime)
	{
		this.startTime = startTime;
		this.endTime = endTime;
		//this.day = day;
	}
	
	// Time given is within this period
	public boolean withinPeriod(Time time)
	{
		if(time.compareWith(this.startTime) == -1
				|| time.compareWith(this.endTime) == 1)
			return false;
		else return true;
	}
	
	public boolean clashWith(Period p)
	{
		// days are different
		if(!this.day.equals(p.day))
			return false;
		
		return !( ( this.startTime.compareWith(p.startTime) < 0 && this.endTime.compareWith(p.startTime) <= 0 ) || 
				( this.startTime.compareWith(p.endTime) >= 0 && this.endTime.compareWith(p.endTime) > 0 ) );
	}
	
	public Time getStartTime()
	{
		return this.startTime;
	}

	public void setStartTime(Time startTime)
	{
		this.startTime = startTime;
	}

	public Time getEndTime()
	{
		return this.endTime;
	}

	public void setEndTime(Time endTime)
	{
		this.endTime = endTime;
	}

	public Day getDay()
	{
		return this.day;
	}

	public void setDay(Day day)
	{
		this.day = day;
	}
	
	@Override
	public String toString()
	{
		return startTime + "," + endTime + "," + day;
	}
	
	public String toTimeString()
	{
		return startTime + "," + endTime;
	}
}
