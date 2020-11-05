package utilities;

import java.util.ArrayList;
import java.util.HashMap;

import contracts.ITeamGrouping;
import contracts.ITeamTrimming;
import data.Runner;
import data.Team;

public class TeamsHandler implements ITeamTrimming, ITeamGrouping {
	
	public ArrayList<Team> groupRunnersByTeam(ArrayList<Runner> runners)
	{
		HashMap<String, Team> teamsMap =  new HashMap<String, Team>();
		
		for(Runner runner : runners)
		{
			String teamName = runner.getTeamName();
			if(teamsMap.containsKey(teamName))
			{
				teamsMap.get(teamName).AddRunnerToTeam(runner);
			}else
			{
				Team newTeam = new Team(teamName);
				newTeam.AddRunnerToTeam(runner);
				teamsMap.put(teamName, newTeam);
			}
		}
		
		return new ArrayList<Team>(teamsMap.values());
	}
	
	public ArrayList<Team> trimTeamsToSizeByNumber(ArrayList<Team> teams, int validSize)
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
	
	public ArrayList<Team> trimTeamsToSizeByGender(ArrayList<Team> teams, int males, int females)
	{
		ArrayList<Team> validTeams = new ArrayList<Team>();
		
		for (Team team : teams)
		{
			boolean isValid = team.TrimTeam(males, females);
			
			if (isValid)
			{
				validTeams.add(team);
			}
		}
		
		return validTeams;
	}

}
