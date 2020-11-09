package controller;

import java.awt.Cursor;
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
import utilities.TimeParser;

public class ProcessDataAction extends AbstractAction {

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JComponent c = (JComponent) e.getSource();
		GUI frame = (GUI) SwingUtilities.getRoot(c);
		
		TimeParser timeParser = new TimeParser();
		FileParser parser = new FileParser(timeParser);
		RunnersSorter sorter = new RunnersSorter();
		TeamsHandler handler = new TeamsHandler();
		TeamsSorter teamsSorter =  new TeamsSorter();
		TimeFormatter formatter =  new TimeFormatter();
		TeamsImporter importer =  new TeamsImporter(parser, sorter, handler);
		TeamsFilter filter = new TeamsFilter(handler, teamsSorter);
		TeamExporter exporter =  new TeamExporter(formatter);
		
		frame.setCursor(Cursor.WAIT_CURSOR);
		frame.processDataAction(importer, filter, exporter);
		frame.setCursor(Cursor.DEFAULT_CURSOR);
	}

}
