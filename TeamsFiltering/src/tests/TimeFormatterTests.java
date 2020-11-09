package tests;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.Test;
import utilities.TimeFormatter;

public class TimeFormatterTests {
	TimeFormatter formatter =  new TimeFormatter();
	
	@Test
	public void roundTime_shouldAddSecond_returnPlusOneSecond()
	{
		Duration expectedValue = Duration.ofSeconds(1);
		Duration timeToRound = Duration.ofMillis(500); // 00:00:00.5
		
		Duration returnValue = formatter.roundTime(timeToRound, 0);
		
		assertEquals(expectedValue.getSeconds(), returnValue.getSeconds());
	}
	
	@Test
	public void roundTime_shouldNotAddSecond_returnSameNumberOfSeconds()
	{	
		Duration expectedValue = Duration.ofSeconds(1);
		Duration timeToRound = Duration.ofMillis(1400); // 00:00:01.4
		
		Duration returnValue = formatter.roundTime(timeToRound, 0);
		
		assertEquals(expectedValue.getSeconds(), returnValue.getSeconds());
	}
	
	@Test
	public void roundTime_shouldAddSecond_returnZeroNanos()
	{
		Duration timeToRound = Duration.ofMillis(500);
		
		Duration returnValue = formatter.roundTime(timeToRound, 0);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void roundTime_shouldNotAddSecond_returnZeroNanos()
	{
		Duration timeToRound = Duration.ofMillis(1400); // 00:00:01.4
		
		Duration returnValue = formatter.roundTime(timeToRound, 0);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void roundTime_round1Decimal_returnsRounded()
	{
		Duration timeToRound = Duration.ofMillis(1560); // 00:00:01.56
		
		Duration returnValue = formatter.roundTime(timeToRound, 1);
		
		assertEquals(600000000, returnValue.getNano());
	}
	
	@Test
	public void roundTime_round1Decimal_returnsNotRounded()
	{
		Duration timeToRound = Duration.ofMillis(1540); // 00:00:01.54
		
		Duration returnValue = formatter.roundTime(timeToRound, 1);
		
		assertEquals(500000000, returnValue.getNano());
		assertEquals(1, returnValue.getSeconds());
	}
	
	@Test
	public void roundTime_round2Decimal_returnsRounded()
	{
		Duration timeToRound = Duration.ofMillis(1565); // 00:00:01.565
		
		Duration returnValue = formatter.roundTime(timeToRound, 2);
		
		assertEquals(570000000, returnValue.getNano());
	}
	
	@Test
	public void roundTime_round2Decimal_returnsNotRounded()
	{
		Duration timeToRound = Duration.ofMillis(1540); // 00:00:01.540
		
		Duration returnValue = formatter.roundTime(timeToRound, 2);
		
		assertEquals(540000000, returnValue.getNano());
		assertEquals(1, returnValue.getSeconds());
	}
	
	@Test
	public void roundTime_round3Decimal_returnsRounded()
	{
		Duration timeToRound = Duration.ofNanos(565700000); // 00:00:01.5657
		
		Duration returnValue = formatter.roundTime(timeToRound, 3);
		
		assertEquals(566000000, returnValue.getNano());
	}
	
	@Test
	public void roundTime_round3Decimal_returnsNotRounded()
	{
		Duration timeToRound = Duration.ofMillis(1540); // 00:00:01.540
		
		Duration returnValue = formatter.roundTime(timeToRound, 3);
		
		assertEquals(540000000, returnValue.getNano());
		assertEquals(1, returnValue.getSeconds());
	}
	
	@Test
	public void roundTime_chainIncerase_returnsRounded()
	{
		Duration timeToRound = Duration.ofMillis(59998); // 09:59:59.998
		timeToRound = timeToRound.plusMinutes(59);
		timeToRound = timeToRound.plusHours(9);
		
		Duration returnValue = formatter.roundTime(timeToRound, 2);
		
		assertEquals(0, returnValue.getNano());
		assertEquals(10 * 60 * 60, returnValue.getSeconds());
	}
	
	@Test
	public void formatCSVOutputTime_singleDigitValues__assertContent()
	{
		LocalTime parsedTime = LocalTime.parse("03:05:09");
		Duration timeToRound = Duration.between(LocalTime.MIDNIGHT, parsedTime);
		
		String returnValue = formatter.formatCSVOutputTime(timeToRound, "HH:mm:ss");
		String expectedValue = "03:05:09";
		
		assertEquals(expectedValue, returnValue);
	}
	
	@Test
	public void formatCSVOutputTime_HHmmssS__assertContent()
	{
		LocalTime parsedTime = LocalTime.of(3, 5, 9, 100000000);
		Duration timeToRound = Duration.between(LocalTime.MIDNIGHT, parsedTime);
		
		String returnValue = formatter.formatCSVOutputTime(timeToRound, "HH:mm:ss.S");
		String expectedValue = "03:05:09.1";
		
		assertEquals(expectedValue, returnValue);
	}
	
	@Test
	public void formatCSVOutputTime_HHmmssSS__assertContent()
	{
		LocalTime parsedTime = LocalTime.of(3, 5, 9, 100000000);
		Duration timeToRound = Duration.between(LocalTime.MIDNIGHT, parsedTime);
		
		String returnValue = formatter.formatCSVOutputTime(timeToRound, "HH:mm:ss.SS");
		String expectedValue = "03:05:09.10";
		
		assertEquals(expectedValue, returnValue);
	}
	
	@Test
	public void formatCSVOutputTime_HHmmssSSS__assertContent()
	{
		LocalTime parsedTime = LocalTime.of(12, 5, 9, 123000000);
		Duration timeToRound = Duration.between(LocalTime.MIDNIGHT, parsedTime);
		
		String returnValue = formatter.formatCSVOutputTime(timeToRound, "HH:mm:ss.SSS");
		String expectedValue = "12:05:09.123";
		
		assertEquals(expectedValue, returnValue);
	}

}
