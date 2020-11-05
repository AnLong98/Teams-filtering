package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import contracts.IFileParsing;
import contracts.IRunnerSorting;
import contracts.ITeamGrouping;
import contracts.ITeamImporting;
import data.Runner;
import data.Team;
import exceptions.IllegalInputHeaderException;

public class TeamsImporter implements ITeamImporting{
	private IFileParsing parser;
	private IRunnerSorting sorter;
	private ITeamGrouping grouper;
	
	public TeamsImporter(IFileParsing parser, IRunnerSorting sorter, ITeamGrouping grouper) {
		super();
		this.parser = parser;
		this.sorter = sorter;
		this.grouper = grouper;
	}

	@Override
	public ArrayList<Team> getTeamsFromFile(File csvFile) throws IllegalInputHeaderException, IOException {
		ArrayList<Runner> runners =  new ArrayList<Runner>();

		runners = parser.readRunnersFromFile(csvFile);
		runners = sorter.sortRunnersByTimeAscending(runners);
			
		return grouper.groupRunnersByTeam(runners);
	}

}
