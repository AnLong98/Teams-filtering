package utilities;

import java.time.LocalTime;

import data.Runner;
import data.Team;

public class TeamsForTest {
	
	public static Team createPedjaTeam() 
	{
		Team team = new Team("Pedja team");
		
		team.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "M", LocalTime.of(2, 20, 32)));
		team.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", "1997", 101, "M", LocalTime.of(2, 20, 33)));
		team.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", "1996", 102, "M", LocalTime.of(2, 20, 34)));
		team.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", "1995", 103, "M", LocalTime.of(2, 20, 35)));
		team.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", "1994", 104, "M", LocalTime.of(2, 20, 36)));
		team.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", "1993", 105, "M", LocalTime.of(2, 20, 37)));
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}
	
	public static Team createEstradaTeam() 
	{
		Team team = new Team("Estrada team");
		
		team.AddRunnerToTeam(new Runner("Ð�Ð°Ñ‚Ð°ÑˆÐ°", "Ð‘ÐµÐºÐ²Ð°Ð»Ð°Ñ†", "SRB", "1982", 200, "F", LocalTime.parse("02:40:30")));
		team.AddRunnerToTeam(new Runner("Marina", "ViÅ¡koviÄ‡", "SRB", "1988", 201, "F", LocalTime.parse("02:42:30")));
		team.AddRunnerToTeam(new Runner("Fahreta", "Å½ivojinoviÄ‡", "SRB", "1958", 202, "F", LocalTime.parse("02:44:30")));
		team.AddRunnerToTeam(new Runner("Radojka", "AdÅ¾iÄ‡", "SRB", "1977", 203, "F", LocalTime.parse("02:45:35")));
		team.AddRunnerToTeam(new Runner("Marina", "GavriÄ‡", "SRB", "1996", 204, "F", LocalTime.parse("02:46:36")));
		team.AddRunnerToTeam(new Runner("Anastasija", "Rudan", "SRB", "1996", 205, "F", LocalTime.parse("02:48:37")));
		team.AddRunnerToTeam(new Runner("Marijana", "Matkovski", "SRB", "1996", 206, "F", LocalTime.parse("02:48:57")));
		
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}
	
	public static Team createTrkaRsTeam() 
	{
		Team team = new Team("Trka.rs team");
		
		team.AddRunnerToTeam(new Runner("Bojan", "Ä†ulibrk", "SRB", "1988", 300, "M", LocalTime.of(2, 2, 1)));
		team.AddRunnerToTeam(new Runner("Ð‘Ð¾ÑˆÐºÐ¾", "Ð¡Ñ‚ÑƒÐ¿Ð°Ñ€", "SRB", "1985", 301, "M", LocalTime.of(2, 2, 33)));
		team.AddRunnerToTeam(new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 302, "M", LocalTime.of(2, 3, 34)));
		team.AddRunnerToTeam(new Runner("Ð”ÑƒÑˆÐ°Ð½", "ÐœÐ°Ñ€Ñ˜Ð°Ð½Ñ�ÐºÐ¸", "SRB", "1996", 303, "M", LocalTime.of(2, 4, 35)));
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}
	
	public static Team createNenormalniTeam() 
	{
		Team team = new Team("Nenormalni ludaci koji trče previše");
		
		team.AddRunnerToTeam(new Runner("Nenormalni", "Lik", "SRB", "1982", 400, "M", LocalTime.parse("12:40:30")));
		team.AddRunnerToTeam(new Runner("Nenormalniji", "Lik", "SRB", "1976", 401, "M", LocalTime.parse("14:25:38")));
		team.AddRunnerToTeam(new Runner("Nenormalna", "Likuša", "SRB", "1985", 402, "F", LocalTime.parse("11:12:13")));
		team.AddRunnerToTeam(new Runner("Nenormalnija", "Likuša", "SRB", "1971", 403, "F", LocalTime.parse("15:42:41")));

		
		
		team.TrimTeam(4);
		team.calculateTeamTotalTime();
		team.calculateTeamAverageTime();
		
		return team;
	}

}
