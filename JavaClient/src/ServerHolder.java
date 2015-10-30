import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ServerHolder extends JPanel {
	private JTextField frequencyTextField;
	private JButton btnConnect;
	private JButton btnRemove;
	private String serverIP;
	private int port;
	private Socket socket;
	private JLabel currentImageJlabel;
	private JPanel panel;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnRes;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;

	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public ServerHolder(Socket s, String ip, int port) {
		this.socket = s;
		this.serverIP = ip;
		this.port = port;
		//rtprwrwwpoå
		//pahurdianuhdoakjsdhaklsjfhnoajhflnaskjdhfnlaskjdfhnskjdfnf

		setBackground(new Color(176, 224, 230));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		currentImageJlabel = new JLabel("IMAGE PLACEHOLDER");
		add(currentImageJlabel);
		
		panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		ActionListener actionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OutputStream out = null;
				try {
					 out = new DataOutputStream(socket.getOutputStream());
					 out.write(30);
					 out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				if (rdbtnNewRadioButton.isSelected()) {
					System.out.println("1");
					
				} else if (rdbtnRes.isSelected()) {
					System.out.println("2");
					
				} else if (radioButton.isSelected()) {
					System.out.println("3");
					
				} else if (radioButton_1.isSelected()) {
					System.out.println("4");
					
				} 
				
			}
		};
		
		rdbtnNewRadioButton = new JRadioButton("res1");
		panel.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.addActionListener(actionListener);
		
		rdbtnRes = new JRadioButton("res2");
		panel.add(rdbtnRes);
		rdbtnRes.addActionListener(actionListener);
		
		radioButton = new JRadioButton("res3");
		panel.add(radioButton);
		radioButton.addActionListener(actionListener);
		
		radioButton_1 = new JRadioButton("res4");
		panel.add(radioButton_1);
		radioButton_1.addActionListener(actionListener);
		
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnRes);
		group.add(radioButton);
		group.add(radioButton_1);
		rdbtnNewRadioButton.setSelected(true);
		
		frequencyTextField = new JTextField();
		frequencyTextField.setToolTipText("FREQUENCY");
		add(frequencyTextField);
		frequencyTextField.setColumns(10);

		btnConnect = new JButton("Connect");
		add(btnConnect);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMe();
				// TODO: CLOSE SOCKETS AND STUFF
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnRemove);

		/**
		 * Start receiving from serversocket
		 */
		Thread t = new Thread(new ReceiveImageRunner());
		t.start();
	}

	private void deleteMe() {
		Container parent = btnRemove.getParent(); // this JPanel
		Container parentparent = parent.getParent(); // JFrame
		parentparent.remove(parent);
		parentparent.validate();
		parentparent.repaint();
	}

	private class ReceiveImageRunner implements Runnable {

		@Override
		public void run() {
			System.out.println("Open to receive from server.");
			/**
			 * TODO: 1. ADD LOOP that receives the images 2. UPDATE Jlabel once
			 * image has been received
			 */

			Image image = null;
			if (socket.isConnected()) {
				System.out.println("Connected:"+ socket.isConnected());
//				image = scaleDown(readImg(socket),50,50);
				image = readImg(socket);
				System.out.println("image received: "+image.toString());
				System.out.println(image);
			} else {
				System.out.println("Not connected to socket");
			}
			ImageIcon imgIcon = new ImageIcon(image);

			currentImageJlabel.setIcon(imgIcon);

 
		}

		public BufferedImage convertBytesToBuffImage(byte[] imgAsBytes) {
			InputStream in = new ByteArrayInputStream(imgAsBytes);
			BufferedImage bImageFromConvert = null;
			try {
				bImageFromConvert = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			return bImageFromConvert;
//			return scaleDown(bImageFromConvert, 0.25, 0.25);
		}
		
		public BufferedImage scaleDown(BufferedImage bImageFromConvert, double widthScale, double heightScale) {
			int w = bImageFromConvert.getWidth();
			int h = bImageFromConvert.getHeight();
			BufferedImage after = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(widthScale, heightScale);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(bImageFromConvert, after);
			return after;
		}

		
		public BufferedImage readImg(Socket socket) {
			try {
				System.out.println("hi");
				return ImageIO.read(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("bye");
			return null;
		}

	}

}
