package model.teile;

import java.awt.Graphics2D;
import java.io.Serializable;


/**
 * 
 * Teil einer @link Skitze}
 * 
 * @author Thomas Nill
 *
 */
public interface SkizzenTeil extends Serializable {
	public void paint(Graphics2D g);
}
