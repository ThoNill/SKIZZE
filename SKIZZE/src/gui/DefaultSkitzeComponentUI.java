package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;


/**
 * 
 * Implementierung der Swing UI Klasse zu SkitzeComponent 
 * 
 * @author Thomas Nill
 *
 */
public class DefaultSkitzeComponentUI extends SkizzeComponentUI {

	private DefaultSkitzeComponentUI() {
		super();
	}

	public void paint(Graphics g, JComponent c) {
		SkizzeComponent sc = (SkizzeComponent) c;
		Graphics2D g2 = (Graphics2D) g;

		sc.getModel().paint(g2);
	}

	public static ComponentUI createUI(JComponent c) {
		return new DefaultSkitzeComponentUI();
	}

	public void installUI(JComponent c) {

	}

	public void uninstallUI(JComponent c) {

	}

}
