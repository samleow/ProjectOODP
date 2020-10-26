package entity;

// Class to store the date values in YEAR:MONTH:DAY format
public class Date
{
	private int year;
	private int month;
	private int day;
	
	public Date(int year, int month, int day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	// returns the date in an array
	// in the order YEAR:MONTH:DAY
	public int[] getDate()
	{
		return new int[] { this.year, this.month, this.day };
	}
	
	// sets the date using an array
	// in the order HR:MIN:SEC
	public void setDate(int[] date)
	{
		date[0] = this.year;
		date[1] = this.month;
		date[2] = this.day;
	}
	
	// Compare the given Date to this Date
	// Returns -1 if this Date is earlier than given Date
	// Returns 1 if this Date is later than given Date
	// Returns 0 if this Date is the same as given Date
	public int compareWith(Date date)
	{
		if (this.year < date.getDate()[0])
			return -1;
		else if (this.year > date.getDate()[0])
			return 1;
		else
		{
			if (this.month < date.getDate()[1])
				return -1;
			else if (this.month > date.getDate()[1])
				return 1;
			else
			{
				if (this.day < date.getDate()[2])
					return -1;
				else if (this.day > date.getDate()[2])
					return 1;
				else
					return 0;
			}
		}
	}
}