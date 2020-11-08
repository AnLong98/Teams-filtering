package utilities;

public class FileUtilities {
	public enum DATA_FIELDS { BIB, FIRSTNAME, LASTNAME, SEX, DOB, STATE, TEAMNAME, CHIPTIME };
	public static String[] outputCSVHeader = { "Place", "Bib #", "First Name", "Last Name", "Sex",
						"YoB", "Country", "Team Name", "Time", "Average Time", "Total Time" };
	
	public static String[] inputTeamsHeader = { "Bib #", "First Name", "Last Name", "Sex",
						"DOB", "State", "Team Name", "Chip Time" };
	
	public static String[] inputTeamsCommaHeader = { "Bib #", "First Name", "Last Name", "Sex",
						"DOB", "State", "Team Name", "Chip Time", "" };
	
	
}
