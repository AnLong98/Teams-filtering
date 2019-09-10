package tests;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Test;

import data.Runner;
import data.Team;

public class DataTests {
	
	@Test
	public void trimTeam_teamSizeBigger_returnsTrue()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		assertTrue(team.TrimTeam(4));
	}
	
	@Test
	public void trimTeam_teamSizeSmaller_returnsFalse()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		assertFalse(team.TrimTeam(10));
	}
	
	@Test
	public void trimTeam_newSize4_assertMemberCount()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		team.TrimTeam(4);
		
		assertEquals(team.getTeamMembers().size(), 4);
	}
	
	@Test
	public void trimTeam_newSize4_assertMemberInfo()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		team.TrimTeam(4);
		
		assertEquals(team.getTeamMembers().get(0).getBib_number(), 100);
		assertEquals(team.getTeamMembers().get(1).getBib_number(), 101);
		assertEquals(team.getTeamMembers().get(2).getBib_number(), 102);
		assertEquals(team.getTeamMembers().get(3).getBib_number(), 103);
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
		
		assertEquals(LocalTime.MIDNIGHT.toString(), team.getTotalTime().toString());
	}
	
	@Test
	public void calculateTeamTotalTime_teamMembers_returnsTrue()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		assertTrue(team.calculateTeamTotalTime());
	}
	
	@Test
	public void calculateTeamTotalTime_teamMembers_assertTotalTime()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		team.calculateTeamTotalTime();
		
		assertEquals(LocalTime.of(14, 3, 27).toString(), team.getTotalTime().toString());
	}
	
	@Test
	public void calculateTeamAverageTime_zeroTeamMembers_returnsFalse()
	{
		Team team = new Team("Test team");
		team.calculateTeamTotalTime();
		
		assertFalse(team.calculateTeamAverageTime());
	}
	
	@Test
	public void calculateTeamAverageTime_zeroTeamMembers_assertZeroAverageTime()
	{
		Team team = new Team("Test team");
		team.calculateTeamTotalTime();
		
		team.calculateTeamAverageTime();
		
		assertEquals(LocalTime.MIDNIGHT.toString(), team.getAverageTime().toString());
	}
	
	@Test
	public void calculateTeamAverageTime_teamMembers_returnsTrue()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		team.calculateTeamTotalTime();
		
		assertTrue(team.calculateTeamAverageTime());
	}
	
	@Test
	public void calculateTeamAverageTime_teamMembers_assertAverageTime()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glava�", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glava�ica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glava�urda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glava�etina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glava�er", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glava�a", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		team.calculateTeamTotalTime();
		
		team.calculateTeamAverageTime();
		
		assertEquals(LocalTime.parse("02:20:35").toString(), team.getAverageTime().toString());
	}

}
