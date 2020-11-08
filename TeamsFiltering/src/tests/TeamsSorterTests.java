package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import data.Team;
import utilities.TeamsForTest;
import utilities.TeamsSorter;

public class TeamsSorterTests {
	TeamsSorter sorter =  new TeamsSorter();

	@Test
	public void sortByTotalTime_unsortedTeams_assertSortedTeamNameValues()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = TeamsForTest.createPedjaTeam();
		Team team2 = TeamsForTest.createEstradaTeam();
		Team team3 = TeamsForTest.createTrkaRsTeam();
		
		teams.add(team2);
		teams.add(team1);
		teams.add(team3);
		
		teams = sorter.sortByTotalTime(teams);
		
		assertEquals(teams.get(0).getTeamName(), team3.getTeamName());
		assertEquals(teams.get(1).getTeamName(), team1.getTeamName());
		assertEquals(teams.get(2).getTeamName(), team2.getTeamName());
	}
	
	@Test
	public void sortByTotalTime_sortedTeams_assertTeamOrderByTeamName()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = TeamsForTest.createPedjaTeam();
		Team team2 = TeamsForTest.createEstradaTeam();
		Team team3 = TeamsForTest.createTrkaRsTeam();
		
		teams.add(team3);
		teams.add(team1);
		teams.add(team2);
		
		teams = sorter.sortByTotalTime(teams);
		
		assertEquals(teams.get(0).getTeamName(), team3.getTeamName());
		assertEquals(teams.get(1).getTeamName(), team1.getTeamName());
		assertEquals(teams.get(2).getTeamName(), team2.getTeamName());
	}
	
	@Test
	public void sortByTotalTime_unsortedTeams_assertRunnersInTeamOrderUnchanged()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = TeamsForTest.createPedjaTeam();
		Team team2 = TeamsForTest.createEstradaTeam();
		Team team3 = TeamsForTest.createTrkaRsTeam();
		
		teams.add(team2);
		teams.add(team1);
		teams.add(team3);
		
		teams = sorter.sortByTotalTime(teams);
		
		assertEquals(teams.get(0).getTeamMembers().get(0).getBib_number(), team3.getTeamMembers().get(0).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(1).getBib_number(), team3.getTeamMembers().get(1).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(2).getBib_number(), team3.getTeamMembers().get(2).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(3).getBib_number(), team3.getTeamMembers().get(3).getBib_number());
	}
	
	@Test
	public void sortByTotalTime_sortedTeams_assertRunnersInTeamOrderUnchanged()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = TeamsForTest.createPedjaTeam();
		Team team2 = TeamsForTest.createEstradaTeam();
		Team team3 = TeamsForTest.createTrkaRsTeam();
		
		teams.add(team3);
		teams.add(team1);
		teams.add(team2);
		
		teams = sorter.sortByTotalTime(teams);
		
		assertEquals(teams.get(0).getTeamMembers().get(0).getBib_number(), team3.getTeamMembers().get(0).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(1).getBib_number(), team3.getTeamMembers().get(1).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(2).getBib_number(), team3.getTeamMembers().get(2).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(3).getBib_number(), team3.getTeamMembers().get(3).getBib_number());
	}
}
