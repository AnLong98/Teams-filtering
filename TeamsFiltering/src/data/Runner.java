package data;

import java.time.LocalTime;

public class Runner {
	
	
	private String firstName, lastName, state;
	private int bib_number;
	private char gender;
	private LocalTime chipTime;
	
	public Runner(String firstName, String lastName, String state, int bib_number, char gender, LocalTime chipTime) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.bib_number = bib_number;
		this.gender = gender;
		this.chipTime = chipTime;
		this.state = state;
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
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public LocalTime getChipTime() {
		return chipTime;
	}
	public void setChipTime(LocalTime chipTime) {
		this.chipTime = chipTime;
	}

}
