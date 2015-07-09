package action;

import model.Skizze;
import model.teile.SkizzenTeil;


/**
 * 
 * Ein Teil entfernen
 * 
 * @author Thomas Nill
 *
 */
public class RemoveAction implements SkizzeAction {

	private static final long serialVersionUID = -8122990391075266896L;

	private SkizzenTeil teil;

	public RemoveAction(SkizzenTeil teil) {
		this.teil = teil;
	}

	public void perform(Skizze model) {
		model.removeElement(teil);
	}

}
