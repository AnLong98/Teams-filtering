package utilities;

import java.util.ArrayList;
import java.util.HashMap;

import data.Runner;
import data.Team;

public class TeamsHandler {
	
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

}
