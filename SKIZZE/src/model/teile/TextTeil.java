package model.teile;

import java.awt.Graphics2D;


/**
 * 
 * Einen Text ausgeben
 * 
 * @author Thomas Nill
 *
 */
public class TextTeil implements SkizzenTeil {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1240515803889811564L;

	private int x;

	private int y;

	private String text;

	public TextTeil() {
	}

	public TextTeil(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public void paint(Graphics2D g) {
		g.drawString(text, x, y);
	}

	public int diff(int x, int y) {
		return Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
