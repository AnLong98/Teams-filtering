package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

public class FileUtilities {
	public enum DATA_FIELDS { BIB, FIRSTNAME, LASTNAME, SEX, DOB, STATE, TEAMNAME, CHIPTIME };
	public static String[] outputCSVHeader = { "Place", "Bib #", "First Name", "Last Name", "Sex",
						"YoB", "Country", "Team Name", "Time", "Average Time", "Total Time" };
	
	public static boolean writeCSVFile(ArrayList<Team> teams, String fileName)
	{	
		boolean ret = false;
		String csvFileName = fileName + ".csv";
		
        try 
        {
        	PrintWriter output = new PrintWriter(new File(csvFileName));
            CSVWriter csvWriter = new CSVWriter(output);

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
        			runner.getState(), team.getTeamName(), DataUtilities.formatCSVOutputTime(runner.getChipTime()),
        			avgTimeStr, totalTimeStr };
        	
        	csvTeamData.add(csvString);	
        }
        
        return csvTeamData;
	}
	
	
	public static ArrayList<Team> ParseCSVFile(File csvFile) throws FileNotFoundException
	{
		CSVReader reader = null;
		ArrayList<Team> teams = new ArrayList<Team>();
		
		//Check if file is empty
		if (csvFile.length() == 0)
		{
			return null;
		}
		
        try
        {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(csvFile),',');
            
            String [] nextLine;
            //Keep this here for now, we don't know if we will need it later
            String[] fileHeader = reader.readNext();
            //Read one line at a time
            
            Team currentTeam = null;
            while ((nextLine = reader.readNext()) != null)
            {
            	HashMap<DATA_FIELDS, String> dataMap =  getDataDictFromCSVFileLine(nextLine);
            	String teamName = dataMap.get(DATA_FIELDS.TEAMNAME);
            	
            	//If runner has no team he is to be ignored
            	if(teamName.isEmpty())
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
        catch (Exception e) 
        {
            e.printStackTrace();
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
		dataMap.put(DATA_FIELDS.BIB, line[0]);
		dataMap.put(DATA_FIELDS.FIRSTNAME, line[1]);
		dataMap.put(DATA_FIELDS.LASTNAME, line[2]);
		dataMap.put(DATA_FIELDS.SEX, line[3]);
		dataMap.put(DATA_FIELDS.DOB, line[4]);
		dataMap.put(DATA_FIELDS.STATE, line[5]);
		dataMap.put(DATA_FIELDS.TEAMNAME, line[6]);
		dataMap.put(DATA_FIELDS.CHIPTIME, line[7]);
		
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
		}catch(Exception e)
		{
			try
			{
				birthYear = dataDict.get(DATA_FIELDS.DOB);

			}catch(Exception ex)
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
	
	/*static ArrayList<Team> ParseFile(File xslxFile)
	{
		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		ArrayList<Team>teams = new ArrayList<Team>();
		
		try {
			file = new FileInputStream(xslxFile);
		} catch (FileNotFoundException e) {
			// TODO: Dodati ovde poruku
			e.printStackTrace();
		}
		 
        //Create Workbook instance holding reference to .xlsx file
       
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
			// TODO I ovde treba poruka
			e.printStackTrace();
		}

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        
		//Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            
            String firstName, lastName, state;
        	int bib_number;
        	char gender;
        	LocalTime chipTime;
            
            int cell_index = 0;
             
            while (cellIterator.hasNext())
            {
            	Cell cell = cellIterator.next();
            	
            	switch(cell_index)
                {
               	   case 0:
               		   if (cell.getCellType() != CellType.NUMERIC)
               		   {
               			  
               		   }
               		 
               		 
                }
            	
                //Check the cell type and format accordingly
                switch (cell.getCellType())
                {
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "t");
                        break;
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "t");
                        break;
                }
            }
            System.out.println("");
        }
        try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return teams;
	}
	
	private static Team ParseDataFromFileRow(Row row)
	{
		Iterator<Cell> cellIterator = row.cellIterator();
        
        String firstName, lastName, state, teamName, unparsedTime;
    	int bibNumber;
    	char gender;
    	LocalTime chipTime;
    	
        
        int cell_index = 0;
         
        while (cellIterator.hasNext())
        {
        	Cell cell = cellIterator.next();
        	
        	switch(cell_index)
            {
           	   case 0:
           		   if (cell.getCellType() != CellType.NUMERIC)
           		   {
           			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
           		   }else
           		   {
           			  bibNumber = (int) cell.getNumericCellValue();
           		   }
           		   break;
           		   
           	   case 1:
        		   if (cell.getCellType() != CellType.STRING)
        		   {
        			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
        		   }else
        		   {
        			  lastName = cell.getStringCellValue();
        		   }
        		   break;
        		   
	           	case 2:
	     		   if (cell.getCellType() != CellType.STRING)
	     		   {
	     			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
	     		   }else
	     		   {
	     			  firstName = cell.getStringCellValue();
	     		   }
	     		   break;
	     		   
	     		   
	           	case 3:
		     		   if (cell.getCellType() != CellType.STRING)
		     		   {
		     			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
		     		   }else
		     		   {
		     			  gender = cell.getStringCellValue().charAt(0);
		     		   }	
		     		   break;
		     		   
	           	case 4:
		     		   if (cell.getCellType() != CellType.NUMERIC)
		     		   {
		     			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
		     		   }
		     		   
	           	case 5:
		     		   if (cell.getCellType() != CellType.STRING)
		     		   {
		     			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
		     		   }else
		     		   {
		     			   state = cell.getStringCellValue();
		     		   }
	           	case 6:
		     		   if (cell.getCellType() != CellType.STRING)
		     		   {
		     			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
		     		   }else
		     		   {
		     			   teamName = cell.getStringCellValue();
		     		   }
	           	
	           	case 7:
		     		   if (cell.getCellType() != CellType.STRING)
		     		   {
		     			  throw new IllegalArgumentException("Illegal argument for cell index " + cell_index);
		     		   }else
		     		   {
		     			   unparsedTime = cell.getStringCellValue();
		     		   }
	     		   
	     		   
           		 
           		 
            }
        	
        }
	}*/

}
