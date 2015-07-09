package action;

import model.Skizze;


/**
 * 
 * Kopieren einer Skitze in eine andere
 * 
 * @author Thomas Nill
 *
 */
public class TransferAction implements SkizzeAction {
	private static final long serialVersionUID = 1328111238820609148L;

	private Skizze skitzze;

	public TransferAction(Skizze skitzze) {
		this.skitzze = skitzze;
	}

	public void perform(Skizze model) {
		if (model != skitzze)
			model.copy(skitzze);
	}

}
