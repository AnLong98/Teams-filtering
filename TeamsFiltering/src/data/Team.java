package data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import utilities.DataUtilities;

public class Team {

	private String teamName;
	private ArrayList<Runner> teamMembers;
	private LocalTime averageTime, totalTime;
	
	
	public Team(String teamName) {
		super();
		this.teamName = teamName;
		this.teamMembers  = new ArrayList<Runner>();
		this.averageTime = LocalTime.MIDNIGHT; 	// (00:00:00) zavisi sta nam je lepse
		this.totalTime = LocalTime.MIDNIGHT;	// LocalTime.parse("00:00:00")
	}
	
	public boolean TrimTeam(int newSize)
	{
		if(teamMembers.size() < newSize)
		{
			return false;
		}
		
		//Remove all extra members
		while(teamMembers.size() > newSize)
		{
			teamMembers.remove(teamMembers.size() - 1);
		}
		
		return true;
	}
	
	public void AddRunnerToTeam(Runner runner)
	{
		teamMembers.add(runner);
	}

	public boolean calculateTeamTotalTime() 
	{
		if (this.getTeamMembers().size() == 0)
		{
			return false;
		}
		
		for (Runner teamMember : this.getTeamMembers())
		{
			LocalTime runnerTime = teamMember.getChipTime();
			LocalTime currentTotalTime = this.getTotalTime();
			
			currentTotalTime = currentTotalTime.plusSeconds(runnerTime.getSecond());
			currentTotalTime = currentTotalTime.plusMinutes(runnerTime.getMinute());
			currentTotalTime = currentTotalTime.plusHours(runnerTime.getHour());
			
			this.setTotalTime(currentTotalTime);
		}
		
		
		return true;
	}
	
	public boolean calculateTeamAverageTime() 
	{
		if (this.getTotalTime() == LocalTime.MIDNIGHT)
		{
			return false;
		}
		
		Duration totalTime = Duration.between(LocalTime.MIDNIGHT, this.getTotalTime());
		Duration dividedTotalTime = totalTime.dividedBy(this.getTeamMembers().size());
		LocalTime calculatedAverageTime = LocalTime.ofNanoOfDay(dividedTotalTime.toNanos());
		
		//System.out.println("second before: " + calculatedAverageTime.getSecond());
		//System.out.println("nano before: " + calculatedAverageTime.getNano());
		
		calculatedAverageTime = DataUtilities.roundSeconds(calculatedAverageTime);
		
		//System.out.println("second after: " + calculatedAverageTime.getSecond());
		//System.out.println("nano after: " + calculatedAverageTime.getNano());
		
		this.setAverageTime(calculatedAverageTime);
		
		//System.out.println("Team average time: " + this.getAverageTime());
		
		return true;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public ArrayList<Runner> getTeamMembers() 
	{
		if (this.teamMembers == null)
		{
			this.teamMembers = new ArrayList<Runner>();
		}
		
		return teamMembers;
	}
	
	public void setTeamMembers(ArrayList<Runner> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	public LocalTime getAverageTime() {
		return averageTime;
	}
	
	public void setAverageTime(LocalTime averageTime) {
		this.averageTime = averageTime;
	}
	
	public LocalTime getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(LocalTime totalTime) {
		this.totalTime = totalTime;
	}
	
}

