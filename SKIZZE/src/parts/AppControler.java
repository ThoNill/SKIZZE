package parts;

import io.Datenbank;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import model.Skizze;
import model.SkizzenEvent;
import model.SkizzenListener;
import model.teile.FontTeil;
import model.teile.LineWidthTeil;
import model.teile.SkizzenTeil;
import userinterface.UserInterface;
import action.ActionController;
import action.AddAction;
import action.ClearAktion;

import com.db4o.ObjectContainer;

/**
 * 
 * Ein Controler, der aus verschiedenen Teilen {@link AppPart} besteht 
 * und auf Änderungendas des Model {@link Skizze} hört
 * 
 * @author Thomas Nill
 *
 */

public class AppControler implements SkizzenListener {

	private ActionController actionControler;

	private UserInterface ui;

	private Skizze model;

	private Vector<AppPart> parts = new Vector<AppPart>();

	private Vector<SkizzenListener> l = new Vector<SkizzenListener>();

	public AppControler() {
		setModel(getModel());
	}

	public ActionController getActionControler() {
		if (actionControler == null) {
			actionControler = new ActionController();
			actionControler.setModel(model);
		}
		return actionControler;
	}

	public void setActionControler(ActionController actionControler) {
		actionControler.setModel(model);
		this.actionControler = actionControler;
		this.actionControler.start();
	}

	public UserInterface getUi() {
		return ui;
	}

	public void setUi(UserInterface ui) {
		this.ui = ui;
		connectUiAndModel();
	}

	private void connectUiAndModel() {
		if (this.ui != null && this.model != null) {
			ui.setModel(model);
		}
	}

	public void addPart(AppPart part) {
		parts.add(part);
		part.addComponents();
		if (part instanceof SkizzenListener) {
			l.addElement((SkizzenListener) part);
		}
	}

	public void removePart(AppPart part) {
		if (part instanceof SkizzenListener) {
			l.removeElement((SkizzenListener) part);
		}
		part.close();
		parts.remove(part);
	}

	public void close() {
		Iterator<AppPart> i = parts.iterator();
		while (i.hasNext()) {
			i.next().close();
		}
		parts.removeAllElements();
	}
	
	public AppPart find(Class cl) {
		Iterator<AppPart> i = parts.iterator();
		while (i.hasNext()) {
			AppPart a = i.next();
			if (cl.equals(a.getClass()))
				return a;
		}
		return null;
	}

	public Skizze getModel() {
		if (model == null) {
			return new Skizze();
		}
		return model;
	}

	public void setModel(Skizze model) {
		if (this.model == model)
			return;

		if (this.model != null) {
			model.removeSkitzenListener(this);
			ObjectContainer db = Datenbank.getContainer();
			db.deactivate(this.model, 10);
		}
		;
		this.model = model;
		if (actionControler != null)
			actionControler.setModel(model);
		
		connectUiAndModel();
	
		model.addSkitzenListener(this);
		updateChoosersInUI();
	}

	public void updateChoosersInUI() {
		HashMap<Class,SkizzenTeil> map = model.getLastTeile();
		for(Class c : map.keySet()) {
			perform(c,new SkizzenEvent(SkizzenEvent.ADDED, map.get(c)));
		}
	}

	public void setOnline(boolean b) {
	}

	public void perform(Class cl, SkizzenEvent ev) {
		Iterator<SkizzenListener> i = l.iterator();
		while (i.hasNext()) {
			SkizzenListener sl = i.next();
			sl.perform(cl, ev);
		}

	}

	public boolean safe() {
		boolean ok = true;
		Iterator<AppPart> i = parts.iterator();
		while (i.hasNext()) {
			ok = ok && i.next().safe();
		}
		return ok;
	}

	public boolean isDirty() {
		Iterator<AppPart> i = parts.iterator();
		while (i.hasNext()) {
			if (i.next().isDirty()) {
				return true;
			}
		}
		return false;
	}
	
	public void clearTheModelAction() {
		getActionControler().addElement(new ClearAktion());
		DrawAppPart dp = (DrawAppPart)find(DrawAppPart.class);
		getActionControler().addElement(new AddAction(dp.getLineWidthTeil()));
		getActionControler().addElement(new AddAction(dp.getColorTeil()));
		KeyAppPart kp = (KeyAppPart)find(KeyAppPart.class);
		getActionControler().addElement(new AddAction(kp.getFontTeil()));
	}
	
	public void setLineWithTeil(LineWidthTeil t) {
		LineWithFontUpdater lfa = (LineWithFontUpdater)find(LineWithFontUpdater.class);
		if (lfa != null) {
			lfa.setLineWithTeil(t);
		}
	}
	
	public void setFontTeil(FontTeil t) {
		LineWithFontUpdater lfa = (LineWithFontUpdater)find(LineWithFontUpdater.class);
		if (lfa != null) {
			lfa.setFontTeil(t);
		}
	}
	


}
