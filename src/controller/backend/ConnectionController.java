package controller.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import middleware.Client;
import middleware.Server;

/**
 *
 * @author Uellington Conceição
 */
public class ConnectionController {

    private final Server server;
    private final Client client;

    public ConnectionController() {
        this.server = new Server();
        this.client = new Client();
    }

    public Server getServer(){
        return this.server;
    }
    
    public Client getClient() {
        return this.client;
    }

    public void startServer(int port) throws IOException {
        this.server.start(port);
    }

    public void startClient(String ip, int port) throws IOException {
        this.client.start(ip, port);
    }

    public void closeServer() throws IOException {
        this.server.close();
    }

    public void closeClient() throws IOException {
        this.client.close();
    }

    public int getConnections() {
        return this.server.getConnections();
    }

    public String getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public boolean clientIsOnline() {
        return this.client.isOnline();
    }

    public boolean serverIsOnline() {
        return this.server.isOnline();
    }

    public void sendToServer(String message) throws IOException {
        this.client.send(message);
    }

    public void notifiyAllClient(String message) {
        this.server.notifyAll(message);
    }

    public void startAll(String clientIp, int clientPort, int serverPort) throws IOException {
        this.startServer(serverPort);
        this.startClient(clientIp, clientPort);
    }

    public void closeAll() throws IOException {
        this.closeClient();
        this.closeServer();
    }
}
