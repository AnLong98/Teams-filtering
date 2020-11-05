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
	public String formatCSVOutputTime(Duration duration) {
		long secondsDuration = Math.abs(duration.getSeconds());
		long hours = secondsDuration / 3600;
		long minutes = (secondsDuration % 3600) / 60;
		long seconds = secondsDuration % 60;
		
		String formattedTime = "";
		String delmiter = ":";
		
		if(hours < 10)
		{
			formattedTime += "0";
		}

		formattedTime += String.valueOf(hours);
		formattedTime += delmiter;
		
		if(minutes < 10)
		{
			formattedTime += "0";
		}
		
		formattedTime += String.valueOf(minutes);
		formattedTime += delmiter;
		
		if(seconds < 10)
		{
			formattedTime += "0";
		}

		formattedTime += String.valueOf(seconds);
		
		return formattedTime;
	}

}
