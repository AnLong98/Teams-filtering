package data;
import java.time.LocalTime;
public class Runner {
	
	private static String chipTimeFormat = " ";
	private String firstName, lastName, state, gender, yob, teamName;
	private int bib_number;
	private LocalTime chipTime;
	
	public Runner(String firstName, String lastName, String state, String yob, int bib_number, String gender, LocalTime chipTime, String teamName) {
		super();
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setYob(yob);
		this.setBib_number(bib_number);
		this.setGender(gender);
		this.setChipTime(chipTime);
		this.setState(state);
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

	public static String getChipTimeFormat() {
		return chipTimeFormat;
	}

	public static void setChipTimeFormat(String chipTimeFormat) {
		Runner.chipTimeFormat = chipTimeFormat;
	}

}
