package com.transmitapp.kira.domain;

public class BusInfo {
	
	
	public String id = "";
	public String stop = "";
	public String delayReason = "";
	public int arrivingInMinutes = 0;
	public String stopName = null;
	
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStop() {
		return stop;
	}
	public void setStop(String stop) {
		this.stop = stop;
	}
	public String getDelayReason() {
		return delayReason;
	}
	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}
	public int getArrivingInMinutes() {
		return arrivingInMinutes;
	}
	public void setArrivingInMinutes(int arrivingInMinutes) {
		this.arrivingInMinutes = arrivingInMinutes;
	}

}
