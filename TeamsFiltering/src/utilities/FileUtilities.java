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
	
	
	/*public static ArrayList<Team> ParseCSVFile(File csvFile) throws FileNotFoundException, IllegalInputHeaderException
	{
		CSVReader reader = null;
		ArrayList<Team> teams = new ArrayList<Team>();
		
		//Check if file is empty
		if (csvFile.length() == 0)
		{
			return null;
		}
		
		FileInputStream fis = new FileInputStream(csvFile);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF8"));
        reader = new CSVReader(isr, ',');
		
            
        String [] nextLine;
         //Keep this here for now, we don't know if we will need it later
        String[] fileHeader;
        try 
        {
			fileHeader = reader.readNext();
			
            if(!compareFileHeaders(inputTeamsHeader, fileHeader) && !compareFileHeaders(inputTeamsCommaHeader, fileHeader))
            	throw new IllegalInputHeaderException(fileHeader);
            
            Team currentTeam = null;
          //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
            	HashMap<DATA_FIELDS, String> dataMap =  getDataDictFromCSVFileLine(nextLine);
            	String teamName = dataMap.get(DATA_FIELDS.TEAMNAME);
            	
            	//If runner has no team he is to be ignored
            	if(teamName.isEmpty() || teamName == null)
            	{
            		continue;
            	}
            	
            	Runner currentRunner = ParseRunnerFromDataDict(dataMap);
            	//This person is invalid we need to skip it
            	if(currentRunner == null)
            	{
            		continue;
            	}
            	
            	//We need to create a new team cause current is non existent
            	if(currentTeam == null)
            	{
            		currentTeam = new Team(teamName);
            		currentTeam.AddRunnerToTeam(currentRunner);
            	}
            	else if(teamName.equals(currentTeam.getTeamName()))
            	{
            		//If parsed runner is member of the same team as last parsed runner put him there
            		currentTeam.AddRunnerToTeam(currentRunner);	
            	}
            	else if( !teamName.equals(currentTeam.getTeamName()) )
            	{
            		//If we reached a new team then add old one to the list
            		teams.add(currentTeam);
            		currentTeam = new Team(teamName);
            		currentTeam.AddRunnerToTeam(currentRunner);
            	}	
            }
            //Add the last parsed team
            teams.add(currentTeam);
        } 
        catch (IOException e1) 
        {
			e1.printStackTrace();
			return null;
		}
        finally
        {
			try 
            {
                reader.close();
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
            
        return teams;
	}*/
	
	
	
	
	

}
