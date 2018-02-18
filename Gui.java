import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class Gui implements ActionListener {
	JFrame mainFrame;
	JTextField fnameField;
	JTextArea outputField;
	JTextArea outputField1;
	JButton parseButton;
	JLabel header;
    private String line;
	Gui(){
		initialize_main_Frame();
		mainFrame.setVisible(true);
	}
	
	void initialize_main_Frame() {
		mainFrame = new JFrame("Simple Trigger Parser");
		mainFrame.setSize(600,400);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.setResizable(false);
		mainFrame.setLayout(new BorderLayout(2,2));
		initialize_header();
		mainFrame.add(new JLabel());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize_outputField();
		initialize_actionListeners();                
	}
	
	void initialize_header() {
		JPanel headerPanel = new JPanel();
		header = new JLabel("Trigger Parser");
		Font obj = new Font("Arial",Font.BOLD,20);
		header.setFont(obj);
		header.setForeground(Color.black);
		headerPanel.setBackground(Color.white);
		headerPanel.add(header);
		mainFrame.add(headerPanel,BorderLayout.NORTH);
	}
	
	void initialize_outputField() {
		fnameField = new JTextField(25);
		parseButton = new JButton("Parse");
		parseButton.setBackground(Color.white);
		JLabel fnameLablel = new JLabel("File name:");
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.add(fnameLablel);
		textFieldPanel.add(fnameField);
		textFieldPanel.add(parseButton);
		JPanel opPanel = new JPanel(new BorderLayout());
		JLabel op = new JLabel("Output:");
		JScrollPane opSPane = new JScrollPane();
		outputField = new JTextArea(100,80);
		outputField.setBackground(Color.white);
		JScrollPane jsp = new JScrollPane(outputField);
		opPanel.add(textFieldPanel,BorderLayout.NORTH);
		opPanel.add(op,BorderLayout.WEST);
		opPanel.add(jsp,BorderLayout.CENTER);
		mainFrame.add(opPanel,BorderLayout.CENTER);
		fnameField.setFont(new Font("Arial",Font.BOLD,15));
		outputField.setFont(new Font("Arial",Font.BOLD,20));
		outputField.setEditable(false);
	}
	
	
	void initialize_actionListeners() {
		parseButton.addActionListener(this);
	}
	
        @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==parseButton) {
			String fname = fnameField.getText();
			if (fname.equals("")) {
				return;
			}
			File f = new File(fname);
			if (!f.exists()) {
				String message = "File "+fname+" does not exists";
				outputField.setText(message);
				//fnameField.setText("");
			}
			else {
				Runtime run = Runtime.getRuntime();
				String lex_command = "lex trigger.l";
				String yacc_command = "yacc -d trigger.y";
				String link_command = "gcc lex.yy.c y.tab.c";
				String ruun_command = "./a.out "+fname;
                                try {
					Process p1 =run.exec(lex_command);
					p1.waitFor();
					Process p2=run.exec(yacc_command);
					p2.waitFor();
					Process p3 = run.exec(link_command);
					p3.waitFor();
                                       Process p4 = run.exec(ruun_command);
					p4.waitFor();
					String line = "";
					String x = "";
					BufferedReader buf = new BufferedReader(new InputStreamReader(p4.getInputStream()));
					while ((line = buf.readLine()) != null) {
						x = x + line + "\n";
						// x=x+"\n";
						// tf1.setText(line+"\n");
					}
					outputField.setText(x);
					File file1 = new File("output_sp.txt");
					BufferedWriter bw = null;
					try {

						if (!file1.exists()) {
							file1.createNewFile();
						}

						FileWriter fw1 = new FileWriter(file1);
						bw = new BufferedWriter(fw1);
						bw.write(x);
						System.out.println("File written Successfully");

					} catch (IOException ioe) {
					} finally {
						try {
							if (bw != null)
								bw.close();
						} catch (Exception ex) {
							System.out.println("Error in closing the BufferedWriter" + ex);
						}
					}
				} catch (IOException | InterruptedException e1) {
				}
				//fnameField.setText("");

			}
		}
	}
	public static void main(String[] args) {
            Gui gui;
            gui = new Gui();
	}

	
}
