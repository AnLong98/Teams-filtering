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
		
		Runner expectedValue = new Runner("Nataša", "Naletina", "SRB", "1983", 110, "F", LocalTime.of(3, 20, 52));
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
		
		Runner expectedValue = new Runner("Nataša", "Naletina", "SRB", "1983", 110, "F", LocalTime.of(3, 20, 52));
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
	public void Test__ParseRunnerFromDataDict__ddmmyyyy_date_format__parses_birth_year()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "Nataša");
		dataDict.put(DATA_FIELDS.LASTNAME, "Naletina");
		dataDict.put(DATA_FIELDS.SEX, "Female");
		dataDict.put(DATA_FIELDS.DOB, "03/05/1998");
		dataDict.put(DATA_FIELDS.STATE, "SRB");
		dataDict.put(DATA_FIELDS.TEAMNAME, " ");
		dataDict.put(DATA_FIELDS.CHIPTIME, "03:20:52");	
		
		String expectedBirthYear = "1998";
		Runner returnedValue = FileUtilities.ParseRunnerFromDataDict(dataDict);
		String returnedYear = returnedValue.getYob();
		
		assertEquals(expectedBirthYear, returnedYear);
	}
	
	@Test
	public void Test__ParseRunnerFromDataDict__yyyy_date_format__parses_birth_year()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "Nataša");
		dataDict.put(DATA_FIELDS.LASTNAME, "Naletina");
		dataDict.put(DATA_FIELDS.SEX, "Female");
		dataDict.put(DATA_FIELDS.DOB, "1998");
		dataDict.put(DATA_FIELDS.STATE, "SRB");
		dataDict.put(DATA_FIELDS.TEAMNAME, " ");
		dataDict.put(DATA_FIELDS.CHIPTIME, "03:20:52");	
		
		String expectedBirthYear = "1998";
		Runner returnedValue = FileUtilities.ParseRunnerFromDataDict(dataDict);
		String returnedYear = returnedValue.getYob();
		
		assertEquals(expectedBirthYear, returnedYear);
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
		
		team1.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "M", LocalTime.of(2, 20, 32)));
		team1.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", "1997", 101, "M", LocalTime.of(2, 20, 33)));
		team1.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", "1996", 102, "M", LocalTime.of(2, 20, 34)));
		team1.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", "1995", 103, "M", LocalTime.of(2, 20, 35)));
		team1.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", "1994", 104, "M", LocalTime.of(2, 20, 36)));
		team1.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", "1993", 105, "M", LocalTime.of(2, 20, 37)));
		
		team1.TrimTeam(4);
		team1.calculateTeamTotalTime();
		team1.calculateTeamAverageTime();
		teams.add(team1);
		
		Team team2 = new Team("Feminisam");
		
		team2.AddRunnerToTeam(new Runner("Наташа", "Бекалац", "SRB", "1982", 200, "F", LocalTime.parse("02:40:30")));
		team2.AddRunnerToTeam(new Runner("Marina", "Višković", "SRB", "1988", 201, "F", LocalTime.parse("02:42:30")));
		team2.AddRunnerToTeam(new Runner("Fahreta", "Živojinović", "SRB", "1958", 202, "F", LocalTime.parse("02:44:30")));
		team2.AddRunnerToTeam(new Runner("Radojka", "Adžić", "SRB", "1977", 203, "F", LocalTime.parse("02:45:35")));
		team2.AddRunnerToTeam(new Runner("Marina", "Gavrić", "SRB", "1996", 204, "F", LocalTime.parse("02:46:36")));
		team2.AddRunnerToTeam(new Runner("Anastasija", "Rudan", "SRB", "1996", 205, "F", LocalTime.parse("02:48:37")));
		
		team2.TrimTeam(4);
		team2.calculateTeamTotalTime();
		team2.calculateTeamAverageTime();
		teams.add(team2);
		
		assertTrue(FileUtilities.writeCSVFile(teams, "test"));
	}
	
	@Test
	public void writeCSVFile_TestTeam_assertContent()
	{
		
		Team team1 = new Team("Test team");
		
		team1.AddRunnerToTeam(new Runner("Predrag", "Glavaš", "SRB", "1998", 100, "M", LocalTime.of(2, 20, 32)));
		team1.AddRunnerToTeam(new Runner("Predragica", "Glavašica", "SRB", "1997", 101, "M", LocalTime.of(2, 20, 33)));
		team1.AddRunnerToTeam(new Runner("Predragurda", "Glavašurda", "SRB", "1996", 102, "M", LocalTime.of(2, 20, 34)));
		team1.AddRunnerToTeam(new Runner("Predragetina", "Glavašetina", "SRB", "1995", 103, "M", LocalTime.of(2, 20, 35)));
		team1.AddRunnerToTeam(new Runner("Predrager", "Glavašer", "SRB", "1994", 104, "M", LocalTime.of(2, 20, 36)));
		team1.AddRunnerToTeam(new Runner("Predraga", "Glavaša", "SRB", "1993", 105, "M", LocalTime.of(2, 20, 37)));
		
		team1.TrimTeam(4);
		team1.calculateTeamTotalTime();
		team1.calculateTeamAverageTime();
		
		ArrayList<String[]> csvData = FileUtilities.getCSVDataFromTeam(team1);
		

		String[] receivedDataFirst  = csvData.get(0);
		String[] expectedDataFirst = {"1", "100","Predrag", "Glavaš", "M", "1998", "SRB", "Test team" , "02:20:32", "02:20:34", "09:22:14"};
		
		String[] receivedDataSecond  = csvData.get(1);
		String[] expectedDataSecond = {"", "101","Predragica", "Glavašica", "M", "1997", "SRB", "Test team" , "02:20:33", "", ""};
		
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
