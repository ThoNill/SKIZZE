package transport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import nls.NLS;
import parts.AppControler;
import parts.AppPart;
import userinterface.AppProperties;
import userinterface.PropertiesDialog;
import action.ActionController;
import action.TransferAction;

/**
 * Senden und Empfangen über das Netzwerk
 * 
 * @author Thomas Nill
 *
 */

public class OnlineAppPart extends AppPart implements ActionListener {
	private JButton bonline = null;

	private boolean online;

	private AppProperties prop;

	public OnlineAppPart(AppControler appc) {
		super(appc,"online");
		setOnline(false);
	}

	public void addComponents() {
		addMenu("prop");
		addMenu("transfer");

		bonline = new JButton();
		bonline.setActionCommand("online");
		bonline.addActionListener(this);

		getAppc().getUi().getInternToolBar().add(bonline);
		setOnline(false);
	};

	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		if ("prop".equals(command)) {
			PropertiesDialog d = new PropertiesDialog(getAppc().getUi(),
					getProp());
			d.setVisible(true);
		}
		;
		if ("online".equals(command)) {
			setOnline(!online);
		}
		if ("transfer".equals(arg0.getActionCommand())) {
			transfer();
		}

	}
	
	public void setText(String text) {
		if (bonline != null)
			bonline.setText(text);
	}

	public void setOnline(boolean online) {
		if (bonline != null)
			bonline.setText(NLS.getText((online) ? "gooffline" : "goonline"));

		if (this.online == online)
			return;

		this.online = online;

		ActionController old = getAppc().getActionControler();
		
		if (old instanceof OnlineActionController) {
			OnlineActionController ac = (OnlineActionController)old;
			ac.close();
		}

		if (online) {
			OnlineActionController ac = new OnlineActionController(this);
			ac.setPort(getProp().getPort());
			ac.setHost(getProp().getHost());
			getAppc().setActionControler(ac);
		} else {
			getAppc().setActionControler(new ActionController());
		}
		old.close();

	}

	public void setMessage(String text) {
		getAppc().getUi().setMessage(text);
	}

	public AppProperties getProp() {
		if (prop == null) {
			prop = new AppProperties();
			prop.setHost("localhost");
			prop.setPort(1234);
		}
		return prop;
	}

	public void setProp(AppProperties prop) {
		this.prop = prop;
	}

	public void close() {
		setOnline(false);
	}

	public void transfer() {
		if (this.online) {
			getAppc().getActionControler().addElement(
					new TransferAction(getAppc().getModel()));
		}
	}

	

}
