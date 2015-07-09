package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import model.teile.ColorTeil;
import model.teile.FontTeil;
import model.teile.LineWidthTeil;
import model.teile.SkizzenTeil;

/**
 * 
 * Die Klasse des Datenmodells eine Skitze, die einen Namen und verschiedene
 * Teile hat
 * 
 * @author Thomas Nill
 *
 */
public class Skizze implements SkizzenTeil {
	private static final long serialVersionUID = 8968178121114199097L;
	private transient EventListenerList ll;
	private transient FontRenderContext fontContext;
	private ArrayList<SkizzenTeil> teile = null;
	private String name = "";
	private transient boolean dirty;
	private ColorTeil currentColor;
	private LineWidthTeil currentLineWidth;
	private FontTeil currentFont;

	public Skizze() {
		teile = new ArrayList<SkizzenTeil>();
	}

	public ArrayList<SkizzenTeil> getTeile() {
		if (teile == null) {
			teile = new ArrayList<SkizzenTeil>();
		}
		return teile;
	}

	public void addChangeListener(ChangeListener l) {
		if (ll == null) {
			ll = new EventListenerList();
		}
		ll.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
		if (ll == null)
			return;
		ll.remove(ChangeListener.class, l);
	}

	public void addSkitzenListener(SkizzenListener l) {
		if (ll == null) {
			ll = new EventListenerList();
		}
		ll.add(SkizzenListener.class, l);
	}

	public void removeSkitzenListener(SkizzenListener l) {
		if (ll == null)
			return;
		ll.remove(SkizzenListener.class, l);
	}

	public void fireChanged() {
		if (ll == null)
			return;
		ChangeEvent e = new ChangeEvent(this);
		Object o[] = ll.getListenerList();
		for (int i = 0; i < o.length; i += 2) {
			if (o[i].equals(ChangeListener.class)) {
				((ChangeListener) o[i + 1]).stateChanged(e);
			}
		}
	}

	public void fireChanged(int status, SkizzenTeil teil) {
		if (ll == null)
			return;

		SkizzenEvent e = new SkizzenEvent(status, teil);
		Object o[] = ll.getListenerList();
		for (int i = 0; i < o.length; i += 2) {
			if (o[i].equals(SkizzenListener.class)) {
				((SkizzenListener) o[i + 1]).perform(teil.getClass(), e);
			}
		}
	}

	public void paint(Graphics2D g) {

		fontContext = g.getFontRenderContext();
		Font f = g.getFont();
		Stroke s = g.getStroke();
		Color c = g.getColor();
		synchronized (teile) {
			for (SkizzenTeil t : teile) {
				t.paint(g);
			}
		}

		g.setFont(f);
		g.setStroke(s);
		g.setColor(c);

	}

	public synchronized HashMap<Class, SkizzenTeil> getLastTeile() {
		HashMap<Class, SkizzenTeil> hash = new HashMap<Class, SkizzenTeil>();
		for (SkizzenTeil t : getTeile()) {
			hash.put(t.getClass(), t);
		}
		return hash;
	}

	public void addElement(SkizzenTeil t) {
		synchronized (teile) {
			dirty = true;

			if (t instanceof ColorTeil) {
				if (t.equals(currentColor))
					return;
				currentColor = (ColorTeil) t;
			}
			if (t instanceof LineWidthTeil) {
				if (t.equals(currentLineWidth))
					return;
				currentLineWidth = (LineWidthTeil) t;
			}
			if (t instanceof FontTeil) {
				if (t.equals(currentFont))
					return;
				currentFont = (FontTeil) t;
			}

			teile.add(t);
		}

		fireChanged();
		fireChanged(SkizzenEvent.ADDED, t);
	}

	public void removeElement(SkizzenTeil t) {
		synchronized (teile) {
			dirty = true;
			teile.remove(t);
		}

		fireChanged();
		fireChanged(SkizzenEvent.DELETED, t);

	}

	public FontRenderContext getFontContext() {
		return fontContext;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void cloneTeile() {
		synchronized (teile) {
			dirty = true;
			this.teile = (ArrayList<SkizzenTeil>) teile.clone();
		}
	}

	public void copy(Skizze s) {
		synchronized (teile) {
			dirty = true;
			this.name = s.name;
			this.teile = (ArrayList<SkizzenTeil>) s.teile.clone();
		}
		fireChanged();
	}

	public void clear() {
		synchronized (teile) {
			dirty = true;
			teile.removeAll(teile);
			teile = new ArrayList<SkizzenTeil>();

			currentColor = null;
			currentLineWidth = null;
			currentFont = null;
		}
		fireChanged();
	}

	public void removeElementAt(int n) {
		dirty = true;
		SkizzenTeil t = null;
		synchronized (teile) {
			t = teile.get(n);
			teile.remove(n);
		}

		fireChanged();
		fireChanged(SkizzenEvent.DELETED, t);
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
