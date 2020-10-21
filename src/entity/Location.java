package entity;

public class Location {
	private String name;
	private String nameExtended;
	private String address;
	private Coordinate coord;
	
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
