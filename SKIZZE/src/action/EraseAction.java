package action;

import java.util.ArrayList;
import java.util.Vector;

import model.Skizze;
import model.teile.LineTeil;
import model.teile.SkizzenTeil;
import model.teile.TextTeil;

/**
 * 
 * Radierer, l�schen von Zeichen und Linien
 * 
 * @author Thomas Nill
 *
 */
public class EraseAction implements SkizzeAction {
	private int x;

	private int y;

	private int diff;

	private static final long serialVersionUID = 5983406109735912837L;

	public EraseAction(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.diff = d;
	}

	public void perform(Skizze model) {
		ArrayList<SkizzenTeil> teile = model.getTeile();
		int index = 0;
		synchronized (teile) {

			while (index < teile.size()) {
				SkizzenTeil sk = teile.get(index);
				if (sk instanceof TextTeil) {
					if (((TextTeil) sk).diff(x, y) <= diff) {
						model.removeElementAt(index);
						index--; // einen Schritt zur�ck, da gel�scht wurde
					}
				}
				if (sk instanceof LineTeil) {
					if (((LineTeil) sk).diff(x, y) <= diff) {
						model.removeElementAt(index);
						index--; // einen Schritt zur�ck, da gel�scht wurde
					}
				}
				index++;
			}
		}

	}

}
