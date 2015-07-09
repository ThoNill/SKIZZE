package transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import model.Skizze;
import model.teile.SkizzenTeil;
import action.ActionController;
import action.AddAction;
import action.SkizzeAction;

/**
 * Senden und Empfangen über das Netzwerk
 * 
 * @author Thomas Nill
 *
 */
public class OnlineActionController extends ActionController implements
		Runnable,ActionExecutor, SocketListener{

	private int port;

	private String host;

	private ServerSocket server;
	
	private ClientSocketConnection clientConnection;
	private ServerSocketConnection serverConnection;
	
	private OnlineAppPart app;

	private boolean isOn;

	public OnlineActionController(OnlineAppPart app) {
		super();
		this.app = app;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void perform(SkizzeAction a) {
		try {
			a.perform(model);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void run() {
		isOn = true;
		try {
			while (isOn) {
				if (server != null) {
					Socket socket = server.accept();

					ClientSocketConnection c = new ClientSocketConnection(socket,this,1);
					serverConnection.addClientSocket(c);
					c.start();
					
					aktuelleSkitzeSenden(c);
					
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			app.setOnline(false);
			app.setMessage(ex.getMessage());
		}

	}

	private void aktuelleSkitzeSenden(ClientSocketConnection c) {
		Skizze skitzze = app.getSModel();
		Vector<Object> actions = new Vector<>();
		for (SkizzenTeil teil : skitzze.getTeile()) {
			actions.add(new AddAction(teil));
		}
		serverConnection.write(actions, c);
	}


	public void addElement(SkizzeAction a) {
		debug("start addElement");
		perform(a);
		if (serverConnection != null) {
			serverConnection.writeObject(a);
		}
		if (clientConnection != null) {
			clientConnection.writeObject(a);
		}
		debug("stop addElement outActions");
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void start() {
		debug("start doStarte ");
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			clientConnection = new ClientSocketConnection(socket,this,2);
			clientConnection.addSocketListener(this);
			clientConnection.start();
			app.setText("Client");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			try {
				server = new ServerSocket(port);
				serverConnection = new ServerSocketConnection(this);
				serverConnection.addSocketListener(this);
				serverConnection.start();
				app.setText("Server");
				Thread t = new Thread(this);
				t.start();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		}
		debug("end doStart ");
	}

	public void close() {
		debug("start doSClose ");
		isOn = false;
		if (serverConnection != null) {
			serverConnection.close();
			try {
				if (server != null) {
					server.close();
					server = null;
				}
			} catch (Exception ex) {
			}
			server = null;
		}
		if (clientConnection != null) {
			clientConnection.close();
		}

		app.setText("Disconnected");
		debug("end doSClose ");
	}

	private void debug(String text) {
	//	System.out.println(((server != null) ? "server " : "client ") + text);

	}

	@Override
	public void perform(Object obj) {
		if (obj instanceof SkizzeAction) {
			perform((SkizzeAction)obj);
		}
		
	}
	
	@Override
	public void statusChanged(SocketEvent ev) {
		if (ev.getStatus() == SocketStatus.STOPPED) {
			close();
		}
		
	}
}