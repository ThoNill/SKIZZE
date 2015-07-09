package userinterface;

import io.IOManager;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SkizzenChooser extends JPanel {
	JComboBox skitzen;

	String skitzenname;

	public SkizzenChooser() {
		skitzen = new JComboBox();
		skitzen.setEditable(true);
		add(skitzen);
		fill();
	}

	public void fill() {
		IOManager iom = new IOManager();
		skitzen.removeAllItems();
		Vector<String> v = iom.getSkitzen();
		skitzen.addItem("");
		Iterator<String> i = v.iterator();
		while (i.hasNext()) {
			skitzen.addItem(i.next());
		}
	}

	public String getSkitzenname() {
		return (String) skitzen.getSelectedItem();
	}

	public void setSkitzenname(String name) {
		skitzen.setSelectedItem(name);
	}

	
	public void addActionListener(ActionListener l) {
		skitzen.addActionListener(l);
	}

	public void removeActionListener(ActionListener l) {
		skitzen.removeActionListener(l);
	}

	public JComboBox getSkitzen() {
		return skitzen;
	}

}
