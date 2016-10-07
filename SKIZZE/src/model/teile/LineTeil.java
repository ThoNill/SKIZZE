package model.teile;

import java.awt.Graphics2D;

/**
 * 
 * Eine Linie zeichnen
 * 
 * @author Thomas Nill
 *
 */
public class LineTeil implements SkizzenTeil {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1240515803889811564L;

    private int x0;

    private int y0;

    private int x1;

    private int y1;

    public LineTeil() {
    }

    public LineTeil(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawLine(x0, y0, x1, y1);
    }

    public int diff(int x, int y) {
        return Math.max(Math.abs(this.x0 - x), Math.abs(this.y0 - y));
    }

}
