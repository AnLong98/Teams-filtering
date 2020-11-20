package utilities;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;
import contracts.ITeamsExporting;
import contracts.ITimeFormatting;
import data.Runner;
import data.Team;

public class TeamExporter implements ITeamsExporting{
	private ITimeFormatting timeFormatter;
	
	public TeamExporter(ITimeFormatting timeFormatter) {
		super();
		this.timeFormatter = timeFormatter;
	}

	@Override
	public boolean exportTeamsToCSVFile(ArrayList<Team> teams, String fileName) {
		boolean ret = false;
		String csvFileName = fileName + ".csv";

		
        try 
        {
        	FileOutputStream fos = new FileOutputStream(csvFileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("UTF8"));

            CSVWriter csvWriter = new CSVWriter(osw);


            // Header column value
            csvWriter.writeNext(FileUtilities.outputCSVHeader);           
            
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
	
	public ArrayList<String[]> getCSVDataFromTeam(Team team, int place)
	{
		String timeFormat = Runner.getChipTimeFormat();
		int decimalsToHave = 0;
		if(timeFormat.endsWith("SSS"))
		{
			decimalsToHave = 3;
			
		}else if(timeFormat.endsWith("SS"))
		{
			decimalsToHave = 2;
			
		}else if(timeFormat.endsWith("S"))
		{
			decimalsToHave = 1;
		}
		ArrayList<String[]> csvTeamData= new ArrayList<String[]>();	
        boolean firstRunnerInTeam = true;
    	
        Duration avgTime = timeFormatter.roundTime(team.getAverageTime(), decimalsToHave);
        String placeStr = String.valueOf(place);
    	String avgTimeStr = timeFormatter.formatCSVOutputTime(avgTime, timeFormat);
    	String totalTimeStr = timeFormatter.formatCSVOutputTime(team.getTotalTime(), timeFormat);
        
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
        			runner.getState(), team.getTeamName(), timeFormatter.formatCSVOutputTime(Duration.between(LocalTime.MIDNIGHT, runner.getChipTime()), timeFormat),
        			avgTimeStr, totalTimeStr };
        	
        	csvTeamData.add(csvString);	
        }
        
        return csvTeamData;
	}

}
