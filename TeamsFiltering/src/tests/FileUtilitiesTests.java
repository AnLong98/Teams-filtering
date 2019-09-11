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
import utilities.DataUtilities;
import utilities.FileUtilities;
import utilities.TeamsForTest;
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
		
		String[] dataLine = {"110",	"NataÅ¡a", "Naletina", "Female", "1983", "SRB", "adidas runners beograd", "3:20:52"};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(dataLine);
		
		assertEquals(returnValue.get(DATA_FIELDS.BIB), "110");
		assertEquals(returnValue.get(DATA_FIELDS.FIRSTNAME), "NataÅ¡a");
		assertEquals(returnValue.get(DATA_FIELDS.LASTNAME), "Naletina");
		assertEquals(returnValue.get(DATA_FIELDS.SEX), "Female");
		assertEquals(returnValue.get(DATA_FIELDS.DOB), "1983");
		assertEquals(returnValue.get(DATA_FIELDS.STATE), "SRB");
		assertEquals(returnValue.get(DATA_FIELDS.TEAMNAME), "adidas runners beograd");
		assertEquals(returnValue.get(DATA_FIELDS.CHIPTIME), "3:20:52");	
	}
	
	@Test
	public void Test__getDataDictFromCSVFileLine__empty_field__assert_content(){
		
		String[] dataLine = {"110",	"NataÅ¡a", "Naletina", "Female",	"1983",	"SRB", " ", "3:20:52"};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(dataLine);
		
		assertEquals(returnValue.get(DATA_FIELDS.BIB), "110");
		assertEquals(returnValue.get(DATA_FIELDS.FIRSTNAME), "NataÅ¡a");
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
		dataDict.put(DATA_FIELDS.FIRSTNAME, "NataÅ¡a");
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
	public void parseRunnerFromDataDict_hhmmssTimeFormat_assertContent()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "NataÅ¡a");
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
	public void parseRunnerFromDataDict_disqualifiedRunner_returnsNull()
	{
		HashMap<DATA_FIELDS, String> dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "NataÅ¡a");
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
	public void parseRunnerFromDataDict_ddmmyyyyDateFormat_parsesBirthYear()
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
	public void parseRunnerFromDataDict_yyyyDateFormat_parsesBirthYear()
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
	public void parseCSVFile_fileWith44Teams_assertTeamCount()
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
	public void parseCSVFile_teamWithOneQualifiedRunner_assertRunnerCount()
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
	public void parseCSVFile_teamWithOneQualifiedRunner_assertRunnerInfo()
	{
		Runner expectedRunner = new Runner("Predrag", "GlavaÅ¡", "SRB", "1998", 100, "Male", LocalTime.of(2, 20, 32));
		
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
	public void writeCSVFile_sortedTeams_returnsTrue()
	{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		Team team1 = TeamsForTest.createPedjaTeam();
		Team team2 = TeamsForTest.createEstradaTeam();
		Team team3 = TeamsForTest.createTrkaRsTeam();
		
		teams.add(team2);
		teams.add(team1);
		teams.add(team3);
		
		teams = DataUtilities.sortByTotalTime(teams);
		
		assertTrue(FileUtilities.writeCSVFile(teams, "test"));
	}
	
	@Test
	public void getCSVDataFromTeam_testTeam_assertContent()
	{
		
		Team team1 = TeamsForTest.createPedjaTeam();
		
		ArrayList<String[]> csvData = FileUtilities.getCSVDataFromTeam(team1);
		
		String[] receivedData  = csvData.get(0);
		String[] expectedData = {"1", "100","Predrag", "Glavaš", "M", "1998", "SRB", "Pedja team" , "02:20:32", "02:20:34", "09:22:14"};
		
		assertEquals(receivedData[0], expectedData[0]);
		assertEquals(receivedData[1], expectedData[1]);
		assertEquals(receivedData[2], expectedData[2]);
		assertEquals(receivedData[3], expectedData[3]);
		assertEquals(receivedData[4], expectedData[4]);
		assertEquals(receivedData[5], expectedData[5]);
		assertEquals(receivedData[6], expectedData[6]);
		assertEquals(receivedData[7], expectedData[7]);
		assertEquals(receivedData[8], expectedData[8]);
		assertEquals(receivedData[9], expectedData[9]);
		assertEquals(receivedData[10], expectedData[10]);
	}

}
