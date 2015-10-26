import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerHolder extends JPanel {
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField resolutionTextField;
	private JTextField frequencyTextField;
	private JButton btnConnect;
	private JButton btnRemove;

	/**
	 * Create the panel.
	 */
	public ServerHolder() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//dick in ass
		JLabel lblNewLabel = new JLabel("IMAGE PLACEHOLDER");
		add(lblNewLabel);
		
		ipTextField = new JTextField();
		ipTextField.setToolTipText("IP");
		add(ipTextField);
		ipTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setToolTipText("PORT");
		add(portTextField);
		portTextField.setColumns(10);
		
		resolutionTextField = new JTextField();
		resolutionTextField.setToolTipText("RESOLUTION");
		add(resolutionTextField);
		resolutionTextField.setColumns(10);
		
		frequencyTextField = new JTextField();
		frequencyTextField.setToolTipText("FREQUENCY");
		add(frequencyTextField);
		frequencyTextField.setColumns(10);
		
		btnConnect = new JButton("Connect");
		add(btnConnect);
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container parent = btnRemove.getParent(); // this JPanel
				
				Container parentparent = parent.getParent(); //JFrame
				parentparent.remove(parent);
				parentparent.validate();
				parentparent.repaint();
				
				//TODO: CLOSE SOCKETS AND STUFF
				
			}
		});
		add(btnRemove);

	}

}
