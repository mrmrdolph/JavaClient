import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import org.omg.CORBA.FREE_MEM;

public class ClientGui extends JFrame {

	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private ErrorJLabel errorMessageJLabel;
	private JLabel searchingJLabel;
	private boolean searching = false;
	private JTextField delayTextField;
	private JRadioButton res1NewRadioButton;
	private JRadioButton res2NewRadioButton_1;
	private JRadioButton res3NewRadioButton_2;
	private JRadioButton res4NewRadioButton_3;
	private String resolutionID = "1";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui frame = new ClientGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 697, 467);
		this.setResizable(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblAddCamera = new JLabel("Add camera");
		panel_1.add(lblAddCamera);
		
		JButton btnNewButton = new JButton();
		btnNewButton.setIcon(new ImageIcon(ClientGui.class.getResource("/img/axiscame.png")));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBorder(null);
		btnNewButton.setContentAreaFilled(false); 
	        
		btnNewButton.setFocusPainted(false); 
	    btnNewButton.setOpaque(false);
	
		panel_1.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel ipPortPanel = new JPanel();
		ipPortPanel.setBackground(Color.WHITE);
		panel_2.add(ipPortPanel);
		
		JLabel lblIp = new JLabel("I.P");
		ipPortPanel.add(lblIp);
		
		ipTextField = new JTextField("192.168.20.249");
		ipPortPanel.add(ipTextField);
		ipTextField.setColumns(10);
		
		JLabel lblPort = new JLabel("PORT");
		ipPortPanel.add(lblPort);
		
		portTextField = new JTextField("8080");
		ipPortPanel.add(portTextField);
		portTextField.setColumns(10);
		
        ActionListener actionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resolutionID = "1";
				if (res1NewRadioButton.isSelected()) {
					System.out.println("1");
					resolutionID = "1";
				} else if (res2NewRadioButton_1.isSelected()) {
					System.out.println("2");
					resolutionID = "2";
				} else if (res3NewRadioButton_2.isSelected()) {
					System.out.println("3");
					resolutionID = "3";
				} else if (res4NewRadioButton_3.isSelected()) {
					System.out.println("4");
					resolutionID = "4";
				}

			}
		};
		
		JPanel radioButtonPanel = new JPanel();
		panel_2.add(radioButtonPanel);
		
		res1NewRadioButton = new JRadioButton("R1");
		radioButtonPanel.add(res1NewRadioButton);
		res1NewRadioButton.addActionListener(actionListener);
		
		res2NewRadioButton_1 = new JRadioButton("R2");
		radioButtonPanel.add(res2NewRadioButton_1);
		res2NewRadioButton_1.addActionListener(actionListener);
		
		res3NewRadioButton_2 = new JRadioButton("R3");
		radioButtonPanel.add(res3NewRadioButton_2);
		res3NewRadioButton_2.addActionListener(actionListener);
		
		res4NewRadioButton_3 = new JRadioButton("R4");
		radioButtonPanel.add(res4NewRadioButton_3);
		res4NewRadioButton_3.addActionListener(actionListener);
		
		ButtonGroup group = new ButtonGroup();
		group.add(res1NewRadioButton);
		group.add(res2NewRadioButton_1);
		group.add(res3NewRadioButton_2);
		group.add(res4NewRadioButton_3);
		res1NewRadioButton.setSelected(true);
		
		JPanel delayPanel = new JPanel();
		panel_2.add(delayPanel);
		
		delayTextField = new JTextField();
		delayPanel.add(delayTextField);
		delayTextField.setColumns(10);
		
		JPanel errorMsgPanel = new JPanel();
		errorMsgPanel.setBackground(Color.WHITE);
		panel_2.add(errorMsgPanel);
		
		searchingJLabel = new JLabel("");
		searchingJLabel.setForeground(Color.BLACK);
		searchingJLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		errorMsgPanel.add(searchingJLabel);
		
		errorMessageJLabel = new ErrorJLabel(""); 
		errorMessageJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		errorMessageJLabel.setForeground(Color.RED);
		errorMsgPanel.add(errorMessageJLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (searching) return;
				searchingJLabel.setText("Searching..."); 		//might need to put these into SwingWorker thread
				
				searching = true;
				new Thread(new Runnable() {
				    public void run() {
				    	Socket serverSocket = null;
				        boolean socketConnected = true;
				        try {
				        	serverSocket = new Socket(ipTextField.getText(),Integer.parseInt(portTextField.getText()));
				        } catch (NumberFormatException | IOException e1) {
				        	System.out.println("No Server found at " + ipTextField.getText()+":"+Integer.parseInt(portTextField.getText()));
				        	socketConnected = false;
				        	errorMessageJLabel.startMessage("No Server found at " + ipTextField.getText()+":"+Integer.parseInt(portTextField.getText()));
				        	
				        }
				        if (socketConnected) {
				        	DataOutputStream out = null;
				    		try {
				    			out = new DataOutputStream(serverSocket.getOutputStream());
				    			out.write(resolutionID.getBytes());  //set by radio buttons!
				    			System.out.println("test: "+getDelay(delayTextField.getText().toString()));
				    			out.write(getDelay(delayTextField.getText().toString()).getBytes()); // 6 BYTES/chars!! range should be 0 - 30000 (30000 equals about 30 seconds)
				    			out.flush();
				    		} catch (IOException e1) {
				    			e1.printStackTrace();
				    		}
				        	ServerHolder server = new ServerHolder(serverSocket, ipTextField.getText(),Integer.parseInt(portTextField.getText()));
				        	panel.add(server);
				        }
				        panel.validate();
				        searching = false;
				        searchingJLabel.setText("");
				    }
				}).start();
				
			}
		});
		
	}
	
	private static String getDelay(String input) {
		String result = "";
		for (int i=0; i<6-input.length();i+=1) {
			result += "0";
		}
		result += input;
		return result;
	}

}
