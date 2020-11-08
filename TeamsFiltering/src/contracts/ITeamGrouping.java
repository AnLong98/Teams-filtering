package contracts;

import java.util.ArrayList;

import data.Runner;
import data.Team;

public interface ITeamGrouping {

	public ArrayList<Team> groupRunnersByTeam(ArrayList<Runner> runners);
}
