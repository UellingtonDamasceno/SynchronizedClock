package middleware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class Client extends Observable implements Runnable {

    private String id;
    private String ip;
    private int port;
    private Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    private boolean online;

    public Client() {
        this.online = false;
    }

    public String getIP() {
        return this.ip;
    }

    public String getID() {
        return this.id;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isOnline() {
        return online;
    }

    public void start(String ip, int port) throws IOException {
        this.start(new Socket(ip, port));
    }

    public void start(Socket socket) throws IOException {
        this.socket = socket;
        this.ip = ((String) socket.getRemoteSocketAddress().toString().replace("/", ""));
        this.port = socket.getPort();
        this.id = this.ip + port;
        InputStreamReader is = new InputStreamReader(this.socket.getInputStream());
        this.reader = new BufferedReader(is);

        OutputStreamWriter ot = new OutputStreamWriter(this.socket.getOutputStream());
        this.writer = new BufferedWriter(ot);

        new Thread(this).start();
    }

    public void close() throws IOException {
        if (this.online) {
            this.online = false;
            this.writer.close();
            this.reader.close();
            this.socket.close();
            this.deleteObservers();
        }
    }

    public synchronized void send(String message) throws IOException {
        if (this.online) {
            System.out.println("Enviando: " + message);
            this.writer.write(message + "\n");
            this.writer.flush();
        }
    }

    @Override
    public void run() {
        this.online = true;
        String message;
        while (this.online) {
            try {
                message = this.reader.readLine();
                System.out.println("Chegou um nova mensagem: " + message);
                if (message != null && !(message.isEmpty())) {
                    this.setChanged();
                    this.notifyObservers(message);
                }
            } catch (IOException ex) {
                this.online = false;
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int hashCode() {
        return this.ip.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Client) {
            Client another = (Client) object;
            return (this.hashCode() == another.hashCode());
        }
        return false;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.accumulate("client", this.ip);
        json.accumulate("connection_status", this.online);
        return json.toString();
    }
}
