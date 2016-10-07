package action;

import model.Skizze;

/**
 * 
 * Verbindet eine Aktion mit dem zugehörigen Skitze als Model
 * 
 * @author Thomas Nill
 *
 */
public class ActionController {

    private static final long serialVersionUID = -3511719626701471504L;

    protected Skizze model;

    public ActionController() {

    }

    public void addElement(SkizzeAction a) {
        a.perform(model);
    }

    public Skizze getModel() {
        return model;
    }

    public void setModel(Skizze model) {
        this.model = model;
    }

    public void close() {
    }

    public void start() {
    }
}
