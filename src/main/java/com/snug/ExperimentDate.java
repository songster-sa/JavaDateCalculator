package com.snug;

public class ExperimentDate {

	private int day;

	private int month;

	private int year;

	public ExperimentDate(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public ExperimentDate(String day, String month, String year) {
		this.day = Integer.parseInt(day);
		this.month = Integer.parseInt(month);
		this.year = Integer.parseInt(year);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
