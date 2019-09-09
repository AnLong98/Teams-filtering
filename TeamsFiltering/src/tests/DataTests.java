package tests;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Test;

import data.Runner;
import data.Team;

public class DataTests {
	
	@Test
	public void Test__TrimTeam__team_size_bigger__returns_true()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", 100, 'M', LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", 101, 'M', LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", 102, 'M', LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", 103, 'M', LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", 104, 'M', LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", 105, 'M', LocalTime.of(2, 20, 37)));
		
		
		assertTrue(team.TrimTeam(4));
	}
	
	@Test
	public void Test__TrimTeam__team_size_smaller__returns_false()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", 100, 'M', LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", 101, 'M', LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", 102, 'M', LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", 103, 'M', LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", 104, 'M', LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", 105, 'M', LocalTime.of(2, 20, 37)));
		
		
		assertFalse(team.TrimTeam(10));
	}
	
	@Test
	public void Test__TrimTeam__new_size_4__assert_member_count()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", 100, 'M', LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", 101, 'M', LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", 102, 'M', LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", 103, 'M', LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", 104, 'M', LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", 105, 'M', LocalTime.of(2, 20, 37)));
		
		team.TrimTeam(4);
		
		assertEquals(team.getTeamMembers().size(), 4);
	}
	
	@Test
	public void Test__TrimTeam__new_size_4__assert_member_info()
	{
		Team team = new Team("Test team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", 100, 'M', LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", 101, 'M', LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", 102, 'M', LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", 103, 'M', LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", 104, 'M', LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", 105, 'M', LocalTime.of(2, 20, 37)));
		
		team.TrimTeam(4);
		
		assertEquals(team.getTeamMembers().get(0).getBib_number(), 100);
		assertEquals(team.getTeamMembers().get(1).getBib_number(), 101);
		assertEquals(team.getTeamMembers().get(2).getBib_number(), 102);
		assertEquals(team.getTeamMembers().get(3).getBib_number(), 103);

	}

}
