package tests;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;
import data.Runner;
import utilities.RunnersSorter;

public class RunnersSorterTest {
	
	@Test
	public void sortRunnersByTimeAscending_runnersList_assertSorted()
	{
		ArrayList<Runner> runners = new ArrayList<Runner>();
		
		runners.add(new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 300, "M", LocalTime.of(2, 3, 34), "Trka.rs team"));
		runners.add(new Runner("Ð‘Ð¾ÑˆÐºÐ¾", "Ð¡Ñ‚ÑƒÐ¿Ð°Ñ€", "SRB", "1985", 301, "M", LocalTime.of(2, 2, 33), "Trka.rs team"));
		runners.add(new Runner("Ð”ÑƒÑˆÐ°Ð½", "ÐœÐ°Ñ€Ñ˜Ð°Ð½Ñ�ÐºÐ¸", "SRB", "1996", 302, "M", LocalTime.of(2, 4, 35), "Trka.rs team"));
		runners.add(new Runner("Bojan", "Ä†ulibrk", "SRB", "1988", 303, "M", LocalTime.of(2, 2, 1), "Trka.rs team"));
		
		RunnersSorter sorter = new RunnersSorter();
		
		ArrayList<Runner> sortedRunners = sorter.sortRunnersByTimeAscending(runners);
		
		assertEquals(sortedRunners.get(0).getBib_number(), 303);
		assertEquals(sortedRunners.get(1).getBib_number(), 301);
		assertEquals(sortedRunners.get(2).getBib_number(), 300);
		assertEquals(sortedRunners.get(3).getBib_number(), 302);
	}
	
	@Test
	public void sortRunnersByTimeAscending_runnersListDescending_assertSorted()
	{
		ArrayList<Runner> runners = new ArrayList<Runner>();
		
		runners.add(new Runner("Ð”ÑƒÑˆÐ°Ð½", "ÐœÐ°Ñ€Ñ˜Ð°Ð½Ñ�ÐºÐ¸", "SRB", "1996", 302, "M", LocalTime.of(2, 4, 35), "Trka.rs team"));
		runners.add(new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 300, "M", LocalTime.of(2, 3, 34), "Trka.rs team"));
		runners.add(new Runner("Ð‘Ð¾ÑˆÐºÐ¾", "Ð¡Ñ‚ÑƒÐ¿Ð°Ñ€", "SRB", "1985", 301, "M", LocalTime.of(2, 2, 33), "Trka.rs team"));
		runners.add(new Runner("Bojan", "Ä†ulibrk", "SRB", "1988", 303, "M", LocalTime.of(2, 2, 1), "Trka.rs team"));
		runners.add(new Runner("Savo", "Izkikinde", "SRB", "1950", 304, "M", LocalTime.of(2, 2, 1), "Trka.rs team"));
		
		RunnersSorter sorter = new RunnersSorter();
		
		ArrayList<Runner> sortedRunners = sorter.sortRunnersByTimeAscending(runners);
		
		assertEquals(sortedRunners.get(0).getBib_number(), 303);
		assertEquals(sortedRunners.get(1).getBib_number(), 304);
		assertEquals(sortedRunners.get(2).getBib_number(), 301);
		assertEquals(sortedRunners.get(3).getBib_number(), 300);
		assertEquals(sortedRunners.get(4).getBib_number(), 302);
	}
	
	@Test
	public void sortRunnersByTimeAscending_runnersListWithMilliseconds_assertSorted()
	{
		ArrayList<Runner> runners = new ArrayList<Runner>();
		
		runners.add(new Runner("Ð”ÑƒÑˆÐ°Ð½", "ÐœÐ°Ñ€Ñ˜Ð°Ð½Ñ�ÐºÐ¸", "SRB", "1996", 302, "M", LocalTime.of(2, 2, 2, 1), "Trka.rs team"));
		runners.add(new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 300, "M", LocalTime.of(2, 2, 2, 5), "Trka.rs team"));
		runners.add(new Runner("Ð‘Ð¾ÑˆÐºÐ¾", "Ð¡Ñ‚ÑƒÐ¿Ð°Ñ€", "SRB", "1985", 301, "M", LocalTime.of(2, 2, 2, 3), "Trka.rs team"));
		runners.add(new Runner("Bojan", "Ä†ulibrk", "SRB", "1988", 303, "M", LocalTime.of(2, 2, 2, 2), "Trka.rs team"));
		runners.add(new Runner("Savo", "Izkikinde", "SRB", "1950", 304, "M", LocalTime.of(2, 2, 2, 4), "Trka.rs team"));
		
		RunnersSorter sorter = new RunnersSorter();
		
		ArrayList<Runner> sortedRunners = sorter.sortRunnersByTimeAscending(runners);
		
		assertEquals(sortedRunners.get(0).getBib_number(), 302);
		assertEquals(sortedRunners.get(1).getBib_number(), 303);
		assertEquals(sortedRunners.get(2).getBib_number(), 301);
		assertEquals(sortedRunners.get(3).getBib_number(), 304);
		assertEquals(sortedRunners.get(4).getBib_number(), 300);
	}

}
