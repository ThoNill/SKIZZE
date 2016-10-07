package transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class SocketConnection implements Runnable {
    private static final Logger LOG = LogManager
            .getLogger(SocketConnection.class);

    protected ActionExecutor oac;
    protected SocketStatus status;
    private List<SocketListener> listener = new ArrayList<>();

    public SocketConnection(ActionExecutor oac) {
        super();
        this.oac = oac;
        this.status = SocketStatus.CREATED;
    }

    public void addSocketListener(SocketListener l) {
        listener.add(l);
        l.statusChanged(new SocketEvent(this.status, this));
    }

    public void removeSocketListener(SocketListener l) {
        listener.remove(l);
    }

    public void fire(SocketEvent ev) {
        for (SocketListener l : listener) {
            l.statusChanged(ev);
        }
    }

    public void setStatus(SocketStatus status) {
        if (this.status.equals(status))
            return;

        this.status = status;
        fire(new SocketEvent(this.status, this));
    }

    public void perform(Object a) {
        oac.perform(a);
    }

    @Override
    public void run() {
        setStatus(SocketStatus.RUNNING);
        try {
            while (status == SocketStatus.RUNNING) {
                try {
                    sleepALittle();
                } catch (InterruptedException e) {
                    LOG.error("Interrupt in run", e);
                    Thread.currentThread().interrupt();
                }
                read();
            }
        } catch (Exception ex) {
            LOG.error("Exception in run", ex);
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

    public void close() {
        setStatus(SocketStatus.STOPPED);
    }

    public abstract void writeObject(Object a);

    protected void read() throws IOException, ClassNotFoundException {
    };

}