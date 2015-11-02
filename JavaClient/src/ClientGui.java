import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ClientGui extends JFrame {

	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private ErrorJLabel errorMessageJLabel;
	private JLabel searchingJLabel;
	private boolean searching = false;
	

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
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_2.add(panel_3);
		
		JLabel lblIp = new JLabel("I.P");
		panel_3.add(lblIp);
		
		ipTextField = new JTextField("192.168.20.249");
		panel_3.add(ipTextField);
		ipTextField.setColumns(10);
		
		JLabel lblPort = new JLabel("PORT");
		panel_3.add(lblPort);
		
		portTextField = new JTextField("8084");
		panel_3.add(portTextField);
		portTextField.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_2.add(panel_4);
		
		searchingJLabel = new JLabel("");
		searchingJLabel.setForeground(Color.BLACK);
		searchingJLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel_4.add(searchingJLabel);
		
		errorMessageJLabel = new ErrorJLabel(""); 
		errorMessageJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		errorMessageJLabel.setForeground(Color.RED);
		panel_4.add(errorMessageJLabel);
		
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

}
