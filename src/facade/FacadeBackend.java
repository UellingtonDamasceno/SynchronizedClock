package facade;

import controller.backend.ConnectionController;
import model.exceptions.NotFoundException;
import controller.backend.PearController;
import controller.backend.ProtocolController;
import controller.backend.SecurityController;
import controller.backend.SynchronizerController;
import java.io.IOException;
import java.util.Collection;
import java.util.Queue;
import middleware.Client;
import model.Pear;

/**
 *
 * @author Uellington Conceição
 */
public class FacadeBackend {

    private static FacadeBackend facade;

    private final PearController pearController;
    private final ConnectionController connectionController;
    private final SecurityController securityController;
    private final SynchronizerController synchronizerController;
    private final ProtocolController pc;

    private FacadeBackend() {
        this.pearController = new PearController();
        this.connectionController = new ConnectionController();
        this.securityController = new SecurityController();
        this.synchronizerController = new SynchronizerController();
        this.pc = new ProtocolController();
    }

    public static synchronized FacadeBackend getInstance() {
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }

    public void initialize(String clientIP, int clientPort, int serverPort) throws IOException {
        this.connectionController.startAll(clientIP, clientPort, serverPort);
        this.pearController.addNewPear(clientIP, clientPort);
        String message = this.pc.getAllPearsMessage();
        this.connectionController.sendToServer(message);
    }

    public void initialize(int port) throws IOException {
        this.connectionController.startServer(port);
        String localHost = this.connectionController.getLocalHost();
        this.pearController.setReference(localHost, port);
        Pear reference = this.pearController.getReference();
        this.pearController.addNewPear(reference);
    }

    public boolean isReference() {
        String ip = this.connectionController.getServer().getIP();
        int port = this.connectionController.getServer().getPort();
        return this.pearController.isReference(ip, port);
    }

    public void setReferencePear(String ip, int port) {
        this.pearController.setReference(ip, port);
    }

    public void setReferencePear(Pear pear) {
        this.pearController.setReference(pear);
    }

    public void initializeSynchronization() {
        Client reference = this.connectionController.getClient();
        this.synchronizerController.setReference(reference);
        this.synchronizerController.startExecutor();
        this.synchronizerController.startSynchronization();
    }

    public void notifyAllNewPearConnected(String id) throws NotFoundException, IOException {
        Pear pear = this.pearController.getPear(id);
        String message = this.pc.getUrgentMessage("NEW/PEAR/CONNECTED", pear.toString());
        this.connectionController.notifyAll(message);
    }
    
    public void addNewKnownPear(String ip, int port, boolean status, boolean reference){
        this.pearController.addNewPear(ip, port, status);
    }

    public void connectWith(Pear pear) throws IOException {
        this.connectionController.startClient(pear.getIP(), pear.getPort());
    }

    public void setKnownPears(Queue<Pear> knownPears) {
        this.pearController.setKnownPears(knownPears);
    }

    public Collection<Pear> getKnownPears() {
        return this.pearController.getKnownPears();
    }

    public void finalizeAll() throws IOException {
        this.connectionController.closeAll();
        this.synchronizerController.stopAll();
    }
}
