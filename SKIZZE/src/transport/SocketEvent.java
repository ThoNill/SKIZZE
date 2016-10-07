package transport;

public class SocketEvent {
    private SocketStatus status;
    private SocketConnection socket;

    public SocketEvent(SocketStatus status, SocketConnection socket) {
        super();
        this.status = status;
        this.socket = socket;
    }

    public SocketStatus getStatus() {
        return status;
    }

    public SocketConnection getSocket() {
        return socket;
    }

}
