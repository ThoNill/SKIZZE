package model.teile;

import java.awt.Color;
import java.awt.Graphics2D;


/**
 * 
 * Farbe der nachfolgenden SkitzenTeile festlegen
 * 
 * @author Thomas Nill
 *
 */
public class ColorTeil implements SkizzenTeil {

	private static final long serialVersionUID = -1825338898414339779L;

	private Color color;

	public ColorTeil() {
	}

	public ColorTeil(Color color) {
		this.color = color;
	}

	public void paint(Graphics2D g) {
		g.setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
