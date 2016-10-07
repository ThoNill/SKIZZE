package action;

import java.io.Serializable;

import model.Skizze;

/**
 * 
 * Eine Aktion auf dem Model Skitze (Command Pattern)
 * 
 * @author Thomas Nill
 *
 */
public interface SkizzeAction extends Serializable {
    public void perform(Skizze model);
}
