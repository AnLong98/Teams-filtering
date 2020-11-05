package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import user_interface.GUI;
import utilities.TeamsHandler;

public class ProcessDataAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JComponent c = (JComponent) e.getSource();
		GUI frame = (GUI) SwingUtilities.getRoot(c);
		
		frame.processDataAction(new TeamsHandler());
	}

}
