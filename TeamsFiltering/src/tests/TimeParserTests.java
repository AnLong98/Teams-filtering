package tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;
import utilities.TimeParser;

public class TimeParserTests {
	TimeParser parser = new TimeParser(); 

	@Test
	public void parseChipTime_hhmmssTimeFormat_assertParsed()
	{
		String time = "03:20:52";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52));
		
	}
	
	@Test
	public void parseChipTime_hmmssTimeFormat_assertParsed()
	{
		String time = "3:20:52";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52));
		
	}
	
	@Test
	public void parseChipTime_hhmmss000TimeFormat_assertParsed()
	{
		String time = "03:20:52.223";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52, 223000000));
		
	}
	
	@Test
	public void parseChipTime_hmmss000TimeFormat_assertParsed()
	{
		String time = "3:20:52.100";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52, 100000000));
		
	}
	
	@Test
	public void parseChipTime_hhmmss00TimeFormat_assertParsed()
	{
		String time = "03:20:52.22";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52, 220000000));
		
	}
	
	@Test
	public void parseChipTime_hmmss00TimeFormat_assertParsed()
	{
		String time = "3:20:52.11";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52, 110000000));
		
	}
	
	@Test
	public void parseChipTime_hhmmss0TimeFormat_assertParsed()
	{
		String time = "03:20:52.2";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52, 200000000));
	
	}
	
	@Test
	public void parseChipTime_hmmss0TimeFormat_assertParsed()
	{
		String time = "3:20:52.1";
		assertEquals(parser.parseChipTime(time), LocalTime.of(3, 20, 52, 100000000));
		
	}
	
	@Test
	public void parseChipTime_hhhmmssTimeFormat_returnsNull()
	{
		String time = "033:20:52";
		assertEquals(parser.parseChipTime(time), null);
		
	}
	
	@Test
	public void parseChipTime_emptyString_returnsNull()
	{
		String time = "";
		assertEquals(parser.parseChipTime(time), null);
		
	}
	
	@Test
	public void parseChipTimeFormat_hhmmssSSS_returnsCorrectFormat()
	{
		String time = "03:20:52.224";
		assertEquals(parser.parseChipTimeFormat(time), "HH:mm:ss.SSS");
		
	}
	
	@Test
	public void parseChipTimeFormat_hhmmssSS_returnsCorrectFormat()
	{
		String time = "03:20:52.22";
		assertEquals(parser.parseChipTimeFormat(time), "HH:mm:ss.SS");
		
	}
	
	@Test
	public void parseChipTimeFormat_hhmmssS_returnsCorrectFormat()
	{
		String time = "03:20:52.2";
		assertEquals(parser.parseChipTimeFormat(time), "HH:mm:ss.S");
		
	}
	
	@Test
	public void parseChipTimeFormat_hmmssSSS_returnsCorrectFormat()
	{
		String time = "3:20:52.224";
		assertEquals(parser.parseChipTimeFormat(time), "H:mm:ss.SSS");
		
	}
	
	@Test
	public void parseChipTimeFormat_hmmssSS_returnsCorrectFormat()
	{
		String time = "3:20:52.22";
		assertEquals(parser.parseChipTimeFormat(time), "H:mm:ss.SS");
		
	}
	
	@Test
	public void parseChipTimeFormat_hmmssS_returnsCorrectFormat()
	{
		String time = "3:20:52.2";
		assertEquals(parser.parseChipTimeFormat(time), "H:mm:ss.S");
		
	}
}
