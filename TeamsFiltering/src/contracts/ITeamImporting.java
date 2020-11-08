package contracts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.Team;
import exceptions.IllegalInputHeaderException;

public interface ITeamImporting {
	public ArrayList<Team> getTeamsFromFile(File csvFile) throws IllegalInputHeaderException, IOException;
}
