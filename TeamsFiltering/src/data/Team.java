package data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import utilities.DataUtilities;

public class Team {

	private String teamName;
	private ArrayList<Runner> teamMembers;
	private Duration averageTime, totalTime;
	
	
	public Team(String teamName) {
		super();
		this.teamName = teamName;
		this.teamMembers  = new ArrayList<Runner>();
		this.averageTime = Duration.ZERO; 	// (00:00:00) zavisi sta nam je lepse
		this.totalTime = Duration.ZERO;	// LocalTime.parse("00:00:00")
	}
	
	public Duration getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Duration averageTime) {
		this.averageTime = averageTime;
	}

	public Duration getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Duration totalTime) {
		this.totalTime = totalTime;
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
			Duration runnerTime = Duration.between(LocalTime.MIDNIGHT, teamMember.getChipTime());
			Duration currentTotalTime = this.getTotalTime();
			
			currentTotalTime = currentTotalTime.plus(runnerTime);
			
			this.setTotalTime(currentTotalTime);
		}
		
		
		return true;
	}
	
	public boolean calculateTeamAverageTime() 
	{
		if(teamMembers.size() == 0)return false;
		
		Duration dividedTotalTime = this.totalTime.dividedBy(this.getTeamMembers().size());
		//LocalTime calculatedAverageTime = LocalTime.ofNanoOfDay(dividedTotalTime.toNanos());
		
		//System.out.println("second before: " + calculatedAverageTime.getSecond());
		//System.out.println("nano before: " + calculatedAverageTime.getNano());
		
		Duration calculatedAverageTime = DataUtilities.roundSeconds(dividedTotalTime);
		
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
}
	
	