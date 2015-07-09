package gui;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Skizze;


/**
 * 
 * Swing Komponente um Skitzen grafisch darzustellen 
 * 
 * @author Thomas Nill
 *
 */
public class SkizzeComponent extends JComponent implements ChangeListener {

	private static final long serialVersionUID = 4228495550165832404L;

	Skizze model;

	public SkizzeComponent() {
		super();
	}

	public Skizze getModel() {
		return model;
	}

	public void setModel(Skizze model) {
		if (this.model == model) {
			return;
		}
		if (this.model != null) {
			this.model.removeChangeListener(this);
		}
		this.model = model;
		this.model.addChangeListener(this);
		updateUI();
	}

	public void stateChanged(ChangeEvent arg0) {
		updateUI();
	}

	public void setUI(SkizzeComponentUI ui) {
		super.setUI(ui);
	}

	public void updateUI() {
		setUI((SkizzeComponentUI) UIManager.getUI(this));
		invalidate();
	}

	public String getUIClassID() {
		return "SkitzeComponentUI";
	}
}
