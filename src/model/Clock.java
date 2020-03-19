package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Uellington Conceição
 */
public class Clock implements Runnable {

    private IntegerProperty time;

    private long resolution;
    private double offset;

    private boolean online;

    public Clock() {
        this.time = new SimpleIntegerProperty((int) System.currentTimeMillis());
        this.resolution = 1000;
        this.offset = 0;
        this.online = false;
    }

    public void initialize() {
        new Thread(this).start();
    }

    public void stop() {
        this.online = false;
    }

    public int getTime() {
        return this.time.get();
    }

    public IntegerProperty getSimpleTimeProperty() {
        return this.time;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public void run() {
        this.online = true;
        while (this.online) {
            this.time.set(this.time.get() + 1000);
            try {
//                System.out.println("Resolution: "+ (long) (resolution - offset));
                Thread.sleep((long) (resolution - offset));
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
