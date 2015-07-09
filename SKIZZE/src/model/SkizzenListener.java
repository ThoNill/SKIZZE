package model;

import java.util.EventListener;


/**
 * 
 * H�hren auf Ver�nderungen in einer Skitze
 * 
 * @author Thomas Nill
 *
 */
public interface SkizzenListener extends EventListener {
	public void perform(Class cl, SkizzenEvent ev);
}
