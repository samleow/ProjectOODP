package entity;

// Location details
public class Location
{
	// Name - Eg. "HWLAB2"
	private String name;
	// Name Extended - Eg. "Hardware Lab 2"
	private String nameExtended;
	// Address - Eg. "N4-01B-05"
	private String address;

	// Default constructor for no location lesson
	public Location()
	{
		this.name = "null";
		this.nameExtended = "null";
		this.address = "null";
	}
	
	// To preload the data to create the text file, can be removed later on
	public Location(String name, String nameExtended, String address)
	{
		this.name = name;
		this.nameExtended = nameExtended;
		this.address = address;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNameExtended()
	{
		return this.nameExtended;
	}

	public void setNameExtended(String nameExtended)
	{
		this.nameExtended = nameExtended;
	}

	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}
	
	@Override
	public String toString()
	{
		return name + "~" + nameExtended + "~" + address;
	}
}
