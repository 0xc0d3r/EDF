import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.File;

public class GUI {
	
	public JLabel timeLabel;
	public JTextField time;
	public JTextField edfName;
	public JFileChooser edfFile;
	public JButton submit;
	public JButton edfBrowse;
	public JPanel timePanel,edfPanel,submitPanel,mainPanel;
	public JFrame mainFrame;
	
	public GUI() {
	
		//time panel
		timePanel = new JPanel(new FlowLayout());
		timeLabel = new JLabel("Time: ");
		time = new JTextField(10);
		timePanel.add(timeLabel);
		timePanel.add(time);
		timePanel.setBorder(BorderFactory.createTitledBorder("Time"));
		
		//edfPanel
		edfPanel = new JPanel(new FlowLayout());
		edfName=new JTextField(30);
		
		edfBrowse=new JButton("Browse");
        edfFile = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("(.edf/.edf+)","edf","edf+");
        edfFile.setFileFilter(filter);
		edfFile.setAcceptAllFileFilterUsed(false);
        edfBrowse.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int returnVal = edfFile.showOpenDialog(mainPanel);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = edfFile.getSelectedFile();
		        edfName.setText("" + file.getPath());
            }
         }
      	});
      	edfPanel.add(edfName);
      	edfPanel.add(edfBrowse);
      	edfPanel.setBorder(BorderFactory.createTitledBorder("EDF FILE"));
      	
      	
      	//submitPanel
      	
      	submitPanel = new JPanel(new FlowLayout());
		submit = new JButton("Submit");
		submit.setMnemonic(KeyEvent.VK_S);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int Time = 0;
				String edfFilePath="";
				String tmp1 = time.getText();
				String tmp2 = edfName.getText();
				if(tmp1.equals("")) {
					JOptionPane.showMessageDialog(mainPanel,"Enter time","Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(tmp2.equals("")) {
					JOptionPane.showMessageDialog(mainPanel,"Browse edf file","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						Time = Integer.parseInt(tmp1);
					}
					catch(NumberFormatException nfe) {
						JOptionPane.showMessageDialog(mainPanel,"Time can\'t be string data","Error",JOptionPane.ERROR_MESSAGE);
					}
					File edf = new File(tmp2);
					if(!edf.exists()) {
						JOptionPane.showMessageDialog(mainPanel,"Browse valid edf file","Error",JOptionPane.ERROR_MESSAGE);
					}
					EDF2ASCII ea = new EDF2ASCII(Time,tmp2);
					ea.edf2ascii();
					
				}
			}
		});
		submitPanel.add(submit);
		
		//mainPanel
		mainPanel = new JPanel(new GridLayout(3,1));
		mainPanel.add(timePanel);
		mainPanel.add(edfPanel);
		mainPanel.add(submitPanel);
		mainFrame = new JFrame("edf2ascii");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(500,200);
		mainFrame.add(mainPanel,BorderLayout.CENTER);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String []a) {
		new GUI();
	}
}
