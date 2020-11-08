package utilities;

import java.time.Duration;

import contracts.ITimeFormatting;

public class TimeFormatter implements ITimeFormatting{
	private static int nanoBound = 500000000;

	@Override
	public Duration roundSeconds(Duration time) {
		if (time.getNano() >= nanoBound) 
		{
			time = time.plusSeconds(1);
		}
		
		time = time.minusNanos(time.getNano());
		return time;
		
	}

	@Override
	public String formatCSVOutputTime(Duration duration, String format) {
		if(!format.contains("S"))
		{
			duration = roundSeconds(duration);
		}
		
		long secondsDuration = Math.abs(duration.getSeconds());
		long hours = secondsDuration / 3600;
		long minutes = (secondsDuration % 3600) / 60;
		long seconds = secondsDuration % 60;
		long millis = Math.abs(duration.getNano() / 1000000 );
		
		String formattedTime = "";
		String delimiter = ":";
		String delimiterMillis = ".";
		
		if(hours < 10 && format.startsWith("HH"))
		{
			formattedTime += "0";
		}

		formattedTime += String.valueOf(hours);
		formattedTime += delimiter;
		
		if(minutes < 10)
		{
			formattedTime += "0";
		}
		
		formattedTime += String.valueOf(minutes);
		formattedTime += delimiter;
		
		if(seconds < 10)
		{
			formattedTime += "0";
		}

		formattedTime += String.valueOf(seconds);
		
		if(format.endsWith("SSS"))
		{
			formattedTime += delimiterMillis;
			formattedTime += Long.toString(millis);
			
		}else if(format.endsWith("SS"))
		{
			formattedTime += delimiterMillis;
			formattedTime += Long.toString(millis).substring(0, 2);
			
		}else if(format.endsWith("S"))
		{
			formattedTime += delimiterMillis;
			formattedTime += Long.toString(millis).substring(0, 1);
		}
		
		return formattedTime;
	}

}
