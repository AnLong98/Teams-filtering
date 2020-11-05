package contracts;

import java.util.ArrayList;

import data.Team;

public interface ITeamsExporting {
	public boolean exportTeamsToCSVFile(ArrayList<Team> teams, String fileName);

}
