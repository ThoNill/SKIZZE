package parts;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import nls.NLS;


/**
 * Anwendung beenden
 * 
 * @author Thomas Nill
 *
 */
public class EndeAppPart extends AppPart implements WindowListener {

	public EndeAppPart(AppControler appc) {
		super(appc,"exit");
	}

	public void addComponents() {
		super.addComponents();
		getAppc().getUi().addWindowListener(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (getName().equals(arg0.getActionCommand())) {
			exit();
		}
	}

	public void windowClosing(WindowEvent arg0) {
		exit();
	}

	private void exit() {
		int res = JOptionPane.NO_OPTION;
		if (getAppc().isDirty()) {

			res = JOptionPane.showConfirmDialog(null, NLS
					.getText("exitAbestaetigen"), NLS
					.getText("exitInformation"),

			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
		if (res == JOptionPane.NO_OPTION) {
			getAppc().close();
			System.exit(0);
		}
		if (res == JOptionPane.YES_OPTION) {
			if(getAppc().safe()) {
				getAppc().close();
				System.exit(0);
			}
		}
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}

}
