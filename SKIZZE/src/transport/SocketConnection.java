package transport;

import java.io.IOException;
import java.util.Vector;

public abstract class SocketConnection  implements Runnable{

	protected ActionExecutor oac;
	protected SocketStatus status;
	private Vector<SocketListener> listener = new Vector<>();

	public SocketConnection(ActionExecutor oac) {
		super();
		this.oac = oac;
		this.status = SocketStatus.CREATED;
	}

	public void addSocketListener(SocketListener l) {
		listener.addElement(l);
		l.statusChanged(new SocketEvent(this.status,this));
	}

	public void removeSocketListener(SocketListener l) {
		listener.removeElement(l);
	}

	public void fire(SocketEvent ev) {
		for (SocketListener l : listener) {
			l.statusChanged(ev);
		}
	}

	public void setStatus(SocketStatus status) {
		if (this.status.equals(status)) return;
		
		this.status = status;
		fire(new SocketEvent(this.status,this));
	}

	public void perform(Object a) {
		oac.perform(a);
	}

	public void run() {
		setStatus(SocketStatus.RUNNING);
		try {
			while (status == SocketStatus.RUNNING) {
				try {
					sleepALittle();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				read();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		close();
	}

	protected void sleepALittle() throws InterruptedException {
		Thread.sleep(200);
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
		return;
	}

	protected void debug(String text) {
	//	System.out.println("server client " + text);
	
	}
	
	public void close() {
		setStatus(SocketStatus.STOPPED);
	}
	
	public abstract void writeObject(Object a);
	protected void read() throws IOException, ClassNotFoundException {};

}