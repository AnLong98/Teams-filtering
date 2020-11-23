package utilities;

import java.time.Duration;

import contracts.ITimeFormatting;

public class TimeFormatter implements ITimeFormatting{
	private static int nanoBound = 500000000;
	private static final int nanoDecimals = 9;

	@Override
	public Duration roundTime(Duration time, int decimalsToHave) {
		int nanoSeconds = time.getNano();
		if (decimalsToHave == 0 && nanoSeconds >= nanoBound) 
		{
			time = time.plusSeconds(1);
			time = time.minusNanos(time.getNano());
			return time;
		
		}
		
		int excessDecimalsMask  = (int)Math.pow(10, nanoDecimals - decimalsToHave - 1);
		nanoSeconds = nanoSeconds / excessDecimalsMask; 
		int decidingDecimal = nanoSeconds % 10;
		nanoSeconds /= 10;
		excessDecimalsMask *= 10;
		if(decidingDecimal >= 5)
		{
			nanoSeconds += 1;
		}
		
		if(nanoSeconds >= Math.pow(10, decimalsToHave + 1))
		{
			time = time.plusSeconds(1);
			time = time.minusNanos(time.getNano());
			return time;
		}
		
		time = time.minusNanos(time.getNano());
		time = time.plusNanos(nanoSeconds * excessDecimalsMask);
		
		return time;
		
	}

	@Override
	public String formatCSVOutputTime(Duration duration, String format) {
		long secondsDuration = Math.abs(duration.getSeconds());
		long hours = secondsDuration / 3600;
		long minutes = (secondsDuration % 3600) / 60;
		long seconds = secondsDuration % 60;
		long millis = Math.abs(duration.getNano() / 1000000 );
		
		String formattedTime = " ";
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
		String millisString = "";
		
		if(millis < 10)
		{
			millisString += "00";
		}else if(millis < 100)
		{
			millisString += "0";
		}
		
		millisString += Long.toString(millis);
		if(millis == 0)millisString = "000";
		
		if(format.endsWith("SSS"))
		{
			formattedTime += delimiterMillis;
			formattedTime += millisString;
			
		}else if(format.endsWith("SS"))
		{
			formattedTime += delimiterMillis;
			formattedTime += millisString.substring(0, 2);
			
		}else if(format.endsWith("S"))
		{
			formattedTime += delimiterMillis;
			formattedTime += millisString.substring(0, 1);
		}
		
		return formattedTime;
	}

}
