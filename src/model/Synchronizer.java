package model;

import java.util.TimerTask;

/**
 *
 * @author Uellington Conceição
 */
public class Synchronizer extends TimerTask {

    private Clock clock;
    private int i;
    
    public Synchronizer(Clock clock){
        this.clock = clock;
    }
    
    public Clock getClock(){
        return this.clock;
    }
    
    public void setClock(Clock clock){
        this.clock = clock;
    }

    @Override
    public void run() {
        System.out.println("Executando " + i++);
    }
}
