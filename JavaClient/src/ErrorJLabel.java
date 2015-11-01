import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;


public class ErrorJLabel extends JLabel{

	private static final long serialVersionUID = 1L;

	public ErrorJLabel(String string) {
		super(string);
	}
	
	public void startMessage(String errMsg) {
		this.setText(errMsg);
		this.validate();
		Timer t = new Timer();
		t.schedule(new ClearMessageTimerTask(), 1500);
	}
	
	class ClearMessageTimerTask extends TimerTask {
		@Override
		public void run() {
			ErrorJLabel.this.setText("");
			this.cancel();
		}
	}

}
