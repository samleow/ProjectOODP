package entity;

public class Location {
	private String name;
	private String nameExtended;
	private String address;
	private Coordinate coord;
	
	
	//To preload the data to create the text file, can be removed later on
	public Location(String name, String nameExtended, String address, Coordinate coord) {
		this.name = name;
		this.nameExtended = nameExtended;
		this.address = address;
		this.coord = coord;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameExtended() {
		return this.nameExtended;
	}
	
	public void setNameExtended(String nameExtended) {
		this.nameExtended = nameExtended;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Coordinate getCoord() {
		return this.coord;
	}
	
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}
}
