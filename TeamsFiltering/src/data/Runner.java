package data;

import java.time.LocalTime;

public class Runner {
	
	
	private String firstName, lastName, state, gender, yob, teamName;
	private int bib_number;
	private LocalTime chipTime;
	
	public Runner(String firstName, String lastName, String state, String yob, int bib_number, String gender, LocalTime chipTime, String teamName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.yob = yob;
		this.bib_number = bib_number;
		this.gender = gender;
		this.chipTime = chipTime;
		this.state = state;
		this.setTeamName(teamName);
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getBib_number() {
		return bib_number;
	}
	public void setBib_number(int bib_number) {
		this.bib_number = bib_number;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalTime getChipTime() {
		return chipTime;
	}
	public void setChipTime(LocalTime chipTime) {
		this.chipTime = chipTime;
	}

	public String getYob() {
		return yob;
	}

	public void setYob(String yob) {
		this.yob = yob;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
