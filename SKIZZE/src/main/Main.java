package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import nls.NLS;
import parts.AppControler;
import parts.DBAppPart;
import parts.DrawAppPart;
import parts.EndeAppPart;
import parts.KeyAppPart;
import parts.LineWithFontUpdater;
import parts.MiscAppPart;
import transport.OnlineAppPart;
import userinterface.UserInterface;


/**
 * 
 * Die Anwendung Skitzen ertellen und über das Netz zusammenarbeiten
 * 
 * @author Thomas Nill
 *
 */
public class Main {

	public final static void main(String args[]) {
		NLS.init();
		UIManager.put("SkitzeComponentUI", "gui.DefaultSkitzeComponentUI");

		AppControler appc = new AppControler();
		UserInterface ui = new UserInterface();
		appc.setUi(ui);
		appc.addPart(new MiscAppPart(appc));
		appc.addPart(new OnlineAppPart(appc));
		appc.addPart(new DBAppPart(appc));
		appc.addPart(new DrawAppPart(appc));
		appc.addPart(new KeyAppPart(appc));
		appc.addPart(new EndeAppPart(appc));
		appc.addPart(new LineWithFontUpdater(appc));
		appc.clearTheModelAction();
		

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		ui.setSize(dim);
		ui.setVisible(true);

	}
}
