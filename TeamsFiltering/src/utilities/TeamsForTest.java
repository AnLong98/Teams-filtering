package utilities;

import java.time.LocalTime;

import data.Runner;
import data.Team;

public class TeamsForTest {
	
	public static Team createPedjaTeam() 
	{
		Team team = new Team("Pedja team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}
	
	public static Team createEstradaTeam() 
	{
		Team team = new Team("Estrada team");
		
		team.AddRunnerToTeam(new Runner("Наташа", "Беквалац", "SRB", "1982", 200, "Female", LocalTime.parse("02:40:30")));
		team.AddRunnerToTeam(new Runner("Marina", "Višković", "SRB", "1988", 201, "Female", LocalTime.parse("02:42:30")));
		team.AddRunnerToTeam(new Runner("Fahreta", "Živojinović", "SRB", "1958", 202, "Female", LocalTime.parse("02:44:30")));
		team.AddRunnerToTeam(new Runner("Radojka", "Adžić", "SRB", "1977", 203, "Female", LocalTime.parse("02:45:35")));
		team.AddRunnerToTeam(new Runner("Marina", "Gavrić", "SRB", "1996", 204, "Female", LocalTime.parse("02:46:36")));
		team.AddRunnerToTeam(new Runner("Anastasija", "Rudan", "SRB", "1996", 205, "Female", LocalTime.parse("02:48:37")));
		team.AddRunnerToTeam(new Runner("Marijana", "Matkovski", "SRB", "1996", 206, "Female", LocalTime.parse("02:48:57")));
		
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}
	
	public static Team createTrkaRsTeam() 
	{
		Team team = new Team("Trka.rs team");
		
		team.AddRunnerToTeam(new Runner("Bojan", "Ćulibrk", "SRB", "1988", 300, "Male", LocalTime.of(2, 2, 1)));
		team.AddRunnerToTeam(new Runner("Бошко", "Ступар", "SRB", "1985", 301, "Male", LocalTime.of(2, 2, 33)));
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", "1998", 302, "Male", LocalTime.of(2, 3, 34)));
		team.AddRunnerToTeam(new Runner("Душан", "Марјански", "SRB", "1996", 303, "Male", LocalTime.of(2, 4, 35)));
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}

}
