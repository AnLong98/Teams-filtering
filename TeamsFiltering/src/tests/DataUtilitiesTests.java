package tests;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import data.Runner;
import data.Team;
import utilities.DataUtilities;
import utilities.TeamsForTest;

public class DataUtilitiesTests {
	
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
		
		teams = DataUtilities.sortByTotalTime(teams);
		
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
		
		teams = DataUtilities.sortByTotalTime(teams);
		
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
		
		teams = DataUtilities.sortByTotalTime(teams);
		
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
		
		teams = DataUtilities.sortByTotalTime(teams);
		
		assertEquals(teams.get(0).getTeamMembers().get(0).getBib_number(), team3.getTeamMembers().get(0).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(1).getBib_number(), team3.getTeamMembers().get(1).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(2).getBib_number(), team3.getTeamMembers().get(2).getBib_number());
		assertEquals(teams.get(0).getTeamMembers().get(3).getBib_number(), team3.getTeamMembers().get(3).getBib_number());
	}
	
	@Test
	public void roundSeconds_shouldAddSecond_returnPlusOneSecond()
	{
		Duration expectedValue = Duration.ofSeconds(1);
		Duration timeToRound = Duration.ofMillis(500); // 00:00:00.5
		
		Duration returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(expectedValue.getSeconds(), returnValue.getSeconds());
	}
	
	@Test
	public void roundSeconds_shouldNotAddSecond_returnSameNumberOfSeconds()
	{	
		Duration expectedValue = Duration.ofSeconds(1);
		Duration timeToRound = Duration.ofMillis(1400); // 00:00:01.4
		
		Duration returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(expectedValue.getSeconds(), returnValue.getSeconds());
	}
	
	@Test
	public void roundSeconds_shouldAddSecond_returnZeroNanos()
	{
		Duration timeToRound = Duration.ofMillis(500);
		
		Duration returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void roundSeconds_shouldNotAddSecond_returnZeroNanos()
	{
		Duration timeToRound = Duration.ofMillis(1400); // 00:00:01.4
		
		Duration returnValue = DataUtilities.roundSeconds(timeToRound);
		
		assertEquals(0, returnValue.getNano());
	}
	
	@Test
	public void formatCSVOutputTime_singleDigitValues__assertContent()
	{
		LocalTime parsedTime = LocalTime.parse("03:05:09");
		Duration timeToRound = Duration.between(LocalTime.MIDNIGHT, parsedTime);
		
		String returnValue = DataUtilities.formatCSVOutputTime(timeToRound);
		String expectedValue = "03:05:09";
		
		assertEquals(expectedValue, returnValue);
	}

}
