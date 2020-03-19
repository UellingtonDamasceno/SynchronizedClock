package controller.backend;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import middleware.Client;
import model.Clock;
import model.Synchronizer;

/**
 *
 * @author Uellington Conceição
 */
public class SynchronizerController {

    private Synchronizer synchronizer;
    private ScheduledExecutorService executor;
    private ScheduledFuture sincronization;

    public SynchronizerController() {
        this.synchronizer = new Synchronizer();
    }

    private synchronized ScheduledExecutorService getExecutor() {
        if (this.executor == null || this.executor.isShutdown()) {
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }
        return this.executor;
    }

    public void setClock(Clock clock){
        this.synchronizer.setClock(clock);
    }
    
    public void setReference(Client reference) {
        this.synchronizer.setReference(reference);
    }

    public void startExecutor() {
        this.stopSynchronization();
        this.stopExecutor();
        this.executor = this.getExecutor();
    }

    public void stopExecutor() {
        if (this.executor != null) {
            this.executor.shutdown();
            try {
                this.executor.awaitTermination(3, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                this.executor.shutdownNow();
            }
        }
    }

    public void startSynchronization() {
        this.startSynchronization(1, TimeUnit.SECONDS);
    }

    public void startSynchronization(long interval) {
        this.startSynchronization(interval, TimeUnit.SECONDS);
    }

    public void startSynchronization(long interval, TimeUnit timeUnit) {
        this.sincronization = this.getExecutor().scheduleAtFixedRate(synchronizer, 0, interval, timeUnit);
    }

    public void updateSyncInterval(long interval, TimeUnit timeUnit) {
        this.stopSynchronization();
        this.sincronization = this.getExecutor().scheduleAtFixedRate(synchronizer, 0, interval, timeUnit);
        System.out.println("Atualizando o intervalo");
    }

    public void stopSynchronization() {
        if (this.sincronization != null) {
            this.sincronization.cancel(true);
            System.out.println("Parou de sincronizar");
        }
    }

    public void stopAll() {
        this.stopSynchronization();
        this.stopExecutor();
    }
}
