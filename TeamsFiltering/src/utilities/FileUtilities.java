package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import data.Runner;
import data.Team;
import exceptions.IllegalInputHeaderException;

public class FileUtilities {
	public enum DATA_FIELDS { BIB, FIRSTNAME, LASTNAME, SEX, DOB, STATE, TEAMNAME, CHIPTIME };
	public static String[] outputCSVHeader = { "Place", "Bib #", "First Name", "Last Name", "Sex",
						"YoB", "Country", "Team Name", "Time", "Average Time", "Total Time" };
	
	public static String[] inputTeamsHeader = { "Bib #", "First Name", "Last Name", "Sex",
						"DOB", "State", "Team Name", "Chip Time" };
	
	public static String[] inputTeamsCommaHeader = { "Bib #", "First Name", "Last Name", "Sex",
						"DOB", "State", "Team Name", "Chip Time", "" };
	
	public static boolean writeCSVFile(ArrayList<Team> teams, String fileName)
	{	
		boolean ret = false;
		String csvFileName = fileName + ".csv";

		
        try 
        {
        	FileOutputStream fos = new FileOutputStream(csvFileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("UTF8"));

            CSVWriter csvWriter = new CSVWriter(osw);


            // Header column value
            csvWriter.writeNext(outputCSVHeader);           
            
            int place = 0;
            for(Team team : teams)
            {
            	place++;
            	for(String[] csvRowData : getCSVDataFromTeam(team, place))
            	{
            		csvWriter.writeNext(csvRowData);
            	}
            }
            
            csvWriter.close();
            
            ret = true;
        } 
        catch (Exception e) 
        {
            // TODO: handle exception
            e.printStackTrace();
		}
		
		return ret;
	}
	
	public static ArrayList<String[]> getCSVDataFromTeam(Team team, int place)
	{
		ArrayList<String[]> csvTeamData= new ArrayList<String[]>();	
        boolean firstRunnerInTeam = true;
    	
        String placeStr = String.valueOf(place);
    	String avgTimeStr = DataUtilities.formatCSVOutputTime(team.getAverageTime());
    	String totalTimeStr = DataUtilities.formatCSVOutputTime(team.getTotalTime());
        
        for(Runner runner: team.getTeamMembers())
        {
        	if (!firstRunnerInTeam)
        	{
        		placeStr = "";
        		avgTimeStr = "";
        		totalTimeStr = "";
        	}
        	else
        	{	
        		firstRunnerInTeam = false;
        	}
        	
        	String[] csvString = { placeStr, String.valueOf(runner.getBib_number()),
        			runner.getFirstName(), runner.getLastName(), runner.getGender(), runner.getYob(), 
        			runner.getState(), team.getTeamName(), DataUtilities.formatCSVOutputTime(Duration.between(LocalTime.MIDNIGHT, runner.getChipTime())),
        			avgTimeStr, totalTimeStr };
        	
        	csvTeamData.add(csvString);	
        }
        
        return csvTeamData;
	}
	
	
	public static ArrayList<Team> getTeamsFromFile(File csvFile) throws FileNotFoundException, IllegalInputHeaderException
	{
		FileParser parser = new FileParser();
		RunnersSorter sorter = new RunnersSorter();
		TeamsHandler handler =  new TeamsHandler();	

		ArrayList<Runner> runners =  new ArrayList<Runner>();
		
		runners = parser.readRunnersFromFile(csvFile);
		runners = sorter.sortRunnersByTimeAscending(runners);
		
		return handler.groupRunnersByTeam(runners);
	}
}
