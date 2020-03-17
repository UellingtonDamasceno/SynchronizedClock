package model;

import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class Pear implements Comparable {

    private String id;
    private String ip;
    private int port;
    private boolean status;
    private boolean reference;
    
    public Pear(String ip, int port, boolean status, boolean reference) {
        this.ip = ip;
        this.port = port;
        this.id = this.ip + this.port;
        this.status = status;
        this.reference = reference;
    }

    public String getID() {
        return this.id;
    }

    public String getIP() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isOnline() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isReference(){
        return this.reference;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.ip.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pear) {
            Pear another = (Pear) obj;
            return (this.hashCode() == another.hashCode());
        }
        return false;
    }

    @Override
    public int compareTo(Object t) {
        if (t instanceof Pear) {
            Pear another = (Pear) t;
            return this.ip.compareTo(another.ip);
        }
        return 1;
    }
    
    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.accumulate("ip", ip);
        json.accumulate("id", id);
        json.accumulate("port", port);
        json.accumulate("isReference", reference);
        json.accumulate("status", status);
        return json.toString();
    }
    
}
