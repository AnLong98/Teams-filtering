package tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

import utilities.DataUtilities;

public class DataUtilitiesTests {
	
	@Test
	public void roundSeconds_shouldAddSecond_returnPlusOneSecond()
	{
		LocalTime expectedValue = LocalTime.parse("00:00:01");
		LocalTime timeToRound = LocalTime.parse("00:00:00.5");
		
		LocalTime returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(expectedValue.getSecond(), returnValue.getSecond());
	}
	
	@Test
	public void roundSeconds_shouldNotAddSecond_returnSameNumberOfSeconds()
	{	
		LocalTime expectedValue = LocalTime.parse("00:00:01");
		LocalTime timeToRound = LocalTime.parse("00:00:01.4");
		
		LocalTime returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(expectedValue.getSecond(), returnValue.getSecond());
	}
	
	@Test
	public void roundSeconds_shouldAddSecond_returnZeroNanos()
	{
		LocalTime timeToRound = LocalTime.parse("00:00:00.5");
		
		LocalTime returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void roundSeconds_shouldNotAddSecond_returnZeroNanos()
	{
		LocalTime timeToRound = LocalTime.parse("00:00:01.4");
		
		LocalTime returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void formatCSVOutputTime_singleDigitValues__assertContent()
	{
		LocalTime timeToRound = LocalTime.parse("03:05:09");
		
		String returnValue = DataUtilities.formatCSVOutputTime(timeToRound);
		String expectedValue = "03:05:09";
		
		assertEquals(expectedValue, returnValue);
	}

}
