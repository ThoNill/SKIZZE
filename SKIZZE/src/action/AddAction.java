package action;

import model.Skizze;
import model.teile.SkizzenTeil;

/**
 * 
 * Ein Teil zu einer Skitze hinzufügen
 * 
 * @author Thomas Nill
 *
 */
public class AddAction implements SkizzeAction {

    private static final long serialVersionUID = -8122990391075266896L;

    private SkizzenTeil teil;

    public AddAction(SkizzenTeil teil) {
        this.teil = teil;
    }

    @Override
    public void perform(Skizze model) {
        model.addElement(teil);
    }

}
