package tests;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import data.Runner;
import utilities.FileParser;
import utilities.FileUtilities;
import utilities.FileUtilities.DATA_FIELDS;

public class FileParserTests {
	private FileParser parser =  new FileParser();
	private static HashMap<DATA_FIELDS, String> dataDict;
	
	@BeforeClass
	public static void setUp() {
		dataDict = new HashMap<DATA_FIELDS, String>();
		
		dataDict.put(DATA_FIELDS.BIB, "110");
		dataDict.put(DATA_FIELDS.LASTNAME, "Naletina");
		dataDict.put(DATA_FIELDS.FIRSTNAME, "Nataša");
		dataDict.put(DATA_FIELDS.SEX, "Female");
		dataDict.put(DATA_FIELDS.DOB, "1983");
		dataDict.put(DATA_FIELDS.STATE, "SRB");
		dataDict.put(DATA_FIELDS.TEAMNAME, "Test");
		dataDict.put(DATA_FIELDS.CHIPTIME, "3:20:52");	    
    }
	
	@Test
	public void getDataDictFromCSVFileLine_emptyLine_returnsNull() {
		
		String[] emptyLine = {};
		
		HashMap<DATA_FIELDS, String> returnValue = parser.getDataDictFromCSVFileLine(emptyLine);
		
		assertNull(returnValue);
	}
	
	@Test
	public void getDataDictFromCSVFileLine_content_assertContent() {
		
		String[] dataLine = {"110",	"NataÅ¡a", "Naletina", "Female", "1983", "SRB", "adidas runners beograd", "3:20:52"};
		
		HashMap<DATA_FIELDS, String> returnValue =parser.getDataDictFromCSVFileLine(dataLine);
		
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
	public void getDataDictFromCSVFileLine_leadingAndTrailingWhitespacesInTeamName_assertContent() {
		
		String[] dataLine = {" 110", "NataÅ¡a", "Naletina", "Female", "1983", "SRB", "  adidas runners beograd  ", "3:20:52"};
		
		
		HashMap<DATA_FIELDS, String> returnValue =parser.getDataDictFromCSVFileLine(dataLine);
		
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
		
		String[] dataLine = {" 110", "NataÅ¡a", "Naletina", "Female", "1983", "SRB", "  adidas runners beograd", " 3:20:52"};
		
		
		HashMap<DATA_FIELDS, String> returnValue =parser.getDataDictFromCSVFileLine(dataLine);
		
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
		
		
		HashMap<DATA_FIELDS, String> returnValue =parser.getDataDictFromCSVFileLine(dataLine);
		
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
		dataDict.replace(DATA_FIELDS.CHIPTIME, "3:20:52");	
		dataDict.replace(DATA_FIELDS.DOB, "1983");
		
		Runner expectedValue = new Runner("Nataša", "Naletina", "SRB", "1983", 110, "F", LocalTime.of(3, 20, 52), "Test");
		Runner returnedValue = parser.parseRunnerFromDataDict(dataDict);
		
		assertEquals(expectedValue.getBib_number(), returnedValue.getBib_number());
		assertEquals(expectedValue.getFirstName(), returnedValue.getFirstName());
		assertEquals(expectedValue.getLastName(), returnedValue.getLastName());
		assertEquals(expectedValue.getYob(), returnedValue.getYob());
		assertEquals(expectedValue.getChipTime(), returnedValue.getChipTime());
		assertEquals(expectedValue.getGender(), returnedValue.getGender());
		assertEquals(expectedValue.getState(), returnedValue.getState());
		assertEquals(expectedValue.getTeamName(), returnedValue.getTeamName());
	}
	
	@Test
	public void parseRunnerFromDataDict_hhmmssTimeFormat_assertContent()
	{

		dataDict.replace(DATA_FIELDS.CHIPTIME, "03:20:52");
		dataDict.replace(DATA_FIELDS.DOB, "1983");
		
		Runner expectedValue = new Runner("Nataša", "Naletina", "SRB", "1983", 110, "F", LocalTime.of(3, 20, 52), "Test");
		Runner returnedValue = parser.parseRunnerFromDataDict(dataDict);
		
		
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
		dataDict.replace(DATA_FIELDS.CHIPTIME, "DNF");
		
		Runner expectedValue = null;
		Runner returnedValue = parser.parseRunnerFromDataDict(dataDict);
		
		
		assertEquals(expectedValue, returnedValue);
	}
	
	@Test
	public void parseRunnerFromDataDict_ddmmyyyyDateFormat_parsesBirthYear()
	{
		dataDict.replace(DATA_FIELDS.DOB, "03/05/1998");
		dataDict.replace(DATA_FIELDS.CHIPTIME, "03:20:52");
		
		String expectedBirthYear = "1998";
		Runner returnedValue = parser.parseRunnerFromDataDict(dataDict);
		
		String returnedYear = returnedValue.getYob();
		
		assertEquals(expectedBirthYear, returnedYear);
	}
	
	@Test
	public void parseRunnerFromDataDict_yyyyDateFormat_parsesBirthYear()
	{
		dataDict.replace(DATA_FIELDS.DOB, "1998");
		dataDict.replace(DATA_FIELDS.CHIPTIME, "03:20:52");
		
		String expectedBirthYear = "1998";
		Runner returnedValue = parser.parseRunnerFromDataDict(dataDict);
		String returnedYear = returnedValue.getYob();
		
		assertEquals(expectedBirthYear, returnedYear);
	}
	
	@Test
	public void parseRunnerFromDataDict_noChipTime_returnsNull()
	{
		dataDict.replace(DATA_FIELDS.CHIPTIME, " ");
		
		Runner expectedValue = null;
		Runner returnedValue = parser.parseRunnerFromDataDict(dataDict);
		
		
		assertEquals(expectedValue, returnedValue);
	}
	
	@Test
	public void compareFileHeaders_inputTeamsHeader_returnsTrue()
	{
		String[] firstHeader = { "Bib #", "First Name", "Last Name", "Sex", "DOB", "State", "Team Name", "Chip Time" };
		
		assertTrue(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_notSupportedInputTeamsHeader_returnsFalse()
	{
		String[] firstHeader = { "Bib #", "Last Name", "First Name", "Sex", "DOB", "State", "Team Name", "Chip Time" };

		assertFalse(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsHeaderWithWhitespaces_returnsTrue()
	{
		String[] firstHeader = { " Bib #", " First Name", " Last Name", " Sex", " DOB", " State", " Team Name", " Chip Time" };
		
		assertTrue(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsHeaderWithNBSPS_returnsTrue()
	{
		String[] firstHeader = { "\u00A0Bib #" , "\u00A0First Name" , "\u00A0Last Name" , "\u00A0Sex" ,"\u00A0DOB" , "\u00A0State" , "\u00A0Team Name", "\u00A0Chip Time" };

		assertTrue(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsCommaHeader_returnsTrue()
	{
		String[] firstHeader = { "Bib #", "First Name", "Last Name", "Sex", "DOB", "State", "Team Name", "Chip Time", "" };

		assertTrue(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsCommaHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsCommaHeaderWithWhitespaces_returnsTrue()
	{
		String[] firstHeader = { " Bib #" , " First Name" , " Last Name" , " Sex" ," DOB" , " State" , " Team Name", " Chip Time", " " };

		assertTrue(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsCommaHeader));
	}
	
	@Test
	public void compareFileHeaders_inputTeamsCommaHeaderWithNBSPS_returnsTrue()
	{
		String[] firstHeader = { "\u00A0Bib #" , "\u00A0First Name" , "\u00A0Last Name" , "\u00A0Sex" ,"\u00A0DOB" , "\u00A0State" , "\u00A0Team Name", "\u00A0Chip Time", "\u00A0" };

		assertTrue(parser.compareFileHeaders(firstHeader, FileUtilities.inputTeamsCommaHeader));
	}

}
