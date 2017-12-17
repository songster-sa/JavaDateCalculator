package com.snug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class with helper/util methods for date calculation
 * 
 */
public class DateCalculatorHelperService {

	private List<Integer> days31 = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
	private List<Integer> days30 = new ArrayList<Integer>(Arrays.asList(4, 6, 9, 11));

	private static final Logger LOG = Logger.getLogger(DateCalculatorHelperService.class.getName());

	/**
	 * Return ExperimentDate object with day, month and year set
	 * 
	 * @param String
	 *            date
	 * @param String
	 *            delimiter used in the date
	 */
	public ExperimentDate getExperimentDate(String date, String delimiter) throws Exception {
		String[] parts = date.split(delimiter);
		return new ExperimentDate(parts[0], parts[1], parts[2]);
	}

	/**
	 * Returns the total number of days within whole/full months included between given months
	 * 
	 * @param fromMonth
	 *            month to begin count
	 * @param toMonth
	 *            month to end count
	 * @param year
	 *            year of the date
	 * @param includefromMonth
	 *            true if fromMonth needs to be included in the count
	 * @param includeToMonth
	 *            true if toMonth needs to be included in the count
	 */
	public int getWholeMonthsInDays(int fromMonth, int toMonth, int year, boolean includefromMonth,
			boolean includeToMonth) {

		int toReturn = 0;

		int month31 = 0; // count of months with 31 days
		int month30 = 0; // count of months with 30 days
		int feb = 0; // count of febs - 28 days

		int i = fromMonth;
		int j = toMonth;

		if (!includefromMonth) {
			i++;
		}
		if (!includeToMonth) {
			j--;
		}

		for (; i <= j; i++) {
			if (days31.contains(i)) {
				month31++;
			} else if (days30.contains(i)) {
				month30++;
			} else if (i == 2) {
				feb++;
			}
		}

		LOG.info("Number of whole months with 31 days passed in start and end year: " + month31);
		toReturn = month31 * 31;

		LOG.info("Number of whole months with 30 days passed in start and end year: " + month30);
		toReturn = toReturn + (month30 * 30);

		LOG.info("Number of whole feburary passed in start and end year: " + feb);
		if (isLeapYear(year)) {
			toReturn = toReturn + (feb * 29);
		} else {
			toReturn = toReturn + (feb * 28);
		}

		return toReturn;
	}

	/**
	 * Returns total number of days left in a given month starting from the given day
	 */
	public int getDaysLeftInMonth(ExperimentDate date) {
		int toReturn = 0;

		if (days31.contains(date.getMonth())) {
			toReturn = 31 - date.getDay();
		} else if (days30.contains(date.getMonth())) {
			toReturn = 30 - date.getDay();
		} else if (date.getMonth() == 2) {

			if (isLeapYear(date.getYear())) {
				toReturn = 29 - date.getDay();
			} else {
				toReturn = 28 - date.getDay();
			}
		}
		LOG.info("Number of whole days passed in start month: " + toReturn);
		return toReturn;
	}

	/**
	 * Returns true if year is a leap year, false otherwise
	 */
	public boolean isLeapYear(int year) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		}
		return false;
	}

	/**
	 * Validates if the given date (as string) is in the format dd/MM/yyyy If not, throws exception
	 */
	public void validateInputFormat(String fromdate, String todate, String dateformat) throws Exception {
		// validate format
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		if (format.parse(fromdate) == null || format.parse(todate) == null) {
			throw new Exception("Date is not in expected format dd/mm/yyyy");
		}
	}

	/**
	 * Check if day, month, year are valid. If not, throws Exception
	 */
	public void validateInputDates(ExperimentDate date, String prefix) throws Exception {

		// validate year
		if (date.getYear() < 1901 || date.getYear() > 2999) {
			throw new Exception(prefix + " date year is not supported");
		}

		// validate month
		if (date.getMonth() > 12 || date.getMonth() < 1) {
			throw new Exception(prefix + " date month is invalid");
		}

		// validate date
		if (date.getDay() < 1) {
			throw new Exception(prefix + " date day is invalid");
		}

		if (days31.contains(date.getMonth()) && date.getDay() > 31) {
			throw new Exception(prefix + " date day is invalid");

		} else if (days30.contains(date.getMonth()) && date.getDay() > 30) {
			throw new Exception(prefix + " date day is invalid");

		} else if (date.getMonth() == 2) {

			if (isLeapYear(date.getYear()) && date.getDay() > 29) {
				throw new Exception(prefix + " date day is invalid");

			} else if (date.getDay() > 28) {
				throw new Exception(prefix + " date day is invalid");
			}
		}

	}
}
