package transport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Senden und Empfangen über das Netzwerk
 * 
 * @author Thomas Nill
 *
 */
public class ClientSocketConnection extends SocketConnection implements
        Runnable {
    private static final Logger LOG = LogManager
            .getLogger(ClientSocketConnection.class);

    private ObjectInputStream in;

    private ObjectOutputStream out;

    private Socket socket;

    private int type = 1;

    public ClientSocketConnection(Socket socket, ActionExecutor oac, int type) {
        super(oac);
        this.type = type;
        setSocket(socket);
    }

    public void setSocket(Socket socket) {
        try {

            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(50);
        } catch (IOException e) {
            LOG.error("Exception in setSocket", e);
        }
    }

    @Override
    protected void read() throws IOException, ClassNotFoundException {
        Object o = readObject();
        while (o != null) {
            LOG.debug("READ " + o);
            perform(o);
            o = readObject();
        }
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        if (socket == null) {
            return null;
        }
        if (in == null) {
            return null;
        }

        synchronized (socket) {
            try {
                return in.readObject();
            } catch (SocketTimeoutException ex) {
                LOG.error("Exception in readObject", ex);
                return null;
            }
        }
    }

    @Override
    public void writeObject(Object a) {
        if (socket == null)
            return;
        if (out == null)
            return;
        try {
            synchronized (socket) {
                LOG.debug("WRITE " + a);
                out.writeObject(a);
                out.flush();
            }
        } catch (IOException e) {
            LOG.error("Exception in writeObject", e);
            close();
        }
    }

    @Override
    public void close() {
        LOG.debug("start doSClose ");
        super.close();
        try {
            if (socket != null) {
                synchronized (socket) {
                    in.close();
                    out.close();
                    socket.close();
                    in = null;
                    out = null;
                }
                socket = null;
            }
        } catch (Exception ex) {
            LOG.error("Exception in close", ex);
        }

    }

    @Override
    protected void sleepALittle() throws InterruptedException {
        Thread.sleep((type == 2) ? 200 : 200);
    }
}