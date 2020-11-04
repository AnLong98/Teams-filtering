package tests;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;
import data.Runner;
import data.Team;
import utilities.RunnersSorter;
import utilities.TeamsHandler;

public class TeamsHandlerTests {
	
	@Test
	public void groupRunnersByTeam_scatteredTeams_assertGrouped()
	{
		ArrayList<Runner> runners = new ArrayList<Runner>();
		
		runners.add(new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 300, "M", LocalTime.of(2, 1, 1), "T1"));
		runners.add(new Runner("Ð‘Ð¾ÑˆÐºÐ¾", "Ð¡Ñ‚ÑƒÐ¿Ð°Ñ€", "SRB", "1985", 301, "M", LocalTime.of(2, 1, 2), "T2"));
		runners.add(new Runner("Ð”ÑƒÑˆÐ°Ð½", "ÐœÐ°Ñ€Ñ˜Ð°Ð½Ñ�ÐºÐ¸", "SRB", "1996", 302, "M", LocalTime.of(2, 1, 3), "T3"));
		runners.add(new Runner("Djoka", "Prdikoka", "SRB", "1988", 303, "M", LocalTime.of(2, 1, 4), "T1"));
		runners.add(new Runner("Mika", "Mikic", "SRB", "1988", 304, "M", LocalTime.of(2, 1, 5), "T1"));
		runners.add(new Runner("Zika", "Zikic", "SRB", "1988", 305, "M", LocalTime.of(2, 1, 5), "T3"));
		
		TeamsHandler handler = new TeamsHandler();
		
		ArrayList<Team> teams = handler.groupRunnersByTeam(runners);
		Team t1 = teams.get(0);
		Team t2 = teams.get(1);
		Team t3 = teams.get(2);
		
		
		assertEquals(teams.size(), 3);
		assertEquals(t1.getTeamName(), "T1");
		assertEquals(t2.getTeamName(), "T2");
		assertEquals(t3.getTeamName(), "T3");
		
		ArrayList<Runner> t1Runners = t1.getTeamMembers();
		ArrayList<Runner> t2Runners = t2.getTeamMembers();
		ArrayList<Runner> t3Runners = t3.getTeamMembers();
		
		assertEquals(t1Runners.size(), 3);
		assertEquals(t2Runners.size(), 1);
		assertEquals(t3Runners.size(), 2);

	}
	
	@Test
	public void groupRunnersByTeam_wrongTeamNames_assertNotGrouped()
	{
		ArrayList<Runner> runners = new ArrayList<Runner>();
		
		runners.add(new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 300, "M", LocalTime.of(2, 1, 1), "Tim 1"));
		runners.add(new Runner("Ð‘Ð¾ÑˆÐºÐ¾", "Ð¡Ñ‚ÑƒÐ¿Ð°Ñ€", "SRB", "1985", 301, "M", LocalTime.of(2, 1, 2), "tim 1"));
		runners.add(new Runner("Ð”ÑƒÑˆÐ°Ð½", "ÐœÐ°Ñ€Ñ˜Ð°Ð½Ñ�ÐºÐ¸", "SRB", "1996", 302, "M", LocalTime.of(2, 1, 3), "tim1"));
		runners.add(new Runner("Djoka", "Prdikoka", "SRB", "1988", 303, "M", LocalTime.of(2, 1, 4), "Tim  1"));
		
		TeamsHandler handler = new TeamsHandler();
		
		ArrayList<Team> teams = handler.groupRunnersByTeam(runners);

		assertEquals(teams.size(), 4);

	}
	
	

}
