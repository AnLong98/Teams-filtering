package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import data.Runner;
import data.Team;
import utilities.TeamsForTest;
import utilities.TeamsSorter;
import utilities.TimeFormatter;

import utilities.TeamExporter;

public class TeamExporterTests {
	private TeamExporter exporter = new TeamExporter(new TimeFormatter());
	
	@Test
	public void writeCSVFile_sortedTeams_returnsTrue()
	{
		TeamsSorter sorter =  new TeamsSorter();
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = TeamsForTest.createPedjaTeam();
		Team team2 = TeamsForTest.createEstradaTeam();
		Team team3 = TeamsForTest.createTrkaRsTeam();
		
		teams.add(team2);
		teams.add(team1);
		teams.add(team3);
		
		teams = sorter.sortByTotalTime(teams);
		
		assertTrue(exporter.exportTeamsToCSVFile(teams, "test"));
	}
	
	@Test
	public void getCSVDataFromTeam_testTeam_assertContent()
	{
		Runner.setChipTimeFormat("HH:mm:ss");
		Team team1 = TeamsForTest.createPedjaTeam();
		int place = 1;
		
		ArrayList<String[]> csvData = exporter.getCSVDataFromTeam(team1, place);
		
		String[] receivedDataFirst  = csvData.get(0);
		String[] expectedDataFirst = {"1", "100","Predrag", "Glavaš", "M", "1998", "SRB", "Pedja team" , " 02:20:32", " 02:20:34", " 09:22:14"};
		
		String[] receivedDataSecond  = csvData.get(1);
		String[] expectedDataSecond = {"", "101","Predragica", "Glavašica", "F", "1997", "SRB", "Pedja team" , " 02:20:33", "", ""};
		
		assertEquals(expectedDataFirst[0], receivedDataFirst[0]);
		assertEquals(expectedDataFirst[1], receivedDataFirst[1]);
		assertEquals(expectedDataFirst[2], receivedDataFirst[2]);
		assertEquals(expectedDataFirst[3], receivedDataFirst[3]);
		assertEquals(expectedDataFirst[4], receivedDataFirst[4]);
		assertEquals(expectedDataFirst[5], receivedDataFirst[5]);
		assertEquals(expectedDataFirst[6], receivedDataFirst[6]);
		assertEquals(expectedDataFirst[7], receivedDataFirst[7]);
		assertEquals(expectedDataFirst[8], receivedDataFirst[8]);
		assertEquals(expectedDataFirst[9], receivedDataFirst[9]);
		assertEquals(expectedDataFirst[10], receivedDataFirst[10]);
		
		assertEquals(expectedDataSecond[0], receivedDataSecond[0]);
		assertEquals(expectedDataSecond[1], receivedDataSecond[1]);
		assertEquals(expectedDataSecond[2], receivedDataSecond[2]);
		assertEquals(expectedDataSecond[3], receivedDataSecond[3]);
		assertEquals(expectedDataSecond[4], receivedDataSecond[4]);
		assertEquals(expectedDataSecond[5], receivedDataSecond[5]);
		assertEquals(expectedDataSecond[6], receivedDataSecond[6]);
		assertEquals(expectedDataSecond[7], receivedDataSecond[7]);
		assertEquals(expectedDataSecond[8], receivedDataSecond[8]);
		assertEquals(expectedDataSecond[9], receivedDataSecond[9]);
		assertEquals(expectedDataSecond[10], receivedDataSecond[10]);	
	}
	
	

}
