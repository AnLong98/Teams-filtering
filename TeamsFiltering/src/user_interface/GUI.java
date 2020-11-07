package user_interface;

import java.awt.CardLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ChooseFileAction;
import controller.ProcessDataAction;
import data.Team;
import exceptions.IllegalInputHeaderException;
import utilities.DataUtilities;
import utilities.FileUtilities;

public class GUI extends JFrame {

	private JTextField txtFieldInputFileName;
	private JButton btnChooseFile, btnProcessData;
	private JSpinner spinnerRunnerCount;
	private JPanel panel;
	private JLabel lblRunnerCount, lblInputFileName;
	private JFileChooser fileChooser, outputFileChooser;
	private File choosenFile, outputFile;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setResizable(false);
		setTitle("Hronometar Team Filtering");
		setBounds(100, 100, 477, 227);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_758619713253");
		panel.setLayout(null);
		
		spinnerRunnerCount = new JSpinner();
		spinnerRunnerCount.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
		spinnerRunnerCount.setBounds(216, 74, 68, 20);
		panel.add(spinnerRunnerCount);
		
		lblRunnerCount = new JLabel("Number of competitors:");
		lblRunnerCount.setToolTipText("The number of competitors within a team");
		lblRunnerCount.setBounds(32, 77, 148, 14);
		panel.add(lblRunnerCount);
		
		lblInputFileName = new JLabel("Input file:");
		lblInputFileName.setToolTipText("Click the Browse button to browse the input file");
		lblInputFileName.setBounds(32, 31, 68, 14);
		panel.add(lblInputFileName);
		
		btnProcessData = new JButton("Process");
		btnProcessData.addActionListener(new ProcessDataAction());
		btnProcessData.setToolTipText("Click here for data processing");

		btnProcessData.setBounds(174, 130, 125, 23);
		panel.add(btnProcessData);
		
		txtFieldInputFileName = new JTextField();
		txtFieldInputFileName.setEditable(false);
		txtFieldInputFileName.setBounds(110, 28, 174, 20);
		panel.add(txtFieldInputFileName);
		txtFieldInputFileName.setColumns(10);
		
		btnChooseFile = new JButton("Browse");
		btnChooseFile.addActionListener(new ChooseFileAction());
		btnChooseFile.setToolTipText("Click here to browse a file to process");
		btnChooseFile.setBounds(319, 27, 117, 23);
		panel.add(btnChooseFile);
		
		setVisible(true);
	}
	
	public void processDataAction() {
		if(choosenFile == null)
		{
			JOptionPane.showMessageDialog(GUI.this, 
					"There is no input file to process.");
		}
		else
		{	
			int runnersInTeam = (int) spinnerRunnerCount.getValue();
			ArrayList<Team> parsedTeams = null;
			
			try 
			{
				parsedTeams = FileUtilities.getTeamsFromFile(choosenFile);
			} 
			catch (IOException fne) 
			{
				fne.printStackTrace();
				JOptionPane.showMessageDialog(GUI.this, "Error reading the input file. Check if file exists and make sure it's opened by another program.");
				return;	
			} 
			catch (IllegalInputHeaderException iihe) 
			{
				iihe.printStackTrace();
				JOptionPane.showMessageDialog(GUI.this, "The input file header is not supported.");
				return;
			}catch (ArrayIndexOutOfBoundsException a)
			{
				a.printStackTrace();
				JOptionPane.showMessageDialog(GUI.this, "Error reading the input file. Check if the data format is correct.");
				return;
			}catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(GUI.this, "Unknown error occured reding the input file.");
				return;
			}
			
			if (parsedTeams != null) 
			{
				if (parsedTeams.size() > 0) 
				{
					ArrayList<Team> trimmedTeams = DataUtilities.removeExtraMembersFromTeams(parsedTeams, runnersInTeam);
					for(Team team : trimmedTeams) 
					{
						team.calculateTeamTotalTime();
						team.calculateTeamAverageTime();
					}
					
					ArrayList<Team> sortedTeams = DataUtilities.sortByTotalTime(trimmedTeams);

			
					String userDir = System.getProperty("user.home");
					outputFileChooser = new JFileChooser(userDir +"/Desktop");
					outputFileChooser.setAcceptAllFileFilterUsed(false);
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "CSV file", "csv");
				    outputFileChooser.setFileFilter(filter);
					outputFileChooser.setDialogTitle("Save file");
					outputFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					outputFileChooser.setAcceptAllFileFilterUsed(false);
					
					int returnVal = outputFileChooser.showSaveDialog(GUI.this);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String outputFilePath = outputFileChooser.getCurrentDirectory().getAbsolutePath();
						String outputFileName = outputFileChooser.getSelectedFile().getName();
						String outputFileString = outputFilePath + "\\" + outputFileName;
						
						FileUtilities.writeCSVFile(sortedTeams, outputFileString);
						JOptionPane.showMessageDialog(GUI.this, "The processed file is saved in the following location:"
								+ "\n" + outputFilePath);
					}
					else
				    {
				    	JOptionPane.showMessageDialog(GUI.this, "Didn't browse a location to save the processed file.");
				    }
				}
			} 
			else 
			{
				JOptionPane.showMessageDialog(GUI.this, "There was an error reading"
		    			+ "\n" + "teams from the input file!");
			}
		}
	}
	
	public void chooseFileAction() 
	{
		String userDir = System.getProperty("user.home");
		fileChooser = new JFileChooser(userDir +"/Desktop");
		fileChooser.setAcceptAllFileFilterUsed(false);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "CSV file", "csv");
	    fileChooser.setDialogTitle("Browse input file");
	    fileChooser.setFileFilter(filter);
	    
	    int returnVal = fileChooser.showOpenDialog(GUI.this);
	    if (returnVal == JFileChooser.APPROVE_OPTION)
	    {			    	
	    	txtFieldInputFileName.setText(fileChooser.getSelectedFile().getName());
	    	choosenFile = fileChooser.getSelectedFile();
	    }
	    else
	    {
	    	JOptionPane.showMessageDialog(GUI.this, "Didn't browse the input file.");
	    	txtFieldInputFileName.setText("");
	    	choosenFile = null;
	    }
	}

	
	public JTextField getTxtFieldInputFileName() {
		return txtFieldInputFileName;
	}

	public void setTxtFieldInputFileName(JTextField txtFieldInputFileName) {
		this.txtFieldInputFileName = txtFieldInputFileName;
	}


	public JButton getBtnChooseFile() {
		return btnChooseFile;
	}

	public void setBtnChooseFile(JButton btnChooseFile) {
		this.btnChooseFile = btnChooseFile;
	}

	public JButton getBtnProcessData() {
		return btnProcessData;
	}

	public void setBtnProcessData(JButton btnProcessData) {
		this.btnProcessData = btnProcessData;
	}

	public JSpinner getSpinnerRunnerCount() {
		return spinnerRunnerCount;
	}

	public void setSpinnerRunnerCount(JSpinner spinnerRunnerCount) {
		this.spinnerRunnerCount = spinnerRunnerCount;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JLabel getLblRunnerCount() {
		return lblRunnerCount;
	}

	public void setLblRunnerCount(JLabel lblRunnerCount) {
		this.lblRunnerCount = lblRunnerCount;
	}

	public JLabel getLblInputFileName() {
		return lblInputFileName;
	}

	public void setLblInputFileName(JLabel lblInputFileName) {
		this.lblInputFileName = lblInputFileName;
	}

}
