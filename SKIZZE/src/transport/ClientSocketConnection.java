package transport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Senden und Empfangen über das Netzwerk
 * 
 * @author Thomas Nill
 *
 */
public class ClientSocketConnection extends SocketConnection implements
		Runnable {

	private ObjectInputStream in;

	private ObjectOutputStream out;

	private Socket socket;
	
	private int type = 1;
	
	public ClientSocketConnection(Socket socket, ActionExecutor oac,int type) {
		super(oac);
		this.type = type;
		setSocket(socket);
	}

	public void setSocket(Socket socket) {
		try {
		//if (type == 2) {
				socket.setKeepAlive(true);
		//	}
			this.socket = socket;
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			socket.setSoTimeout(50);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void read() throws IOException, ClassNotFoundException {
		Object o = readObject();
		while (o != null) {
			debug("READ " + o);
			perform(o);
			o = readObject();
		}
	}

	public Object readObject() throws ClassNotFoundException, IOException {
		if (socket == null)
			return null;
		if (in == null)
			return null;
		
			// if (in.available()==0) return null;
			try {
				synchronized (socket) {
					return in.readObject();
				}
			} catch(SocketTimeoutException ex) {
				return null;
			}
	}

	public void writeObject(Object a) {
		if (socket == null)
			return;
		if (out == null)
			return;
		try {
			synchronized (socket) {
				debug("WRITE " + a);
				out.writeObject(a);
				out.flush();
			}
		} catch (IOException e) {
			e.getStackTrace();
			debug(e.getMessage());
			close();
		}
	}

	public void close() {
		debug("start doSClose ");
		super.close();
		try {
			if (socket != null) {
				synchronized (socket) {
					in.close();
					out.close();
					socket.close();
					in = null;
					out = null;
					socket = null;
				}
			}
		} catch (Exception ex) {
			ex.getStackTrace();
			debug(ex.getMessage());
		}
		debug("end doSClose ");
	}
	
	protected void debug(String text) {
	//	System.out.println("client type " + type + " "+ text);
	
	}
	
	protected void sleepALittle() throws InterruptedException {
		Thread.sleep((type==2) ? 200 : 200);
	}
}