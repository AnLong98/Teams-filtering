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
	private ITimeFormatting formatter;
	
	public TeamsFilter(ITeamTrimming teamTrimmer, ITeamSorting teamSorter, ITimeFormatting formatter) {
		super();
		this.teamTrimmer = teamTrimmer;
		this.teamSorter = teamSorter;
		this.formatter = formatter;
	}
	
	@Override
	public ArrayList<Team> filterTeamsToSizeByNumber(ArrayList<Team> teams, int validSize) {
		teams = teamTrimmer.trimTeamsToSizeByNumber(teams, validSize);
		for(Team team : teams) 
		{
			team.calculateTeamTotalTime();
			team.calculateTeamAverageTime(formatter);
		}	
		teams = teamSorter.sortByTotalTime(teams);
		return teams;
	}

	@Override
	public ArrayList<Team> filterTeamsToSizeByGender(ArrayList<Team> teams, int malesRequired, int femalesRequired) {
		// TODO Auto-generated method stub
		return null;
	}

}
