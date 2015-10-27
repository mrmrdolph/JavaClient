import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ClientGui extends JFrame {

	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;

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
		setBounds(100, 100, 727, 498);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
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
		
		JLabel lblIp = new JLabel("I.P");
		panel_1.add(lblIp);
		
		ipTextField = new JTextField();
		panel_1.add(ipTextField);
		ipTextField.setColumns(10);
		
		JLabel lblPort = new JLabel("PORT");
		panel_1.add(lblPort);
		
		portTextField = new JTextField();
		panel_1.add(portTextField);
		portTextField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(176, 224, 230));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerHolder server = new ServerHolder(ipTextField.getText(),portTextField.getText());
				ipTextField.setText("");
				portTextField.setText("");
				panel.add(server);
				panel.validate();
				
			}
		});
		
	}

}
