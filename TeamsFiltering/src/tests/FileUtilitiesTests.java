package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import data.Runner;
import data.Team;
import utilities.FileUtilities;
import utilities.FileUtilities.DATA_FIELDS;

public class FileUtilitiesTests {

	@Test
	public void Test__getDataDictFromCSVFileLine__empty_line_returns_null() {
		
		String[] emptyLine = {};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(emptyLine);
		
		assertNull(returnValue);
	}
	
	@Test
	public void Test__getDataDictFromCSVFileLine__assert_content() {
		
		String[] dataLine = {"110",	"Nataša", "Naletina", "Female",	"1983",	"SRB", "adidas runners beograd", "3:20:52"};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(dataLine);
		
		assertEquals(returnValue.get(DATA_FIELDS.BIB), "110");
		assertEquals(returnValue.get(DATA_FIELDS.FIRSTNAME), "Nataša");
		assertEquals(returnValue.get(DATA_FIELDS.LASTNAME), "Naletina");
		assertEquals(returnValue.get(DATA_FIELDS.SEX), "Female");
		assertEquals(returnValue.get(DATA_FIELDS.DOB), "1983");
		assertEquals(returnValue.get(DATA_FIELDS.STATE), "SRB");
		assertEquals(returnValue.get(DATA_FIELDS.TEAMNAME), "adidas runners beograd");
		assertEquals(returnValue.get(DATA_FIELDS.CHIPTIME), "3:20:52");	
	}
	
	@Test
	public void Test__getDataDictFromCSVFileLine__empty_field__assert_content(){
		
		String[] dataLine = {"110",	"Nataša", "Naletina", "Female",	"1983",	"SRB", " ", "3:20:52"};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(dataLine);
		
		assertEquals(returnValue.get(DATA_FIELDS.BIB), "110");
		assertEquals(returnValue.get(DATA_FIELDS.FIRSTNAME), "Nataša");
		assertEquals(returnValue.get(DATA_FIELDS.LASTNAME), "Naletina");
		assertEquals(returnValue.get(DATA_FIELDS.SEX), "Female");
		assertEquals(returnValue.get(DATA_FIELDS.DOB), "1983");
		assertEquals(returnValue.get(DATA_FIELDS.STATE), "SRB");
		assertEquals(returnValue.get(DATA_FIELDS.TEAMNAME), " ");
		assertEquals(returnValue.get(DATA_FIELDS.CHIPTIME), "3:20:52");	
	}
	
	@Test
	public void Test__ParseRunnerFromDataDict__assert_content()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.LASTNAME, "Naletina");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "Nataša");
		dataDict.put(DATA_FIELDS.SEX, "Female");
		dataDict.put(DATA_FIELDS.DOB, "1983");
		dataDict.put(DATA_FIELDS.STATE, "SRB");
		dataDict.put(DATA_FIELDS.TEAMNAME, " ");
		dataDict.put(DATA_FIELDS.CHIPTIME, "3:20:52");	
		
		Runner expectedValue = new Runner("Nataša", "Naletina", "SRB", "1983", 110, "Female", LocalTime.of(3, 20, 52));
		Runner returnedValue = FileUtilities.ParseRunnerFromDataDict(dataDict);
		
		assertEquals(expectedValue.getBib_number(), returnedValue.getBib_number());
		assertEquals(expectedValue.getFirstName(), returnedValue.getFirstName());
		assertEquals(expectedValue.getLastName(), returnedValue.getLastName());
		assertEquals(expectedValue.getYob(), returnedValue.getYob());
		assertEquals(expectedValue.getChipTime(), returnedValue.getChipTime());
		assertEquals(expectedValue.getGender(), returnedValue.getGender());
		assertEquals(expectedValue.getState(), returnedValue.getState());
	}
	
	@Test
	public void Test__ParseRunnerFromDataDict__hh_mm_ss_time_format__assert_content()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "Nataša");
		dataDict.put(DATA_FIELDS.LASTNAME, "Naletina");
		dataDict.put(DATA_FIELDS.SEX, "Female");
		dataDict.put(DATA_FIELDS.DOB, "1983");
		dataDict.put(DATA_FIELDS.STATE, "SRB");
		dataDict.put(DATA_FIELDS.TEAMNAME, " ");
		dataDict.put(DATA_FIELDS.CHIPTIME, "03:20:52");	
		
		Runner expectedValue = new Runner("Nataša", "Naletina", "SRB", "1983", 110, "Female", LocalTime.of(3, 20, 52));
		Runner returnedValue = FileUtilities.ParseRunnerFromDataDict(dataDict);
		
		assertEquals(expectedValue.getBib_number(), returnedValue.getBib_number());
		assertEquals(expectedValue.getFirstName(), returnedValue.getFirstName());
		assertEquals(expectedValue.getLastName(), returnedValue.getLastName());
		assertEquals(expectedValue.getYob(), returnedValue.getYob());
		assertEquals(expectedValue.getChipTime(), returnedValue.getChipTime());
		assertEquals(expectedValue.getGender(), returnedValue.getGender());
		assertEquals(expectedValue.getState(), returnedValue.getState());
	}
	
	@Test
	public void Test__ParseRunnerFromDataDict__disqualified_runner__returns_null()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "Nataša");
		dataDict.put(DATA_FIELDS.LASTNAME, "Naletina");
		dataDict.put(DATA_FIELDS.SEX, "Female");
		dataDict.put(DATA_FIELDS.DOB, "1983");
		dataDict.put(DATA_FIELDS.STATE, "SRB");
		dataDict.put(DATA_FIELDS.TEAMNAME, " ");
		dataDict.put(DATA_FIELDS.CHIPTIME, "DNF");	
		
		Runner expectedValue = null;
		Runner returnedValue = FileUtilities.ParseRunnerFromDataDict(dataDict);
		
		assertEquals(expectedValue, returnedValue);
	}
	
	@Test
	public void Test__ParseCSVFile__file_with_44_teams__assert_team_count()
	{
		try 
		{
			ArrayList<Team> returnedValue = FileUtilities.ParseCSVFile(new File("dayumson.csv"));
			assertEquals(returnedValue.size(), 44);
		} 
		catch (FileNotFoundException e) 
		{
			fail("File was not found");
		}
	}
	
	@Test
	public void Test__ParseCSVFile__team_with_one_qualified_runner__assert_runner_count()
	{
		try 
		{
			ArrayList<Team> returnedValue = FileUtilities.ParseCSVFile(new File("team_valid_and_invalid_runners.csv"));
			assertEquals(returnedValue.get(0).getTeamMembers().size(), 1);
		} 
		catch (FileNotFoundException e) 
		{
			fail("File was not found");
		}
	}
	
	@Test
	public void Test__ParseCSVFile__team_with_one_qualified_runner__assert_runner_info()
	{
		Runner expectedRunner = new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32));
		
		try 
		{
			ArrayList<Team> returnedValue = FileUtilities.ParseCSVFile(new File("utf8_multiple_runners.csv"));
			Runner returnedRunner = returnedValue.get(0).getTeamMembers().get(0);
			
			assertEquals(expectedRunner.getBib_number(), returnedRunner.getBib_number());
			assertEquals(expectedRunner.getFirstName(), returnedRunner.getFirstName());
			assertEquals(expectedRunner.getLastName(), returnedRunner.getLastName());
			assertEquals(expectedRunner.getYob(), returnedRunner.getYob());
			assertEquals(expectedRunner.getGender(), returnedRunner.getGender());
			assertEquals(expectedRunner.getState(), returnedRunner.getState());
			assertEquals(expectedRunner.getChipTime(), returnedRunner.getChipTime());
		} 
		catch (FileNotFoundException e) 
		{
			fail("File was not found");
		}
	}
	
	@Test
	public void writeCSVFile_nesto_returnsTrue()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = new Team("Test team");
		
		team1.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32)));
		team1.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", "1997", 101, "Male", LocalTime.of(2, 20, 33)));
		team1.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", "1996", 102, "Male", LocalTime.of(2, 20, 34)));
		team1.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", "1995", 103, "Male", LocalTime.of(2, 20, 35)));
		team1.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", "1994", 104, "Male", LocalTime.of(2, 20, 36)));
		team1.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", "1993", 105, "Male", LocalTime.of(2, 20, 37)));
		
		team1.TrimTeam(4);
		team1.calculateTeamTotalTime();
		team1.calculateTeamAverageTime();
		teams.add(team1);
		
		Team team2 = new Team("Feminisam");
		
		team2.AddRunnerToTeam(new Runner("Наташа", "Бекалац", "SRB", "1982", 200, "Female", LocalTime.parse("02:40:30")));
		team2.AddRunnerToTeam(new Runner("Marina", "Višković", "SRB", "1988", 201, "Female", LocalTime.parse("02:42:30")));
		team2.AddRunnerToTeam(new Runner("Fahreta", "Živojinović", "SRB", "1958", 202, "Female", LocalTime.parse("02:44:30")));
		team2.AddRunnerToTeam(new Runner("Radojka", "Adžić", "SRB", "1977", 203, "Female", LocalTime.parse("02:45:35")));
		team2.AddRunnerToTeam(new Runner("Marina", "Gavrić", "SRB", "1996", 204, "Female", LocalTime.parse("02:46:36")));
		team2.AddRunnerToTeam(new Runner("Anastasija", "Rudan", "SRB", "1996", 205, "Female", LocalTime.parse("02:48:37")));
		
		team2.TrimTeam(4);
		team2.calculateTeamTotalTime();
		team2.calculateTeamAverageTime();
		teams.add(team2);
		
		assertTrue(FileUtilities.writeCSVFile(teams, "test"));
	}

}
