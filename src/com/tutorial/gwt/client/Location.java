package com.tutorial.gwt.client;

public class Location {

	private double LAT;
	private double LONG;

	public double getLAT() {
		return LAT;
	}

	public void setLAT(double lAT) {
		LAT = lAT;
	}

	public double getLONG() {
		return LONG;
	}

	public void setLONG(double lONG) {
		LONG = lONG;
	}

	public Location(double lAT, double lONG) {
		super();
		LAT = lAT;
		LONG = lONG;
	}
}
