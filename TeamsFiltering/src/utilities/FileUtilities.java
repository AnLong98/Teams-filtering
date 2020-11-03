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
	
	
	public static ArrayList<Team> ParseCSVFile(File csvFile) throws FileNotFoundException, IllegalInputHeaderException
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
	}
	
	public static HashMap<DATA_FIELDS, String> getDataDictFromCSVFileLine(String[] line)
	{
		if(line.length == 0)
		{
			return null;
		}
		
		
		HashMap<DATA_FIELDS, String> dataMap= new HashMap<DATA_FIELDS, String>();
		
		dataMap.put(DATA_FIELDS.BIB, line[0].trim());
		dataMap.put(DATA_FIELDS.FIRSTNAME, line[1].trim());
		dataMap.put(DATA_FIELDS.LASTNAME, line[2].trim());
		dataMap.put(DATA_FIELDS.SEX, line[3].trim());
		dataMap.put(DATA_FIELDS.DOB, line[4].trim());
		dataMap.put(DATA_FIELDS.STATE, line[5].trim());
		dataMap.put(DATA_FIELDS.TEAMNAME, line[6].trim());
		dataMap.put(DATA_FIELDS.CHIPTIME,  line[7].trim());
		
		
		
		return dataMap;
	}
	
	public static Runner ParseRunnerFromDataDict(HashMap<DATA_FIELDS, String> dataDict)
	{
		SimpleDateFormat formatLonger = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatShorter = new SimpleDateFormat("yyyy");
		LocalTime localTime;
		String birthYear = "";
		String firstName = dataDict.get(DATA_FIELDS.FIRSTNAME);
		String lastName = dataDict.get(DATA_FIELDS.LASTNAME);
		String state = dataDict.get(DATA_FIELDS.STATE);
		String sex = dataDict.get(DATA_FIELDS.SEX).substring(0, 1);
		int bibNumber = Integer.parseInt(dataDict.get(DATA_FIELDS.BIB));
		
		try
		{
			Date date = formatLonger.parse(dataDict.get(DATA_FIELDS.DOB));
			birthYear = formatShorter.format(date);
		}
		catch(Exception e)
		{
			try
			{
				birthYear = dataDict.get(DATA_FIELDS.DOB);
			}
			catch(Exception ex)
			{
				//Totaly illegal format, something is wrong
				return null;
			}
		}

		try
		{
			localTime = LocalTime.parse(dataDict.get(DATA_FIELDS.CHIPTIME), DateTimeFormatter.ofPattern("H:mm:ss"));
		}
		catch(DateTimeParseException dtpe)
		{
			try
			{
				localTime = LocalTime.parse(dataDict.get(DATA_FIELDS.CHIPTIME), DateTimeFormatter.ofPattern("HH:mm:ss"));
			}
			catch(DateTimeParseException dtpe_2)
			{
				//This probably means he is unqualified
				return null;
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		Runner runner = new Runner(firstName,
				lastName,
				state,
				birthYear,
				bibNumber,
				sex,
				localTime);
		
		return runner;
	}
	
	public static boolean compareFileHeaders(String[] firstHeader, String[] secondHeader)
	{
		
		if (firstHeader.length != secondHeader.length)
			return false;
		
		for(int i = 0; i < firstHeader.length; i++)
		{
			//Remove nbsps and whitespaces cause they are a bitch
			String firstValue = firstHeader[i].trim().replaceAll("\u00A0", "");
			String secondValue = secondHeader[i].trim().replaceAll("\u00A0", "");
			if(!firstValue.equals(secondValue))
			{
				return false;
			}
		}
		
		return true;
	}
	

}
