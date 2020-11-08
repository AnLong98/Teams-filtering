package tests;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.Test;

import data.Runner;
import data.Team;
import utilities.TeamsForTest;
import utilities.TimeFormatter;

public class DataTests {
	private TimeFormatter formatter =  new TimeFormatter();
	
	@Test
	public void trimTeam_teamSizeBigger_returnsTrue()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		
		assertTrue(team.TrimTeam(4));
	}
	
	@Test
	public void trimTeam_teamSizeSmaller_returnsFalse()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		
		assertFalse(team.TrimTeam(10));
	}
	
	@Test
	public void trimTeam_newSize4_assertMemberCount()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		
		team.TrimTeam(4);
		
		assertEquals(team.getTeamMembers().size(), 4);
	}
	
	@Test
	public void trimTeam_newSize4_assertMemberInfo()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		
		team.TrimTeam(4);
		
		assertEquals(team.getTeamMembers().get(0).getBib_number(), 100);
		assertEquals(team.getTeamMembers().get(1).getBib_number(), 101);
		assertEquals(team.getTeamMembers().get(2).getBib_number(), 102);
		assertEquals(team.getTeamMembers().get(3).getBib_number(), 103);
	}
	
	@Test 
	public void trimTeam_allMalesEnoughMembers_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertTrue(pedjaTeam.TrimTeam(3,  0));
		assertEquals(pedjaTeam.getTeamMembers().get(0).getBib_number(), 100);
		assertEquals(pedjaTeam.getTeamMembers().get(1).getBib_number(), 102);
		assertEquals(pedjaTeam.getTeamMembers().get(2).getBib_number(), 103);
		
		
	}
	
	@Test 
	public void trimTeam_3Males2FemalesEnoughBoth_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertTrue(pedjaTeam.TrimTeam(3,  2));
		assertEquals(pedjaTeam.getTeamMembers().get(0).getBib_number(), 100);
		assertEquals(pedjaTeam.getTeamMembers().get(1).getBib_number(), 101);
		assertEquals(pedjaTeam.getTeamMembers().get(2).getBib_number(), 102);
		assertEquals(pedjaTeam.getTeamMembers().get(3).getBib_number(), 103);
		assertEquals(pedjaTeam.getTeamMembers().get(4).getBib_number(), 105);
		
	}
	
	@Test 
	public void trimTeam_2Males3FemalesNotEnoughFemales_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertFalse(pedjaTeam.TrimTeam(2,  3));
		
	}
	
	@Test 
	public void trimTeam_5Males3FemalesNotEnoughBoth_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertFalse(pedjaTeam.TrimTeam(5,  3));
		
	}
	
	@Test 
	public void trimTeam_5Males2FemalesNotEnoughMales_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertFalse(pedjaTeam.TrimTeam(5,  2));
		
	}
	
	@Test 
	public void trimTeam_allFemalesEnoughMembers_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertTrue(pedjaTeam.TrimTeam(0,  2));
		assertEquals(pedjaTeam.getTeamMembers().get(0).getBib_number(), 101);
		assertEquals(pedjaTeam.getTeamMembers().get(1).getBib_number(), 105);
	}
	
	@Test 
	public void trimTeam_tooSmallTeam_assertTrimmed()
	{
		Team pedjaTeam = TeamsForTest.createPedjaTeamRaw();
		
		assertFalse(pedjaTeam.TrimTeam(5,  5));
	}
	
	@Test
	public void calculateTeamTotalTime_zeroTeamMembers_returnsFalse()
	{
		Team team = new Team("Test team");
		
		assertFalse(team.calculateTeamTotalTime());
	}
	
	@Test
	public void calculateTeamTotalTime_zeroTeamMembers_assertZeroTotalTime()
	{
		Team team = new Team("Test team");
		
		team.calculateTeamTotalTime();
		
		assertEquals(LocalTime.MIDNIGHT.toSecondOfDay(), team.getTotalTime().getSeconds());
	}
	
	@Test
	public void calculateTeamTotalTime_teamMembers_returnsTrue()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		
		assertTrue(team.calculateTeamTotalTime());
	}
	
	@Test
	public void calculateTeamTotalTime_teamMembers_assertTotalTime()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		
		team.calculateTeamTotalTime();
		
		assertEquals(LocalTime.of(14, 3, 27).toSecondOfDay(), team.getTotalTime().getSeconds());
	}
	
	@Test
	public void calculateTeamAverageTime_zeroTeamMembers_returnsFalse()
	{
		Team team = new Team("Test team");
		team.calculateTeamTotalTime();
		
		assertFalse(team.calculateTeamAverageTime(formatter));
	}
	
	@Test
	public void calculateTeamAverageTime_zeroTeamMembers_assertZeroAverageTime()
	{
		Team team = new Team("Test team");
		team.calculateTeamTotalTime();
		
		team.calculateTeamAverageTime(formatter);
		
		assertEquals(LocalTime.MIDNIGHT.toSecondOfDay(), team.getAverageTime().getSeconds());
	}
	
	@Test
	public void calculateTeamAverageTime_teamMembers_returnsTrue()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		team.calculateTeamTotalTime();
		
		assertTrue(team.calculateTeamAverageTime(formatter));
	}
	
	@Test
	public void calculateTeamAverageTime_teamMembers_assertAverageTime()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34), "Test team"));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35), "Test team"));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36), "Test team"));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37), "Test team"));
		team.calculateTeamTotalTime();
		
		team.calculateTeamAverageTime(formatter);
		
		assertEquals(LocalTime.parse("02:20:35").toSecondOfDay(), team.getAverageTime().getSeconds());
	}
	
	@Test
	public void calculateTeamTotalTime_TotalTimeOver24h_assertTotalTime()
	{
		Team nenormalniTim = TeamsForTest.createNenormalniTeam();

		
		assertEquals( Duration.parse("P2DT6H1M2.0S").getSeconds(), nenormalniTim.getTotalTime().getSeconds()); //Total time is 2 days 6 hours 1 minute 2 seconds
	}
	
	@Test
	public void calculateTeamAverageTime_TotalTimeOver24h_assertAverageTime()
	{
		Team nenormalniTim = TeamsForTest.createNenormalniTeam();

		
		assertEquals( Duration.parse("PT13H30M16.0S").getSeconds(), nenormalniTim.getAverageTime().getSeconds()); //Average time is 13 hours 30 minutes 16 seconds
	}

}
