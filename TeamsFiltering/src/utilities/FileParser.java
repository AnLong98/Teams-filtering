package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;
import data.Runner;
import exceptions.IllegalInputHeaderException;
import utilities.FileUtilities.DATA_FIELDS;

public class FileParser {
	
	public ArrayList<Runner> readRunnersFromFile(File file) throws FileNotFoundException, IllegalInputHeaderException
	{
		ArrayList<Runner> runners = new ArrayList<Runner>();
		
		CSVReader reader = null;
		
		//Check if file is empty
		if (file.length() == 0)
		{
			return null;
		}
		
		FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF8"));
        reader = new CSVReader(isr, ',');
		
            
        String [] nextLine;
        String[] fileHeader;
        try 
        {
			fileHeader = reader.readNext();
			
            if(!compareFileHeaders(FileUtilities.inputTeamsHeader, fileHeader) && !compareFileHeaders(FileUtilities.inputTeamsCommaHeader, fileHeader))
            	throw new IllegalInputHeaderException(fileHeader);
            
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
            	HashMap<DATA_FIELDS, String> dataMap =  getDataDictFromCSVFileLine(nextLine);
            	Runner currentRunner = parseRunnerFromDataDict(dataMap);
            	//This person is invalid we need to skip it
            	if(currentRunner == null)
            	{
            		continue;
            	}
            	
            	runners.add(currentRunner);
        
            }
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
            
		return runners;
	}
	
	
	public boolean compareFileHeaders(String[] firstHeader, String[] secondHeader)
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
	
	
	public HashMap<DATA_FIELDS, String> getDataDictFromCSVFileLine(String[] line)
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
	
	
	public Runner parseRunnerFromDataDict(HashMap<DATA_FIELDS, String> dataDict)
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
		String teamName = dataDict.get(DATA_FIELDS.TEAMNAME);
		
		if(teamName.isEmpty() || teamName == null)
    	{
    		return null;
    	}
		
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
				//Totally illegal format, something is wrong
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
				localTime,
				teamName);
		
		return runner;
	}

}
