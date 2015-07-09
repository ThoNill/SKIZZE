package model;

import java.util.EventListener;


/**
 * 
 * Höhren auf Veränderungen in einer Skitze
 * 
 * @author Thomas Nill
 *
 */
public interface SkizzenListener extends EventListener {
	public void perform(Class cl, SkizzenEvent ev);
}
