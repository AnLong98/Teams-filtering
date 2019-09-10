package utilities;

import java.time.LocalTime;
import java.util.ArrayList;

import data.Runner;
import data.Team;

public class DataUtilities {
	
	private static int nanoBound = 500000000;
	
	public static ArrayList<Team> removeExtraMembersFromTeams(ArrayList<Team> teams, int validSize)
	{
		ArrayList<Team> validTeams = new ArrayList<Team>();
		
		for (Team team : teams)
		{
			boolean isValid = team.TrimTeam(validSize);
			
			if (isValid)
			{
				validTeams.add(team);
			}
		}
		
		return validTeams;
	}
	
	public static String[] runnerToCSVString(Runner r) 
	{
		String[] ret = { String.valueOf(r.getBib_number()), String.valueOf(r.getFirstName()),
				String.valueOf(r.getLastName()) };
		
		return ret;
	}
	
	public static LocalTime roundSeconds(LocalTime time) {
		
		if (time.getNano() >= nanoBound) 
		{
			time = time.plusSeconds(1);
		}
		
		time = time.minusNanos(time.getNano());
		
		return time;
	}
	
	public static String formatCSVOutputTime(LocalTime localTime)
	{
		int hours = localTime.getHour();
		int minutes = localTime.getMinute();
		int seconds = localTime.getSecond();
		
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
