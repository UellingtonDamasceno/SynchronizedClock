package controller.backend;

import model.Clock;

/**
 *
 * @author Uellington Conceição
 */
public class ClockController {

    private final Clock clock;

    public ClockController() {
        this.clock = new Clock();
    }

    public Clock getClock() {
        return this.clock;
    }

    public void initialize() {
        this.clock.initialize();
    }

    public void stop() {
        this.clock.stop();
    }

    public int getCurrentTime() {
        return this.clock.getTime();
    }
}
