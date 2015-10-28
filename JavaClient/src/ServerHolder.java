import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerHolder extends JPanel {
	private JTextField resolutionTextField;
	private JTextField frequencyTextField;
	private JButton btnConnect;
	private JButton btnRemove;
	private String serverIP;
	private int port;
	private Socket socket;
	private JLabel currentImageJlabel;

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

		setBackground(new Color(176, 224, 230));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		currentImageJlabel = new JLabel("IMAGE PLACEHOLDER");
		add(currentImageJlabel);

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
				deleteMe();
				// TODO: CLOSE SOCKETS AND STUFF

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
			try {
				image = convertBytesToBuffImage(readBytes(socket));
			} catch (IOException e) {
				e.printStackTrace();
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

			
			return scaleDown(bImageFromConvert, 0.25, 0.25);
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

		public byte[] readBytes(Socket socket) throws IOException {
			// Again, probably better to store these objects references in the
			// support class
			InputStream in = socket.getInputStream();
			DataInputStream dataInStream = new DataInputStream(in);

			int len = dataInStream.readInt();
			byte[] data = new byte[len];
			if (len > 0) {
				dataInStream.readFully(data);
			}
			return data;
		}

	}

}
