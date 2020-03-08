package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Uellington Conceição
 */
public class Clock implements Runnable {

    private SimpleDoubleProperty time;

    private double accuracy;
    private double resolution;
    private double drift;
    private double offset;

    private boolean online;

    public Clock() {
        this.time = new SimpleDoubleProperty(0);
        this.accuracy = 0;
        this.resolution = 0;
        this.drift = 0;
        this.offset = 0;
        this.online = false;
    }

    public void initialize() {
        new Thread(this).start();
    }

    public void finalize() {
        this.online = false;
    }

    public SimpleDoubleProperty getSimpleTimeProperty() {
        return this.time;
    }

    @Override
    public void run() {
        this.online = true;
        while (this.online) {
            this.time.set(this.time.get() + 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
