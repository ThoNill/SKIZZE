package model.teile;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * 
 * Strichbreite der folgenden Linien festlegen
 * 
 * @author Thomas Nill
 *
 */
public class LineWidthTeil implements SkizzenTeil {
    private static final long serialVersionUID = 4633408156094983803L;

    private float width;

    transient Stroke stroke;

    public LineWidthTeil() {
    }

    public LineWidthTeil(float width) {
        this.width = width;
    }

    @Override
    public void paint(Graphics2D g) {
        if (stroke == null) {
            stroke = new BasicStroke(width);
        }
        g.setStroke(stroke);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        stroke = new BasicStroke(width);
    }

}
