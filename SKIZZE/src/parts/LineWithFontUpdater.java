package parts;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import model.teile.FontTeil;
import model.teile.LineWidthTeil;
import action.AddAction;
import action.SkizzeAction;

public class LineWithFontUpdater extends AppPart implements MouseListener {

    private List<SkizzeAction> actions = new ArrayList<>();

    boolean inUIArea = false;

    LineWidthTeil linewidthTeil = null;
    FontTeil fontTeil = null;

    public LineWithFontUpdater(AppControler appc) {
        super(appc, "ineWithFontUpdater");
        getUi().getSkitzeComponentr().addMouseListener(this);
    }

    @Override
    public void addComponents() {
    }

    public void setLineWithTeil(LineWidthTeil t) {
        if (!inUIArea) {
            linewidthTeil = t;
        }

    }

    public void setFontTeil(FontTeil t) {
        if (!inUIArea) {
            fontTeil = t;
        }

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        inUIArea = true;

        if (linewidthTeil != null) {
            getAppc().getActionControler().addElement(
                    new AddAction(linewidthTeil));
            linewidthTeil = null;
        }
        if (fontTeil != null) {
            getAppc().getActionControler().addElement(new AddAction(fontTeil));
            fontTeil = null;
        }

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        inUIArea = false;

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

}
