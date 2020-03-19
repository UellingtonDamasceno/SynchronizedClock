package facade;

import controller.backend.ClockController;
import controller.backend.ConnectionController;
import model.exceptions.NotFoundException;
import controller.backend.PearController;
import controller.backend.ProtocolController;
import controller.backend.SecurityController;
import controller.backend.SynchronizerController;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import middleware.Client;
import model.Clock;
import model.Pear;
import model.exceptions.ReferenceNotFoundException;

/**
 *
 * @author Uellington Conceição
 */
public class FacadeBackend {

    private static FacadeBackend facade;

    private final PearController pearController;
    private final ConnectionController connectionController;
    private final ClockController clockController;
    private final SecurityController securityController;
    private final SynchronizerController synchronizerController;
    private final ProtocolController protocolController;

    private FacadeBackend() {
        this.pearController = new PearController();
        this.connectionController = new ConnectionController();
        this.clockController = new ClockController();
        this.securityController = new SecurityController();
        this.synchronizerController = new SynchronizerController();
        this.protocolController = new ProtocolController();
    }

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public void initialize(String clientIP, int clientPort, int serverPort) throws IOException {
        this.connectionController.startAll(clientIP, clientPort, serverPort);

        this.pearController.addNewPear(clientIP, clientPort);
        this.pearController.setThisPear(clientIP, clientPort);

        String message = this.protocolController.getAllPearsMessage();
        this.connectionController.sendToServer(message);
    }

    public void initialize(int port) throws IOException, ReferenceNotFoundException {
        this.connectionController.startServer(port);

        String localHost = this.connectionController.getLocalHost();
        Pear reference = new Pear(localHost, port, true, true);

        this.pearController.setReference(reference);
        this.pearController.setThisPear(reference);
        this.pearController.addNewPear(reference);
    }

    public int getCurrentTime() {
        return this.clockController.getCurrentTime();
    }

    public Clock getClock() {
        return this.clockController.getClock();
    }

    public boolean isReference() {
        return this.pearController.thisPearIsReference();
    }

    public void setReferencePear(String ip, int port) {
        this.pearController.setReference(ip, port);
    }

    public void setReferencePear(Pear pear) {
        this.pearController.setReference(pear);
    }

    public void initializeSynchronization() {
        Client reference = this.connectionController.getClient();
        Clock clock = this.clockController.getClock();
        this.synchronizerController.setClock(clock);
        this.synchronizerController.setReference(reference);
        this.synchronizerController.startExecutor();
        this.synchronizerController.startSynchronization();
    }

    public void updateSyncInterval(long interval, TimeUnit timeUnit) {
        this.synchronizerController.updateSyncInterval(interval, timeUnit);
    }

    public void stopSynchronization() {
        this.synchronizerController.stopAll();
    }

    public void notifyAllNewPearConnected(String id) throws NotFoundException, IOException {
        Pear pear = this.pearController.getPear(id);
        String message = this.protocolController.getUrgentMessage("NEW/PEAR/CONNECTED", pear.toString());
        this.connectionController.notifyAll(message);
    }

    public void addNewKnownPear(String ip, int port, boolean status, boolean reference) {
        this.pearController.addNewPear(ip, port, status);
    }

    public void connectWith(Pear pear) throws IOException {
        this.connectionController.startClient(pear.getIP(), pear.getPort());
    }

    public void setKnownPears(Queue<Pear> knownPears) throws ReferenceNotFoundException {
        this.pearController.setKnownPears(knownPears);
        Pear reference = this.pearController.getReference();
        this.pearController.setReference(reference);
    }

    public Collection<Pear> getKnownPears() {
        return this.pearController.getKnownPears();
    }

    public void finalizeAll() throws IOException {
        this.clockController.stop();
        this.synchronizerController.stopAll();
        this.connectionController.closeAll();
    }

    public void disconnectPear(String id) {
        this.pearController.updateStatePear(id, false);
        this.connectionController.getServer().disconnectPear(id);
    }

    public void reconnectPear(String id) {
        this.pearController.updateStatePear(id, true);
        this.connectionController.getServer().reconnectPear(id);
    }
}
