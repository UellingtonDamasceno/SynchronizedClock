package middleware;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uellington Damasceno
 */
public class Server implements Runnable {

    private ServerSocket server;
    private Map<String, Client> clients;
    private boolean online;

    public Server() {
        this.clients = new HashMap();
        this.online = false;
    }

    public boolean isOnline() {
        return this.online;
    }

    public int getConnections() {
        return this.clients.size();
    }

    public Client remove(String ip) {
        return this.clients.remove(ip);
    }

    public void start(int port) throws IOException {
        if (!this.online) {
            this.server = new ServerSocket(port);
            new Thread(this).start();
        }
    }

    public void close() throws IOException {
        if (this.online) {
            this.online = false;
            this.server.close();
        }
    }

    public void notifyAll(String message) {
        this.clients.forEach((ip, client) -> {
            try {
                System.out.println("Notificando o client: " + ip);
                client.send(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void run() {
        this.online = true;
        while (online) {
            try {
                Socket socket = this.server.accept();
                Client client = new Client();
                client.addObserver(Router.getInstance());
                client.start(socket);
                this.clients.put(client.getIP(), client);
                System.out.println("Nova conexão:" + client);
            } catch (IOException ex) {
                System.out.println("Falha na conexão: " + ex.getMessage());
            }
        }
    }
}
