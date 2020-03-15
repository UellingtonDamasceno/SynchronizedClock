package model;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import middleware.Client;
import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class Synchronizer extends TimerTask implements Observer {

    private Client reference;
    private Clock clock;

    public Synchronizer(Clock clock) {
        this.clock = clock;
    }

    public Clock getClock() {
        return this.clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setReference(Client reference) {
        this.reference.addObserver(this);
        this.reference = reference;
    }

    @Override
    public void run() {
        try {
            JSONObject message = new JSONObject();
            message.accumulate("ROUTE", "SYNCHRONIZE");
            message.accumulate("a", this.clock.getTime());
            this.reference.send(message.toString());
        } catch (IOException ex) {

        }
    }

    @Override
    public void update(Observable o, Object o1) {
        JSONObject response = new JSONObject((String) o1);
        double x = response.getDouble("x");
        double y = response.getDouble("y");
        double a = response.getDouble("a");
        double b = this.clock.getTime();

        double RTT = (b-a)-(y-x);
        double offset = x - (a + RTT/2);
        
        this.clock.setOffset(offset);
    }
}
