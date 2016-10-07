package transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.Skizze;
import model.teile.SkizzenTeil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
        Runnable, ActionExecutor, SocketListener {
    private static final Logger LOG = LogManager
            .getLogger(OnlineActionController.class);

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
            LOG.error("Exception in perform", ex);
        }
    }

    @Override
    public void run() {
        isOn = true;
        try {
            while (isOn) {
                if (server != null) {
                    Socket socket = server.accept();

                    ClientSocketConnection c = new ClientSocketConnection(
                            socket, this, 1);
                    serverConnection.addClientSocket(c);
                    c.start();

                    aktuelleSkitzeSenden(c);

                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    LOG.error("Interrupt in run", e);
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception ex) {
            LOG.error("Exception in run", ex);
            app.setOnline(false);
            app.setMessage(ex.getMessage());
        }

    }

    private void aktuelleSkitzeSenden(ClientSocketConnection c) {
        Skizze skitzze = app.getSModel();
        List<Object> actions = new ArrayList<>();
        for (SkizzenTeil teil : skitzze.getTeile()) {
            actions.add(new AddAction(teil));
        }
        serverConnection.write(actions, c);
    }

    @Override
    public void addElement(SkizzeAction a) {
        LOG.debug("start addElement");
        perform(a);
        if (serverConnection != null) {
            serverConnection.writeObject(a);
        }
        if (clientConnection != null) {
            clientConnection.writeObject(a);
        }
        LOG.debug("stop addElement outActions");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void start() {
        LOG.debug("start doStarte ");
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            clientConnection = new ClientSocketConnection(socket, this, 2);
            clientConnection.addSocketListener(this);
            clientConnection.start();
            app.setText("Client");
        } catch (UnknownHostException e) {
            LOG.error("Exception in start", e);
            return;
        } catch (IOException e) {
            try {
                LOG.error("IOException in start", e);
                server = new ServerSocket(port);
                serverConnection = new ServerSocketConnection(this);
                serverConnection.addSocketListener(this);
                serverConnection.start();
                app.setText("Server");
                Thread t = new Thread(this);
                t.start();
            } catch (IOException e1) {
                LOG.error("Exception in start", e1);
                return;
            }
        }
        LOG.debug("end doStart ");
    }

    @Override
    public void close() {
        LOG.debug("start doSClose ");
        isOn = false;
        if (serverConnection != null) {
            serverConnection.close();
            try {
                if (server != null) {
                    server.close();
                    server = null;
                }
            } catch (Exception ex) {
                LOG.error("Exception in close", ex);
            }
            server = null;
        }
        if (clientConnection != null) {
            clientConnection.close();
        }

        app.setText("Disconnected");
        LOG.debug("end doSClose ");
    }

    @Override
    public void perform(Object obj) {
        if (obj instanceof SkizzeAction) {
            perform((SkizzeAction) obj);
        }

    }

    @Override
    public void statusChanged(SocketEvent ev) {
        if (ev.getStatus() == SocketStatus.STOPPED) {
            close();
        }

    }
}