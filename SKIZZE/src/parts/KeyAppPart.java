package parts;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.SkizzenEvent;
import model.SkizzenListener;
import model.teile.FontTeil;
import model.teile.SkizzenTeil;
import model.teile.TextTeil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import userinterface.FontChooser;
import action.AddAction;
import action.DelKeyAction;

/**
 * eingegebene Zeichen abfangen und in die Skitze einfügen
 * 
 * @author Thomas Nill
 *
 */
public class KeyAppPart extends AppPart implements MouseListener, KeyListener,
        SkizzenListener, PropertyChangeListener {
    private static final Logger LOG = LogManager.getLogger(KeyAppPart.class);

    private FontChooser fontChooser;

    private int x = 0;

    private int x0 = 0;

    private int y = 0;

    private int y0 = 0;

    public KeyAppPart(AppControler appc) {
        super(appc, "key");
    }

    @Override
    public void addComponents() {
        fontChooser = new FontChooser();
        fontChooser.addPropertyChangeListener(this);
        getUi().getInternToolBar().add(fontChooser);
        getSkitze().addMouseListener(this);
        getSkitze().addKeyListener(this);
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
            x0 = arg0.getX();
            y0 = arg0.getY();
            x = x0;
            y = y0;
            getUi().focusSkitze();
        }

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        try {
            char c = arg0.getKeyChar();
            SkizzenTeil t = null;

            Font font = fontChooser.getChooseFont();
            font = font.deriveFont(fontChooser.getFontSize());

            if (c == KeyEvent.VK_ENTER) {
                Rectangle2D r = font.getStringBounds("X", getAppc().getModel()
                        .getFontContext());
                y += r.getHeight();
                x = x0;
            } else if (c == KeyEvent.VK_BACK_SPACE) {
                getAppc().getActionControler().addElement(new DelKeyAction());
            } else {
                String text = new String(new char[] { c });

                t = new TextTeil(x, y, text);

                Rectangle2D r = font.getStringBounds(text, getAppc().getModel()
                        .getFontContext());
                x += r.getWidth();
                getAppc().getActionControler().addElement(new AddAction(t));
            }

        } catch (Exception ex) {
            LOG.error("Key Eingabe mit Ausnahme", ex);
        }

    }

    public FontTeil getFontTeil() {
        return new FontTeil(fontChooser.getChooseFont(),
                fontChooser.getFontSize());
    }

    @Override
    public void perform(Class cl, SkizzenEvent ev) {
        SkizzenTeil t = ev.getPart();
        if (ev.getStatus() == SkizzenEvent.DELETED && cl == TextTeil.class) {
            TextTeil textTeil = (TextTeil) t;
            x = textTeil.getX();
            y = textTeil.getY();
        }
        if (ev.getStatus() == SkizzenEvent.ADDED && cl == FontTeil.class) {
            FontTeil fontTeil = (FontTeil) t;
            fontChooser.setChooseFont(fontTeil);

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        getAppc().setFontTeil(getFontTeil());

    }

}
