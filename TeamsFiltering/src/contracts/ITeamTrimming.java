package contracts;

import java.util.ArrayList;

import data.Team;

public interface ITeamTrimming {

	public ArrayList<Team> trimTeamsToSizeByNumber(ArrayList<Team> teams, int validSize);
	public ArrayList<Team> trimTeamsToSizeByGender(ArrayList<Team> teams, int males, int females);
}
