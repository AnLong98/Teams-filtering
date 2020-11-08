package tests;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.Test;
import utilities.TimeFormatter;

public class TimeFormatterTests {
	TimeFormatter formatter =  new TimeFormatter();
	
	@Test
	public void roundSeconds_shouldAddSecond_returnPlusOneSecond()
	{
		Duration expectedValue = Duration.ofSeconds(1);
		Duration timeToRound = Duration.ofMillis(500); // 00:00:00.5
		
		Duration returnValue = formatter.roundSeconds(timeToRound);
		
		assertEquals(expectedValue.getSeconds(), returnValue.getSeconds());
	}
	
	@Test
	public void roundSeconds_shouldNotAddSecond_returnSameNumberOfSeconds()
	{	
		Duration expectedValue = Duration.ofSeconds(1);
		Duration timeToRound = Duration.ofMillis(1400); // 00:00:01.4
		
		Duration returnValue = formatter.roundSeconds(timeToRound);
		
		assertEquals(expectedValue.getSeconds(), returnValue.getSeconds());
	}
	
	@Test
	public void roundSeconds_shouldAddSecond_returnZeroNanos()
	{
		Duration timeToRound = Duration.ofMillis(500);
		
		Duration returnValue = formatter.roundSeconds(timeToRound);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void roundSeconds_shouldNotAddSecond_returnZeroNanos()
	{
		Duration timeToRound = Duration.ofMillis(1400); // 00:00:01.4
		
		Duration returnValue = formatter.roundSeconds(timeToRound);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void formatCSVOutputTime_singleDigitValues__assertContent()
	{
		LocalTime parsedTime = LocalTime.parse("03:05:09");
		Duration timeToRound = Duration.between(LocalTime.MIDNIGHT, parsedTime);
		
		String returnValue = formatter.formatCSVOutputTime(timeToRound);
		String expectedValue = "03:05:09";
		
		assertEquals(expectedValue, returnValue);
	}

}
