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
import exceptions.IllegalInputHeaderException;
import utilities.DataUtilities;
import utilities.FileUtilities;
import utilities.TeamsForTest;
import utilities.FileUtilities.DATA_FIELDS;

public class FileUtilitiesTests {

	@Test
	public void getDataDictFromCSVFileLine_emptyLine_returnsNull() {
		
		String[] emptyLine = {};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(emptyLine);
		
		assertNull(returnValue);
	}
	
	@Test
	public void getDataDictFromCSVFileLine_content_assertContent() {
		
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
	public void getDataDictFromCSVFileLine_leadingWhitespaces_assertContent() {
		
		String[] dataLine = {" 110", "NataÅ¡a", "Naletina", "Female", "1983", "SRB", "adidas runners beograd", " 3:20:52"};
		
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
	public void getDataDictFromCSVFileLine_emptyField_assertContent(){
		
		String[] dataLine = {"110",	"NataÅ¡a", "Naletina", "Female",	"1983",	"SRB", " ", "3:20:52"};
		
		HashMap<DATA_FIELDS, String> returnValue = FileUtilities.getDataDictFromCSVFileLine(dataLine);
		
		assertEquals(returnValue.get(DATA_FIELDS.BIB), "110");
		assertEquals(returnValue.get(DATA_FIELDS.FIRSTNAME), "NataÅ¡a");
		assertEquals(returnValue.get(DATA_FIELDS.LASTNAME), "Naletina");
		assertEquals(returnValue.get(DATA_FIELDS.SEX), "Female");
		assertEquals(returnValue.get(DATA_FIELDS.DOB), "1983");
		assertEquals(returnValue.get(DATA_FIELDS.STATE), "SRB");
		assertEquals(returnValue.get(DATA_FIELDS.TEAMNAME), "");
		assertEquals(returnValue.get(DATA_FIELDS.CHIPTIME), "3:20:52");	
	}
	
	@Test
	public void parseRunnerFromDataDict_runner_assertContent()
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
		catch (FileNotFoundException | IllegalInputHeaderException e) 
		{
			fail("File error");
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
		catch (FileNotFoundException | IllegalInputHeaderException e) 
		{
			fail("File error");
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
		catch (FileNotFoundException | IllegalInputHeaderException e) 
		{
			fail("File error");
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
		int place = 1;
		
		ArrayList<String[]> csvData = FileUtilities.getCSVDataFromTeam(team1, place);
		
		String[] receivedDataFirst  = csvData.get(0);
		String[] expectedDataFirst = {"1", "100","Predrag", "Glava�", "M", "1998", "SRB", "Pedja team" , "02:20:32", "02:20:34", "09:22:14"};
		
		String[] receivedDataSecond  = csvData.get(1);
		String[] expectedDataSecond = {"", "101","Predragica", "Glava�ica", "M", "1997", "SRB", "Pedja team" , "02:20:33", "", ""};
		
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
	
	@Test
	public void compareFileHeaders_inputTeamsHeader_returnsTrue()
	{
		String[] firstHeader = { "Bib #", "First Name", "Last Name", "Sex", "DOB", "State", "Team Name", "Chip Time" };
		
		assertTrue(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_notSupportedInputTeamsHeader_returnsFalse()
	{
		String[] firstHeader = { "Bib #", "Last Name", "First Name", "Sex", "DOB", "State", "Team Name", "Chip Time" };
		
		assertFalse(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsHeaderWithWhitespaces_returnsTrue()
	{
		String[] firstHeader = { " Bib #", " First Name", " Last Name", " Sex", " DOB", " State", " Team Name", " Chip Time" };
		
		assertTrue(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsHeaderWithNBSPS_returnsTrue()
	{
		String[] firstHeader = { "\u00A0Bib #" , "\u00A0First Name" , "\u00A0Last Name" , "\u00A0Sex" ,"\u00A0DOB" , "\u00A0State" , "\u00A0Team Name", "\u00A0Chip Time" };
		
		assertTrue(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsCommaHeader_returnsTrue()
	{
		String[] firstHeader = { "Bib #", "First Name", "Last Name", "Sex", "DOB", "State", "Team Name", "Chip Time", "" };
		
		assertTrue(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsCommaHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsCommaHeaderWithWhitespaces_returnsTrue()
	{
		String[] firstHeader = { " Bib #" , " First Name" , " Last Name" , " Sex" ," DOB" , " State" , " Team Name", " Chip Time", " " };
		
		assertTrue(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsCommaHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsCommaHeaderWithNBSPS_returnsTrue()
	{
		String[] firstHeader = { "\u00A0Bib #" , "\u00A0First Name" , "\u00A0Last Name" , "\u00A0Sex" ,"\u00A0DOB" , "\u00A0State" , "\u00A0Team Name", "\u00A0Chip Time", "\u00A0" };
		
		assertTrue(FileUtilities.compareFileHeaders(firstHeader, FileUtilities.inputTeamsCommaHeader));
	}

}
