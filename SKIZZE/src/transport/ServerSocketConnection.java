package transport;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSocketConnection extends SocketConnection implements SocketListener {

	private CopyOnWriteArrayList<ClientSocketConnection> clients = new CopyOnWriteArrayList<ClientSocketConnection>();

	
	public ServerSocketConnection(ActionExecutor oac) {
		super(oac);
	}

	private CopyOnWriteArrayList<ClientSocketConnection> getClients() {
		if (clients == null) {
			clients = new CopyOnWriteArrayList<ClientSocketConnection>();
		}
		return clients;
	}
	

	public void addClientSocket(ClientSocketConnection client) {
		getClients().add(client);
		client.addSocketListener(this);
	}

	public void removeClientSocket(SocketConnection client) {
		getClients().remove(client);
		client.removeSocketListener(this);
	}
 

	private void writeObject(Object obj,SocketConnection verboten) {
		for (ClientSocketConnection client : getClients()) {
			if (!verboten.equals(client)) {
				client.writeObject(obj);
			}
		}
	}
	
	public void write(Vector<Object> objects, SocketConnection client) {
		for(Object obj : objects) {
			client.writeObject(obj);
		}
	}
	
	@Override
	public void writeObject(Object obj) {
		for (ClientSocketConnection client : getClients()) {
			client.writeObject(obj);
		}
	}
	
	private void readObjects(ClientSocketConnection client) throws ClassNotFoundException, IOException {
		Object obj = client.readObject();
		while(obj != null) {
			writeObject(obj, client);
			perform(obj);
			obj = client.readObject();
		}

	}
	
	@Override
	protected void read() throws ClassNotFoundException, IOException{
		for (ClientSocketConnection client : getClients()) {
			readObjects(client);
		}
	}


	@Override
	public void statusChanged(SocketEvent ev) {
		if (ev.getStatus() == SocketStatus.STOPPED) {
			removeClientSocket(ev.getSocket());
		}
		
	}

	@Override
	public void close() {
		super.close();
		for (ClientSocketConnection client : getClients()) {
			client.close();
		}	
	}
	
	protected void debug(String text) {
	//	System.out.println("server " + text);
	
	}

}
