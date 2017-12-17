package com.snug;

import org.junit.Assert;
import org.junit.Test;

public class DateCalculatorTest {

	@Test(expected = Exception.class)
	public void invalidInputsByYear() throws Exception {
		DateCalculator.calculateDuration("01/12/2010", "01/12/2001");
	}

	@Test(expected = Exception.class)
	public void invalidInputsByMonth() throws Exception {
		DateCalculator.calculateDuration("01/12/2010", "01/11/2010");
	}

	@Test(expected = Exception.class)
	public void invalidInputsByDay() throws Exception {
		DateCalculator.calculateDuration("31/12/2010", "01/12/2010");
	}

	@Test(expected = Exception.class)
	public void invalidInputFormat() throws Exception {
		DateCalculator.calculateDuration("31/Dec/2010", "01/12/2010");
	}

	@Test(expected = Exception.class)
	public void invalidInputYear() throws Exception {
		DateCalculator.calculateDuration("01/12/1800", "01/12/2001");
	}

	@Test(expected = Exception.class)
	public void invalidInputMonth() throws Exception {
		DateCalculator.calculateDuration("01/34/2000", "01/12/2001");
	}

	@Test(expected = Exception.class)
	public void invalidInputDay() throws Exception {
		DateCalculator.calculateDuration("34/12/2000", "01/12/2001");
	}

	@Test(expected = Exception.class)
	public void invalidInputFeb() throws Exception {
		DateCalculator.calculateDuration("01/02/2000", "29/02/2001");
	}

	@Test(expected = Exception.class)
	public void invalidInputNegative() throws Exception {
		DateCalculator.calculateDuration("-11/02/2000", "29/02/2001");
	}

	@Test
	public void givenTestCases() throws Exception {

		int days = DateCalculator.calculateDuration("07/11/1972", "08/11/1972");
		Assert.assertEquals(0, days);

		days = DateCalculator.calculateDuration("01/01/2000", "03/01/2000");
		Assert.assertEquals(1, days);

		days = DateCalculator.calculateDuration("02/06/1983", "22/06/1983");
		Assert.assertEquals(19, days);

		days = DateCalculator.calculateDuration("04/07/1984", "25/12/1984");
		Assert.assertEquals(173, days);

		days = DateCalculator.calculateDuration("03/08/1983", "03/01/1989");
		Assert.assertEquals(1979, days);

		days = DateCalculator.calculateDuration("03/01/1983", "03/08/1989");
		Assert.assertEquals(2403, days);

	}

	@Test
	public void moreTestCases() throws Exception {
		// test cases for code coverage
		int days = DateCalculator.calculateDuration("01/02/2004", "03/03/2004");
		Assert.assertEquals(30, days);

		days = DateCalculator.calculateDuration("01/02/2001", "03/03/2001");
		Assert.assertEquals(29, days);

		days = DateCalculator.calculateDuration("01/04/2000", "03/01/2001");
		Assert.assertEquals(276, days);

		days = DateCalculator.calculateDuration("01/04/2000", "03/04/2001");
		Assert.assertEquals(366, days);
	}

}
