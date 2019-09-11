package user_interface;

import java.awt.CardLayout;
import java.io.File;
import java.io.FileNotFoundException;
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
		setTitle("Filtriranje timskog plasmana");
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
		
		lblRunnerCount = new JLabel("Takmičara:");
		lblRunnerCount.setToolTipText("Broj takmičara u okviru tima");
		lblRunnerCount.setBounds(32, 77, 148, 14);
		panel.add(lblRunnerCount);
		
		lblInputFileName = new JLabel("Ulazni fajl:");
		lblInputFileName.setToolTipText("Klikni na dugme izaberi da izabereš ulazni fajl");
		lblInputFileName.setBounds(32, 31, 68, 14);
		panel.add(lblInputFileName);
		
		btnProcessData = new JButton("Obradi");
		btnProcessData.addActionListener(new ProcessDataAction());
		btnProcessData.setToolTipText("Klikni ovde za obradu podataka");

		btnProcessData.setBounds(174, 130, 125, 23);
		panel.add(btnProcessData);
		
		txtFieldInputFileName = new JTextField();
		txtFieldInputFileName.setEditable(false);
		txtFieldInputFileName.setBounds(110, 28, 174, 20);
		panel.add(txtFieldInputFileName);
		txtFieldInputFileName.setColumns(10);
		
		btnChooseFile = new JButton("Izaberi");
		btnChooseFile.addActionListener(new ChooseFileAction());
		btnChooseFile.setToolTipText("Klikni ovde da izabereš fajl za obradu");
		btnChooseFile.setBounds(319, 27, 117, 23);
		panel.add(btnChooseFile);
		
		setVisible(true);
	}

	
	public void processDataAction() {
		if(choosenFile == null)
		{
			JOptionPane.showMessageDialog(GUI.this, 
										"Nema izabranog fajla za obradu");
		}
		else
		{
			if (choosenFile instanceof File)
			{

			}
			
			int runnersInTeam = (int) spinnerRunnerCount.getValue();

			ArrayList<Team> parsedTeams = null;
			try 
			{
				parsedTeams = FileUtilities.ParseCSVFile(choosenFile);
				
			} catch (FileNotFoundException fne) 
			{

				fne.printStackTrace();
				JOptionPane.showMessageDialog(GUI.this, "Traženi fajl nije pronađen ili ne može biti otvoren!");
				return;
				
			} catch (IllegalInputHeaderException iihe) 
			{

				iihe.printStackTrace();
				JOptionPane.showMessageDialog(GUI.this, "Zaglavlje izabranog fajla nije podržano!");
				return;
			}
			
			if (parsedTeams != null) 
			{
				
				if (parsedTeams.size() > 0) 
				{
					ArrayList<Team> trimmedTeams = DataUtilities.removeExtraMembersFromTeams(parsedTeams, runnersInTeam);
					
					for(Team team : trimmedTeams) {
						team.calculateTeamTotalTime();
						team.calculateTeamAverageTime();
					}
					
					
					ArrayList<Team> sortedTeams = DataUtilities.sortByTotalTime(trimmedTeams);

					outputFileChooser = new JFileChooser();
					outputFileChooser.setCurrentDirectory(new java.io.File("."));
					outputFileChooser.setDialogTitle("Sačuvaj fajl");
					outputFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					outputFileChooser.setAcceptAllFileFilterUsed(false);
					
					int returnVal = outputFileChooser.showSaveDialog(GUI.this);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String outputFilePath = outputFileChooser.getCurrentDirectory().getAbsolutePath();
						String outputFileName = outputFileChooser.getSelectedFile().getName();
						String outputFileString = outputFilePath + "\\" + outputFileName;
						
						FileUtilities.writeCSVFile(sortedTeams, outputFileString);
						JOptionPane.showMessageDialog(GUI.this, "Obrađen fajl je sačuvan na sledećoj lokaciji:"
								+ "\n" + outputFilePath);
					}
					else
				    {
				    	JOptionPane.showMessageDialog(GUI.this, "Niste izabrali putanju za čuvanje"
				    			+ "\n" + "obrađenog fajla!");
				    }
				}
			} 
			else 
			{
				JOptionPane.showMessageDialog(GUI.this, "Došlo je do greške pri učitavanju"
		    			+ "\n" + "timova iz ulaznog fajla!");
			}
		}
	}
	
	
	public void chooseFileAction() 
	{
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "CSV File", "csv");
	    fileChooser.setFileFilter(filter);
	    
	    int returnVal = fileChooser.showOpenDialog(GUI.this);
	    if (returnVal == JFileChooser.APPROVE_OPTION)
	    {			    	
	    	txtFieldInputFileName.setText(fileChooser.getSelectedFile().getName());
	    	choosenFile = fileChooser.getSelectedFile();
	    }
	    else
	    {
	    	JOptionPane.showMessageDialog(GUI.this, "Niste izabrali fajl");
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
