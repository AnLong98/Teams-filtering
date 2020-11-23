package utilities;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;
import contracts.IFileParsing;
import contracts.ITimeParsing;
import data.Runner;
import exceptions.IllegalInputHeaderException;
import utilities.FileUtilities.DATA_FIELDS;

public class FileParser implements IFileParsing{
	private ITimeParsing timeParser;

	public FileParser(ITimeParsing timeParser) {
		super();
		this.timeParser = timeParser;
	}


	public ArrayList<Runner> readRunnersFromFile(File file) throws IllegalInputHeaderException, IOException
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

		fileHeader = reader.readNext();
		
        if(!compareFileHeaders(FileUtilities.inputTeamsHeader, fileHeader) && !compareFileHeaders(FileUtilities.inputTeamsCommaHeader, fileHeader))
        {
        	reader.close();
        	throw new IllegalInputHeaderException(fileHeader);

        }
        	
        
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
       

        reader.close();

            
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
		DateTimeFormatter formatLonger =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter reverseFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter reverseFormatDashed = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatShorter = new DateTimeFormatterBuilder()
			     .appendPattern("yyyy")
			     .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
			     .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
			     .toFormatter();
		ArrayList<DateTimeFormatter> dobFormats =  new ArrayList<DateTimeFormatter>(Arrays.asList(formatLonger, reverseFormat, reverseFormatDashed, formatShorter));

		LocalTime localTime;
		String birthYear = "";
		String sex;
		String firstName;
		String lastName;
		String state;
		int bibNumber;
		String teamName;
		
		try
		{		
			sex = dataDict.get(DATA_FIELDS.SEX).substring(0, 1);
			firstName = dataDict.get(DATA_FIELDS.FIRSTNAME);
			lastName = dataDict.get(DATA_FIELDS.LASTNAME);
			state = dataDict.get(DATA_FIELDS.STATE);
			bibNumber = Integer.parseInt(dataDict.get(DATA_FIELDS.BIB));
			teamName = dataDict.get(DATA_FIELDS.TEAMNAME);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		if(teamName.isEmpty() || teamName == null)
    	{
    		return null;
    	}
		
		for(DateTimeFormatter format : dobFormats)
		{
			try
			{
				LocalDate localDate = LocalDate.parse(dataDict.get(DATA_FIELDS.DOB), format);
				birthYear = String.valueOf(localDate.getYear()); //formatShorter.format(localDate);
				break;
			}catch(Exception ex)
			{
				continue;
			}
			
		}
		
		if(birthYear.isEmpty())return null;

		localTime = timeParser.parseChipTime(dataDict.get(DATA_FIELDS.CHIPTIME));		
		if(localTime == null)return null;
		if(Runner.getChipTimeFormat().isEmpty())
		{	
			Runner.setChipTimeFormat(timeParser.parseChipTimeFormat(dataDict.get(DATA_FIELDS.CHIPTIME)));//Znam da je lose, ali nisam smislio nista bolje a da ne refaktorisem dosta koda.
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
