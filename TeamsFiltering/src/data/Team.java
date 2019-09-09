package data;

import java.time.LocalTime;
import java.util.ArrayList;

public class Team {

	private String teamName;
	private ArrayList<Runner> teamMembers;
	private LocalTime averageTime, totalTime;
	
	
	public Team(String teamName) {
		super();
		this.teamName = teamName;
		this.teamMembers  = new ArrayList<Runner>();
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
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public ArrayList<Runner> getTeamMembers() {
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

