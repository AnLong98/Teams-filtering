package contracts;

import java.util.ArrayList;

import data.Team;

public interface ITeamSorting {
	public ArrayList<Team> sortByTotalTime(ArrayList<Team> teams);
}
