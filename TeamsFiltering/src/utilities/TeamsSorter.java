package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import contracts.ITeamSorting;
import data.Team;

public class TeamsSorter implements ITeamSorting {

	@Override
	public ArrayList<Team> sortByTotalTime(ArrayList<Team> teams) {
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
