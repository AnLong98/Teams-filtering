package utilities;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import data.Team;

public class DataUtilities {
	
	private static int nanoBound = 500000000;
	
	
	public static Duration roundSeconds(Duration time) 
	{
		if (time.getNano() >= nanoBound) 
		{
			time = time.plusSeconds(1);
		}
		
		time = time.minusNanos(time.getNano());
		
		return time;
	}
	
	public static String formatCSVOutputTime(Duration duration)
	{
		
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

	public static ArrayList<Team> sortByTotalTime(ArrayList<Team> teams) 
	{
		Collections.sort(teams, new Comparator<Team>() 
		{
		    @Override
		    public int compare(Team team1, Team team2) 
		    {
		        return team1.getTotalTime().compareTo(team2.getTotalTime());
		    }
		});
		
		return teams;
	}

}
