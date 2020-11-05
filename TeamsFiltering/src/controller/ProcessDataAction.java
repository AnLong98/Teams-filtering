package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import user_interface.GUI;
import utilities.FileParser;
import utilities.RunnersSorter;
import utilities.TeamExporter;
import utilities.TeamsFilter;
import utilities.TeamsHandler;
import utilities.TeamsImporter;
import utilities.TeamsSorter;
import utilities.TimeFormatter;

public class ProcessDataAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JComponent c = (JComponent) e.getSource();
		GUI frame = (GUI) SwingUtilities.getRoot(c);
		
		FileParser parser = new FileParser();
		RunnersSorter sorter = new RunnersSorter();
		TeamsHandler handler = new TeamsHandler();
		TeamsSorter teamsSorter =  new TeamsSorter();
		TimeFormatter formatter =  new TimeFormatter();
		TeamsImporter importer =  new TeamsImporter(parser, sorter, handler);
		TeamsFilter filter = new TeamsFilter(handler, teamsSorter, formatter);
		TeamExporter exporter =  new TeamExporter(formatter);
		
		frame.processDataAction(importer, filter, exporter);
	}

}
