package parts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.SkizzenEvent;
import model.SkizzenListener;
import model.teile.ColorTeil;
import model.teile.LineTeil;
import model.teile.LineWidthTeil;
import model.teile.SkizzenTeil;
import nls.NLS;
import userinterface.LineWidthCooser;
import action.AddAction;
import action.EraseAction;

/**
 * Zeichnen der Liste und Mausbewegungen in Zechenaktionen umwandeln
 * 
 * @author Thomas Nill
 *
 */
public class DrawAppPart extends AppPart implements MouseListener,
        MouseMotionListener, ActionListener, ChangeListener, SkizzenListener {

    public final static int DRAW = 1;

    public final static int ERASE = 2;

    private LineWidthCooser lwChooser;

    private JSpinner lineWidth = null;

    private int status = DRAW;

    private JButton drawButton = null;

    private JButton colorButton = null;

    private int x = 0;

    private int y = 0;

    private boolean aktiv = false;

    private long q;

    public DrawAppPart(AppControler appc) {
        super(appc, "draw");
    }

    @Override
    public void addComponents() {
        JPanel p = new JPanel();
        lwChooser = new LineWidthCooser();
        p.add(lwChooser);
        lineWidth = new JSpinner();
        lineWidth.getModel().setValue(new Integer(1));
        lineWidth.addChangeListener(this);
        Dimension s = lineWidth.getPreferredSize();
        s.width = 40;
        lineWidth.setMinimumSize(s);
        lineWidth.setPreferredSize(s);
        lineWidth.setSize(s);
        p.add(lineWidth);

        colorButton = new JButton();
        colorButton.setText(NLS.getText("color"));
        colorButton.setActionCommand("color");
        colorButton.addActionListener(this);
        p.add(colorButton);

        drawButton = new JButton();
        drawButton.setActionCommand("draw");
        drawButton.addActionListener(this);
        p.add(drawButton);

        setStatus(DRAW);

        getAppc().getUi().getInternToolBar().add(p);
        getAppc().getUi().getSkitze().addMouseListener(this);
        getAppc().getUi().getSkitze().addMouseMotionListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {

        if (arg0.getButton() == MouseEvent.BUTTON1) {
            aktiv = true;
            x = arg0.getX();
            y = arg0.getY();
            q = quad(x, y);
        }

    }

    private long quad(long x, long y) {
        return x * x + y * y;
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        if (aktiv) {
            int x1 = arg0.getX();
            int y1 = arg0.getY();
            long q1 = quad(x1, y1);
            if (Math.abs(q1 - q) > 500) {
                if (status == DRAW) {
                    SkizzenTeil t = new LineTeil(x, y, x1, y1);
                    getAppc().getActionControler().addElement(new AddAction(t));
                } else {
                    getAppc().getActionControler().addElement(
                            new EraseAction(x - 5, y - 5, 10));
                }

                x = x1;
                y = y1;
                q = quad(x, y);
            }
        }

    }

    public void setStatus(int status) {
        this.status = status;
        if (status == ERASE) {
            drawButton.setText(NLS.getText("erase"));
        }
        if (status == DRAW) {
            drawButton.setText(NLS.getText("draw"));
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if ("color".equals(arg0.getActionCommand())) {
            Color color = JColorChooser.showDialog(getAppc().getUi(),
                    NLS.getText("Farbauswahl"), lwChooser.getColor());
            lwChooser.setChooserColor(color);
            getAppc().getActionControler().addElement(
                    new AddAction(getColorTeil()));
        }
        if ("draw".equals(arg0.getActionCommand())) {
            setStatus((status == DRAW) ? ERASE : DRAW);
        }

    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        Object o = lineWidth.getModel().getValue();

        float width = Float.parseFloat(o.toString());
        if (width < 0.1) {
            setLineWidth(1.0f);
        } else {
            lwChooser.setLineWidth(width);
            getAppc().setLineWithTeil(getLineWidthTeil());
        }
    }

    public ColorTeil getColorTeil() {
        return new ColorTeil(lwChooser.getColor());
    }

    public LineWidthTeil getLineWidthTeil() {
        return new LineWidthTeil(lwChooser.getLineWidth());
    }

    @Override
    public void perform(Class cl, SkizzenEvent ev) {
        if (ev.getStatus() == SkizzenEvent.ADDED && cl == LineWidthTeil.class) {
            LineWidthTeil t = (LineWidthTeil) ev.getPart();
            float width = t.getWidth();
            if (width < lwChooser.getLineWidth() - 0.01
                    || width > lwChooser.getLineWidth() + 0.01) {
                setLineWidth(width);
            }
        }
        if (ev.getStatus() == SkizzenEvent.ADDED && cl == ColorTeil.class) {
            ColorTeil t = (ColorTeil) ev.getPart();
            Color c = t.getColor();
            lwChooser.setChooserColor(c);
        }

    }

    public void setLineWidth(float startWidth) {
        float width = startWidth;
        if (width <= 0.0f) {
            width = 1.0f;
        }
        lineWidth.getModel().setValue(new Integer((int) width));
    }
}