package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import data.Runner;
import data.Team;
import exceptions.IllegalInputHeaderException;
import utilities.FileParser;
import utilities.RunnersSorter;
import utilities.TeamsHandler;
import utilities.TeamsImporter;
import utilities.TimeParser;

public class TeamsImporterTests {
	private TeamsImporter importer =  new TeamsImporter(new FileParser(new TimeParser()), new RunnersSorter(), new TeamsHandler());

	@Test
	public void parseCSVFile_fileWith44Teams_assertTeamCount()
	{
		try 
		{
			ArrayList<Team> returnedValue = importer.getTeamsFromFile(new File("dayumson.csv"));
			assertEquals(returnedValue.size(), 44);
		} 
		catch (IllegalInputHeaderException | IOException e) 
		{
			fail("File error");
		}
	}
	
	@Test
	public void parseCSVFile_teamWithOneQualifiedRunner_assertRunnerCount()
	{
		try 
		{
			ArrayList<Team> returnedValue = importer.getTeamsFromFile(new File("team_valid_and_invalid_runners.csv"));
			assertEquals(returnedValue.get(0).getTeamMembers().size(), 1);
		} 
		catch (IllegalInputHeaderException | IOException e) 
		{
			fail("File error");
		}
	}
	
	@Test
	public void parseCSVFile_teamWithOneQualifiedRunner_assertRunnerInfo()
	{
		Runner expectedRunner = new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "M", LocalTime.of(2, 20, 32), "DAYUMSON");
		
		try 
		{
			ArrayList<Team> returnedValue = importer.getTeamsFromFile(new File("utf8_multiple_runners.csv"));
			Runner returnedRunner = returnedValue.get(0).getTeamMembers().get(0);
			
			assertEquals(expectedRunner.getBib_number(), returnedRunner.getBib_number());
			assertEquals(expectedRunner.getFirstName(), returnedRunner.getFirstName());
			assertEquals(expectedRunner.getLastName(), returnedRunner.getLastName());
			assertEquals(expectedRunner.getYob(), returnedRunner.getYob());
			assertEquals(expectedRunner.getGender(), returnedRunner.getGender());
			assertEquals(expectedRunner.getState(), returnedRunner.getState());
			assertEquals(expectedRunner.getChipTime(), returnedRunner.getChipTime());
			assertEquals(expectedRunner.getTeamName(), returnedRunner.getTeamName());
		} 
		catch (IllegalInputHeaderException | IOException e) 
		{
			fail("File error");
		}
	}
	
}
