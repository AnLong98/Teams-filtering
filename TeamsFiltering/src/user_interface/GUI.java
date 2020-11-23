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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import contracts.ITeamImporting;
import contracts.ITeamTrimming;
import contracts.ITeamsExporting;
import contracts.ITeamsFiltering;
import controller.ChooseFileAction;
import controller.FemaleSpinnerChangeListener;
import controller.MaleSpinnerChangeListener;
import controller.ProcessDataAction;
import controller.RunnersSpinnerChangeListener;
import data.Team;
import exceptions.IllegalInputHeaderException;
import utilities.FileUtilities;
import javax.swing.SpinnerListModel;
import java.awt.Font;

public class GUI extends JFrame {

	private JTextField txtFieldInputFileName;
	private JButton btnChooseFile, btnProcessData;
	private JSpinner spinnerRunnerCount;
	private JPanel panel;
	private JLabel lblRunnerCount, lblInputFileName;
	private JFileChooser fileChooser, outputFileChooser;
	private File choosenFile, outputFile;
	private JSpinner spinnerMalesCount, spinnerFemalesCount;

	/**
	 * Create the application.
	 */
	public GUI() {
		setFont(new Font("Dialog", Font.BOLD, 12));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setResizable(false);
		setTitle("Hronometar Team Filtering");
		setBounds(100, 100, 468, 193);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "name_758619713253");
		panel.setLayout(null);
		
		spinnerRunnerCount = new JSpinner();
		spinnerRunnerCount.setFont(new Font("Tahoma", Font.BOLD, 11));
		spinnerRunnerCount.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
		spinnerRunnerCount.setBounds(244, 74, 40, 20);
		spinnerRunnerCount.addChangeListener(new RunnersSpinnerChangeListener());
		panel.add(spinnerRunnerCount);
		
		lblRunnerCount = new JLabel("Number of competitors:");
		lblRunnerCount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRunnerCount.setToolTipText("The number of competitors within a team");
		lblRunnerCount.setBounds(31, 77, 148, 14);
		panel.add(lblRunnerCount);
		
		lblInputFileName = new JLabel("Input file:");
		lblInputFileName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInputFileName.setToolTipText("Click the Browse button to browse the input file");
		lblInputFileName.setBounds(32, 31, 68, 14);
		panel.add(lblInputFileName);
		
		btnProcessData = new JButton("Process");
		btnProcessData.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProcessData.addActionListener(new ProcessDataAction());
		btnProcessData.setToolTipText("Click here for data processing");

		btnProcessData.setBounds(318, 125, 118, 23);
		panel.add(btnProcessData);
		
		txtFieldInputFileName = new JTextField();
		txtFieldInputFileName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtFieldInputFileName.setEditable(false);
		txtFieldInputFileName.setBounds(110, 28, 174, 20);
		panel.add(txtFieldInputFileName);
		txtFieldInputFileName.setColumns(10);
		
		btnChooseFile = new JButton("Browse");
		btnChooseFile.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnChooseFile.addActionListener(new ChooseFileAction());
		btnChooseFile.setToolTipText("Click here to browse a file to process");
		btnChooseFile.setBounds(319, 27, 117, 23);
		panel.add(btnChooseFile);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSex.setToolTipText("");
		lblSex.setBounds(31, 129, 34, 14);
		panel.add(lblSex);
		
		JLabel lblM = new JLabel("M");
		lblM.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblM.setToolTipText("The number of male competitors within a team");
		lblM.setBounds(103, 129, 24, 14);
		panel.add(lblM);
		
		spinnerMalesCount = new JSpinner();
		spinnerMalesCount.setFont(new Font("Tahoma", Font.BOLD, 11));
		spinnerMalesCount.setModel(new SpinnerCircularListModel(new String[] {"-", "0", "1", "2"}));
		spinnerMalesCount.setBounds(127, 126, 39, 20);
		spinnerMalesCount.addChangeListener(new MaleSpinnerChangeListener());
		panel.add(spinnerMalesCount);
		
		JLabel lblF = new JLabel("         F");
		lblF.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblF.setToolTipText("The number of female competitors within a team");
		lblF.setBounds(194, 129, 40, 14);
		panel.add(lblF);
		
		spinnerFemalesCount = new JSpinner();
		spinnerFemalesCount.setFont(new Font("Tahoma", Font.BOLD, 11));
		spinnerFemalesCount.setModel(new SpinnerListModel(new String[] {"-", "0", "1", "2"}));
		spinnerFemalesCount.setBounds(244, 126, 39, 20);
		spinnerFemalesCount.addChangeListener(new FemaleSpinnerChangeListener());
		panel.add(spinnerFemalesCount);
		
		setVisible(true);
	}
	

	public void processDataAction(ITeamImporting importer, ITeamsFiltering teamsFilter, ITeamsExporting exporter) {
		if(choosenFile == null)
		{
			JOptionPane.showMessageDialog(GUI.this, 
					"There is no input file to process.");
		}
		else
		{	
			ArrayList<Team> parsedTeams = null;
			
			try 
			{
				parsedTeams = importer.getTeamsFromFile(choosenFile);
				
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
				JOptionPane.showMessageDialog(GUI.this, "Unknown error occurred reading the input file.");
				return;
			}
			
			if (parsedTeams != null) 
			{
				if (parsedTeams.size() > 0) 
				{
					ArrayList<Team> filteredTeams = filterTeams(teamsFilter, parsedTeams);
			
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
						
						exporter.exportTeamsToCSVFile(filteredTeams, outputFileString);
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
	
	public void updateGenderSpinnersModel(int value)
	{
		String[] modelValuesMale = new String[value + 2];
		String[] modelValuesFemale = new String[value + 2];
		modelValuesMale[0] = "-";
		modelValuesFemale[0] = "-";
		
		for(int i = 0; i <= value; i++)
		{
			modelValuesMale[i + 1] =  Integer.toString(i) ;
			modelValuesFemale[i + 1] =  Integer.toString(i) ;
		}
		
		spinnerMalesCount.setModel(new SpinnerCircularListModel(modelValuesMale));
		spinnerFemalesCount.setModel(new SpinnerCircularListModel(modelValuesFemale));
	}
	
	public void updateFemaleSpinnerValue(String maleValue)
	{
		if(maleValue.equals("-"))
		{
			spinnerFemalesCount.getModel().setValue("-");
		}else
		{
			int intValue = Integer.parseInt(maleValue);
			int runnersInTeam = (int) spinnerRunnerCount.getValue();
			spinnerFemalesCount.getModel().setValue(Integer.toString(runnersInTeam - intValue));
		}
	
	}
	
	public void updateMaleSpinnerValue(String femaleValue)
	{
		if(femaleValue.equals("-"))
		{
			spinnerMalesCount.getModel().setValue("-");
		}
		else
		{
			int intValue = Integer.parseInt(femaleValue);
			int runnersInTeam = (int) spinnerRunnerCount.getValue();
			spinnerMalesCount.getModel().setValue(Integer.toString(runnersInTeam - intValue));
		}
	
	}

	public ArrayList<Team> filterTeams(ITeamsFiltering teamsFilter, ArrayList<Team> parsedTeams)
	{
		try
		{
		
			int runnersInTeam = (int) spinnerRunnerCount.getValue();
			String valueMales = (String) spinnerMalesCount.getValue();
			String valueFemales = (String) spinnerFemalesCount.getValue();
			
			if(valueMales.equals("-"))
			{
				return teamsFilter.filterTeamsToSizeByNumber(parsedTeams, runnersInTeam);
			}else
			{
				return teamsFilter.filterTeamsToSizeByGender(parsedTeams, Integer.parseInt(valueMales), Integer.parseInt(valueFemales));
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(GUI.this, "There was an error with team size choice or sex choice.");
			return null;
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
