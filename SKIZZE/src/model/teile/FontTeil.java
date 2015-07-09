package model.teile;

import java.awt.Font;
import java.awt.Graphics2D;


/**
 * 
 * Font der folgenden Zeichen festlegen
 * 
 * @author Thomas Nill
 *
 */
public class FontTeil implements SkizzenTeil {
	private static final long serialVersionUID = -5645050578287296908L;

	private Font font;
	private float fontSize;
	private transient Font derivedFont;

	public FontTeil(Font font,float fontSize) {
		this.font = font;
		this.fontSize = fontSize;
		this.derivedFont = null;
	}

	public void paint(Graphics2D g) {
		if (derivedFont == null) {
			derivedFont =  font.deriveFont(fontSize);
		}
		g.setFont(derivedFont);
	}

	public Font getFont() {
		return font;
	}

	public float getFontSize() {
		return fontSize;
	}
}
