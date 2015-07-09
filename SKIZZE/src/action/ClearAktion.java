package action;

import model.Skizze;

/**
 * 
 * Skitze leeren
 * 
 * @author Thomas Nill
 *
 */
public class ClearAktion implements SkizzeAction {

	private static final long serialVersionUID = -6291680806478652222L;

	public void perform(Skizze model) {
		model.clear();
	}

}
