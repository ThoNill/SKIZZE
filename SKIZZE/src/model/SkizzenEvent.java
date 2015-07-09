package model;

import model.teile.SkizzenTeil;


/**
 * 
 * Nachrichten über die Änderung eines Models
 * werden vom Model versendet
 * 
 * @author Thomas Nill
 *
 */
public class SkizzenEvent {
	public final static int ADDED = 1;

	public final static int DELETED = 2;

	public final static int CHANGED = 3;
	
	public final static int UPDATESTATUS = 4;

	int status;

	SkizzenTeil part;

	public SkizzenEvent(int status, SkizzenTeil part) {
		this.status = status;
		this.part = part;
	}

	public SkizzenTeil getPart() {
		return part;
	}

	public int getStatus() {
		return status;
	}

}
