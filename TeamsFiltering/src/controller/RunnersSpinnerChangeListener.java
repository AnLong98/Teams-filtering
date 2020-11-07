package controller;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import user_interface.GUI;

public class RunnersSpinnerChangeListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner spinner = (JSpinner) e.getSource();
        GUI frame = (GUI) SwingUtilities.getRoot(spinner);
        int value = (int)spinner.getValue();
        frame.updateGenderSpinnersModel(value);
		
	}

}
