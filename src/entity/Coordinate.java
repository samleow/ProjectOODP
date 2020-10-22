package entity;

import entity.AllEnums.Gender;

public class Coordinate {
	private double latitude;
	private double longtitude;
	
	
	//To preload the data to create the text file, can be removed later on
	public Coordinate(double lat, double lon) {
		this.latitude = lat;
		this.longtitude = lon;
	}
	
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(double longtitude) {
		this.latitude = longtitude;
	}
	
	public double getLongtitude() {
		return this.longtitude;
	}
	
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
}
