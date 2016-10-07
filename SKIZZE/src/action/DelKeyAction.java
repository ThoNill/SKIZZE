package action;

import java.util.ArrayList;

import model.Skizze;
import model.teile.SkizzenTeil;
import model.teile.TextTeil;

/**
 * 
 * Aus einem Text ein Zeichen entfernen
 * 
 * @author Thomas Nill
 *
 */
public class DelKeyAction implements SkizzeAction {

    private static final long serialVersionUID = 7595492666562628213L;

    @Override
    public void perform(Skizze model) {

        ArrayList<SkizzenTeil> teile = model.getTeile();
        synchronized (teile) {

            int anz = teile.size();
            while (anz > 0) {
                int index = anz - 1;
                SkizzenTeil sk = teile.get(index);
                if (sk instanceof TextTeil) {
                    model.removeElementAt(index);
                    return; // Ein Zeichen wurde gefunden, d.h. Arbeit erledigt
                }
            }
        }

    }

}
