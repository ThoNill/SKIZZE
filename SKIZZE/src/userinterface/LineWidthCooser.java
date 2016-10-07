package userinterface;

import gui.SkizzeComponent;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.BevelBorder;

import model.Skizze;
import model.teile.ColorTeil;
import model.teile.LineTeil;
import model.teile.LineWidthTeil;

public class LineWidthCooser extends SkizzeComponent {

    private static final long serialVersionUID = 3463897248813425248L;

    private float width;
    private Color color;

    public LineWidthCooser() {
        super();
        color = Color.BLACK;
        width = 1.0f;
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setMinimumSize(new Dimension(40, 20));
        setPreferredSize(new Dimension(40, 20));
        setSize(new Dimension(40, 20));
        addToSkitze();
    }

    public void addToSkitze() {
        setModel(new Skizze());
        ColorTeil colorTeil = new ColorTeil(color);
        LineWidthTeil linewidthTeil = new LineWidthTeil(width);
        getModel().addElement(colorTeil);
        getModel().addElement(linewidthTeil);
        Dimension dim = getSize();
        getModel().addElement(
                new LineTeil(0, dim.height / 2, dim.width, dim.height / 2));
    }

    public void setChooserColor(Color color) {
        if (this.color.equals(color))
            return;
        this.color = color;
        addToSkitze();
        invalidate();
        updateUI();
    }

    public void setLineWidth(float width) {
        if (width < 0.1f) {
            return;
        }

        this.width = width;
        addToSkitze();
        invalidate();
        updateUI();
    }

    public float getLineWidth() {
        return width;
    }

    public Color getColor() {
        return color;
    }

}
