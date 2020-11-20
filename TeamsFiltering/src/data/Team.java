package data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

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
	
	public boolean TrimTeam(int malesRequired, int femalesRequired)
	{
		if(malesRequired + femalesRequired > teamMembers.size())return false;
		
		ArrayList<Runner> newTeam = new ArrayList<Runner>();
		
		
		for(Runner runner : teamMembers)
		{
			if(runner.getGender().equals("M") && malesRequired > 0)
			{
				newTeam.add(runner);
				malesRequired--;
			}
			
			if(runner.getGender().equals("F") && femalesRequired > 0)
			{
				newTeam.add(runner);
				femalesRequired--;
			}
			
			if(malesRequired + femalesRequired == 0)
			{
				teamMembers = newTeam;
				return true;
			}
			
		}
		
		return false;
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
		this.setAverageTime(dividedTotalTime);
		
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
	
	