package utilities;

import java.util.ArrayList;

import data.Team;

public class DataUtilities {
	
	public static ArrayList<Team> RemoveExtraMembersFromTeams(ArrayList<Team> teams, int validSize)
	{
		ArrayList<Team> validTeams = new ArrayList<Team>();
		
		for (Team team : teams)
		{
			boolean isValid = team.TrimTeam(validSize);
			
			if(isValid)validTeams.add(team);
		}
		
		return validTeams;
	}

}
