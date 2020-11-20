package utilities;

import java.util.ArrayList;

import contracts.ITeamSorting;
import contracts.ITeamTrimming;
import contracts.ITeamsFiltering;
import contracts.ITimeFormatting;
import data.Team;

public class TeamsFilter implements ITeamsFiltering{
	private ITeamTrimming teamTrimmer;
	private ITeamSorting teamSorter;
	
	public TeamsFilter(ITeamTrimming teamTrimmer, ITeamSorting teamSorter) {
		super();
		this.teamTrimmer = teamTrimmer;
		this.teamSorter = teamSorter;
	}
	
	@Override
	public ArrayList<Team> filterTeamsToSizeByNumber(ArrayList<Team> teams, int validSize) {
		teams = teamTrimmer.trimTeamsToSizeByNumber(teams, validSize);
		for(Team team : teams) 
		{
			team.calculateTeamTotalTime();
			team.calculateTeamAverageTime();
		}	
		teams = teamSorter.sortByTotalTime(teams);
		return teams;
	}

	@Override
	public ArrayList<Team> filterTeamsToSizeByGender(ArrayList<Team> teams, int malesRequired, int femalesRequired) {
		teams = teamTrimmer.trimTeamsToSizeByGender(teams, malesRequired, femalesRequired);
		for(Team team : teams) 
		{
			team.calculateTeamTotalTime();
			team.calculateTeamAverageTime();
		}	
		teams = teamSorter.sortByTotalTime(teams);
		return teams;
	}

}
