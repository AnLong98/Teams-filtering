package utilities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import contracts.ITimeParsing;

public class TimeParser implements ITimeParsing{
	private static final String[] supportedFormats = {"HH:mm:ss.SSS", "H:mm:ss.SSS", "HH:mm:ss.SS", "H:mm:ss.SS", "HH:mm:ss.S", "H:mm:ss.S", "HH:mm:ss",  "H:mm:ss"};

	@Override
	public LocalTime parseChipTime(String timeString) {
		for(String format : supportedFormats)
		{
			try
			{
				DateTimeFormatter timeParseFormatter = DateTimeFormatter.ofPattern(format);
				LocalTime localTime = LocalTime.parse(timeString, timeParseFormatter);
				return localTime;
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		return null;
	}

	@Override
	public String parseChipTimeFormat(String timeString) {
		for(String format : supportedFormats)
		{
			try
			{
				DateTimeFormatter timeParseFormatter = DateTimeFormatter.ofPattern(format);
				LocalTime.parse(timeString, timeParseFormatter);
				return format;
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		return null;
	}

}
