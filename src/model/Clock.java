package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Uellington Conceição
 */
public class Clock implements Runnable {

    private SimpleDoubleProperty time;

    private long resolution;
    private double offset;

    private boolean online;

    public Clock() {
        this.time = new SimpleDoubleProperty(0);
        this.resolution = 0;
        this.offset = 0;
        this.online = false;
    }

    public void initialize() {
        new Thread(this).start();
    }

    public void finalize() {
        this.online = false;
    }

    public double getTime() {
        return this.time.get();
    }

    public SimpleDoubleProperty getSimpleTimeProperty() {
        return this.time;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public void run() {
        this.online = true;
        while (this.online) {
            this.time.set(this.time.get() + 1);
            try {
                Thread.sleep((long) (resolution - offset));
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
