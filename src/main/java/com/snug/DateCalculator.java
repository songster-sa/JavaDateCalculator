package com.snug;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class with main method. reads system input and calculates the date difference in days
 * 
 */
public class DateCalculator {

	private static final Logger LOG = Logger.getLogger(DateCalculator.class.getName());

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter Experiment begin date as dd/mm/yyyy : ");
		String fromdate = scanner.nextLine();

		System.out.println("Enter Experiment end date as dd/mm/yyyy : ");
		String todate = scanner.nextLine();

		try {
			// Calculate duration
			int noOfDays = calculateDuration(fromdate, todate);
			System.out.println("-----------------------------------------------");
			System.out.println("Result : Duration of Experiment in days : " + noOfDays);

		} catch (Exception e) {
			System.out.println("Error : " + e.getLocalizedMessage());
			System.exit(0);
		}

	}

	public static int calculateDuration(String fromdate, String todate) throws Exception {

		int toReturn = 0;

		DateCalculatorHelperService service = new DateCalculatorHelperService();

		// validate input
		// this can be enhanced to support more formats
		service.validateInputFormat(fromdate, todate,"dd/MM/yyyy");
		
		ExperimentDate from = service.getExperimentDate(fromdate,"/");
		ExperimentDate to = service.getExperimentDate(todate,"/");

		// check if dates are valid
		service.validateInputDates(from, "Start");
		service.validateInputDates(to, "End");

		// ----------------------------------- calculate years---------------------

		int years = 0; // count of whole years passed
		int leapyears = 0;

		if (from.getYear() > to.getYear()) {
			// invalid input
			throw new Exception("Invalid input : start year is after end year");
		} else if (from.getYear() < to.getYear()) {
			years = to.getYear() - from.getYear() - 1;

			if (years > 0) {
				// atleast 1 whole year in between
				for (int i = from.getYear() + 1; i < to.getYear(); i++) {
					if (service.isLeapYear(i)) {
						leapyears++;
					}
				}
			}
		} // else equal years

		LOG.info("Number of whole years passed : " + years);
		toReturn = years * 365;
		LOG.info("Number of whole leap years passed : " + leapyears);
		toReturn = toReturn + leapyears; // add 1 day

		// ----------------------------------- calculate months in the first and last year---------------------

		if (from.getYear() == to.getYear()) {
			// years same - get difference of months

			if (from.getMonth() > to.getMonth()) {
				// invalid input
				throw new Exception("Invalid input : start month is after end month");
			} else if (to.getMonth() - from.getMonth() > 1) {
				// whole month(s) in between
				int daysForWholeMonths = service.getWholeMonthsInDays(from.getMonth(), to.getMonth(), from.getYear(), false, false);
				toReturn = toReturn + daysForWholeMonths;

			} // else same month or no whole month in between

		} else {
			// years different - whole years are already counted -

			// whole months for fromdate
			if (from.getMonth() != 12) {
				int daysForWholeMonths = service.getWholeMonthsInDays(from.getMonth(), 12, from.getYear(), false, true);
				toReturn = toReturn + daysForWholeMonths;
			}

			// whole months for todate
			if (to.getMonth() != 1) {
				int daysForWholeMonths = service.getWholeMonthsInDays(1, to.getMonth(), to.getYear(), true, false);
				toReturn = toReturn + daysForWholeMonths;
			}
		}

		// ---------------- calculate days in the first and last month

		if (from.getMonth() == to.getMonth() && from.getYear() == to.getYear()) {
			// same month
			if (from.getDay() > to.getDay()) {
				// invalid input
				throw new Exception("Invalid input : start day is after end day");
			} else if (to.getDay() - from.getDay() > 1) {
				int days = to.getDay() - from.getDay() - 1;
				LOG.info("Number of whole days passed in the start/end month : " + days);
				toReturn = toReturn + days;
			}
		} else {
			// different months - whole months already counted
			int days = service.getDaysLeftInMonth(from);
			toReturn = toReturn + days;

			toReturn = toReturn + (to.getDay() - 1); // days in the end month
		}

		return toReturn;
	}
}
