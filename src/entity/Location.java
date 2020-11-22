package entity;

/**
 * Details of a particular location
 */
public class Location
{
	/**
	 * The name of the location.
	 * E.g. "HWLAB2"
	 */
	private String name;
	/**
	 * The extended name of the location.
	 * E.g. "Hardware Lab 2"
	 */
	private String nameExtended;
	/**
	 * The address of the location.
	 * E.g. "N4-01B-05"
	 */
	private String address;
	
	/**
	 * Class constructor that specifies the default values for each variable.
	 */
	public Location()
	{
		this.name = "null";
		this.nameExtended = "null";
		this.address = "null";
	}
	
	/**
	 * Class constructor that specifies the course objects to create.
	 * @param name The name of the location.
	 * @param nameExtended The extended name of the location.
	 * @param address The address of the location.
	 */
	public Location(String name, String nameExtended, String address)
	{
		this.name = name;
		this.nameExtended = nameExtended;
		this.address = address;
	}
	
	/**
	 * Gets all information on the location in String format.
	 */
	@Override
	public String toString()
	{
		return name + "~" + nameExtended + "~" + address;
	}
}
