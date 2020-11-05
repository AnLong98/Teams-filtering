package contracts;

import java.util.ArrayList;

import data.Team;

public interface ITeamsFiltering {

	public ArrayList<Team> filterTeamsToSizeByNumber(ArrayList<Team> teams, int validSize);
	public ArrayList<Team> filterTeamsToSizeByGender(ArrayList<Team> teams, int malesRequired, int femalesRequired);
}
