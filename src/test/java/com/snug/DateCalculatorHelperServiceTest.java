package com.snug;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class DateCalculatorHelperServiceTest {

	private DateCalculatorHelperService service = new DateCalculatorHelperService();

	@Test
	public void getExperimentDateValidTest() throws Exception {
		ExperimentDate date = service.getExperimentDate("23/12/2323", "/");
		Assert.assertEquals(23, date.getDay());
		Assert.assertEquals(12, date.getMonth());
		Assert.assertEquals(2323, date.getYear());

		date = service.getExperimentDate("12-10-1212", "-");
		Assert.assertEquals(12, date.getDay());
		Assert.assertEquals(10, date.getMonth());
		Assert.assertEquals(1212, date.getYear());
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getExperimentDateInvalidDelimiter() throws Exception {
		service.getExperimentDate("23/12/2323", "-");
	}

	@Test(expected = NumberFormatException.class)
	public void getExperimentDateInvalidDate() throws Exception {
		service.getExperimentDate("23/12/abcd", "/");
	}

	@Test
	public void getExperimentDateInvalidTest() {

		int days = service.getWholeMonthsInDays(1, 12, 2020, true, true);
		Assert.assertEquals(366, days);

		days = service.getWholeMonthsInDays(1, 12, 2020, true, false);
		Assert.assertEquals(335, days);

		days = service.getWholeMonthsInDays(2, 12, 2020, false, true);
		Assert.assertEquals(306, days);

		days = service.getWholeMonthsInDays(1, 12, 2021, true, true);
		Assert.assertEquals(365, days);

		days = service.getWholeMonthsInDays(12, 12, 2021, false, false);
		Assert.assertEquals(0, days);

		days = service.getWholeMonthsInDays(12, 12, 2021, true, true);
		Assert.assertEquals(31, days);

	}

	@Test
	public void getDaysLeftInMonthTest() {

		ExperimentDate date = new ExperimentDate(12, 12, 2020);

		int days = service.getDaysLeftInMonth(date);
		Assert.assertEquals(19, days);

		date = new ExperimentDate(12, 02, 2020);
		days = service.getDaysLeftInMonth(date);
		Assert.assertEquals(17, days);

		date = new ExperimentDate(12, 31, 2020);
		days = service.getDaysLeftInMonth(date);
		Assert.assertEquals(0, days);

		date = new ExperimentDate(31, 12, 2020);
		days = service.getDaysLeftInMonth(date);
		Assert.assertEquals(0, days);
	}

	@Test
	public void isLeapYearTest() {
		Assert.assertTrue(service.isLeapYear(2020));
		Assert.assertFalse(service.isLeapYear(2021));
		Assert.assertTrue(service.isLeapYear(-2020));
	}

	@Test
	public void validateInputFormatTest() throws Exception {
		service.validateInputFormat("12/12/1212", "12/12/2323", "dd/MM/yyyy");
		service.validateInputFormat("12-12-1212", "12-12-2323", "dd-MM-yyyy");
	}

	@Test
	public void validateInputFormatNegativeTest() throws Exception {
		service.validateInputFormat("12/12/1212", "12/-12/-2323", "dd/MM/yyyy");
	}

	@Test(expected = ParseException.class)
	public void validateInputFormatDifferentTest() throws Exception {
		service.validateInputFormat("12/12/1212", "12-12-2323", "dd/MM/yyyy");
	}
	
	@Test
	public void validateInputFormatNegativeTest2() throws Exception {
		service.validateInputFormat("12--12--1212", "12-12-2323", "dd-MM-yyyy");
	}
	
	@Test(expected = ParseException.class)
	public void validateInputFormatNegativeTest3() throws Exception {
		service.validateInputFormat("12-/12-/1212", "12-12-2323", "dd-MM-yyyy");
	}
	
	@Test
	public void validateInputDatesTest() throws Exception{
		ExperimentDate date = new ExperimentDate(12, 12, 2020);
		service.validateInputDates(date, "");
	}
	
	@Test(expected = Exception.class)
	public void validateInputDatesYearTest() throws Exception{
		ExperimentDate date = new ExperimentDate(12, 12, 1900);
		service.validateInputDates(date, "");
	}
	
	@Test(expected = Exception.class)
	public void validateInputDatesYearUpperLimitTest() throws Exception{
		ExperimentDate date = new ExperimentDate(12, 12, 3000);
		service.validateInputDates(date, "");
	}
	
	@Test(expected = Exception.class)
	public void validateInputDatesMonthTest() throws Exception{
		ExperimentDate date = new ExperimentDate(12, -1, 2020);
		service.validateInputDates(date, "");
	}
	
	@Test(expected = Exception.class)
	public void validateInputDatesMonthUpperLimitTest() throws Exception{
		ExperimentDate date = new ExperimentDate(12, 13, 2020);
		service.validateInputDates(date, "");
	}
	
	@Test(expected = Exception.class)
	public void validateInputDatesDayTest() throws Exception{
		ExperimentDate date = new ExperimentDate(0, 12, 2020);
		service.validateInputDates(date, "");
	}
	
	@Test(expected = Exception.class)
	public void validateInputDatesDayLeapYearTest() throws Exception{
		ExperimentDate date = new ExperimentDate(29, 02, 2021);
		service.validateInputDates(date, "");
	}
}
