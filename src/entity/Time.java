package entity;

public class Time {
	private int hour;
	private int minute;
	private int second;
	
	// returns int array
	// NOT SURE if correct
	public int[] getTime() {
		return new int[] {this.hour,this.minute,this.second};
	}
	
	public void setTime(int[] time) {
		time[0] = this.hour;
		time[1] = this.minute;
		time[2] = this.second;
	}
	
//	public int getHour() {
//		return this.hour;
//	}
//	
//	public void setHour(int hour) {
//		this.hour = hour;
//	}
//	
//	public int getMinute() {
//		return this.minute;
//	}
//	
//	public void setMinute(int hour) {
//		this.minute = minute;
//	}
//	
//	public int getSecond() {
//		return this.second;
//	}
//	
//	public void setSecond(int hour) {
//		this.second = second;
//	}
}
