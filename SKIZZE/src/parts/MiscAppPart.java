package parts;

import java.awt.event.ActionEvent;

import print.PrintJob;

/**
 * Auf sonstige Kommandos (Druck und Löschen der Skitze) hören und ausführen
 * 
 * @author Thomas Nill
 *
 */
public class MiscAppPart extends AppPart {

    public MiscAppPart(AppControler appc) {
        super(appc, "misc");
    }

    @Override
    public void addComponents() {
        addMenu("clear");
        addMenu("print");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String command = arg0.getActionCommand();

        if ("clear".equals(command)) {
            clear();
        }
        if ("print".equals(command)) {
            new PrintJob(getAppc().getModel());
        }
    }

    public void clear() {

        getAppc().clearTheModelAction();

    }

}
