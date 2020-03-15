package facade;

import controller.backend.ConnectionController;
import controller.backend.PearController;
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
    
    private FacadeBackend(){
        this.pearController = new PearController();
        this.connectionController = new ConnectionController();
        this.securityController = new SecurityController();
        this.synchronizerController = new SynchronizerController();
    }
    
    public static synchronized FacadeBackend getInstance(){
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }
    
    public void initialize(String clientIP, int clientPort, int serverPort) throws IOException{
        this.connectionController.startAll(clientIP, clientPort,  serverPort);
        this.pearController.addNewPear(clientIP, clientPort);
    }
    
    public void initialize(int port) throws IOException{
        this.connectionController.startServer(port);
        String localHost = this.connectionController.getLocalHost();
        this.pearController.setReference(localHost, port);
    }

    public void setReferencePear(String ip, int port){
        this.pearController.setReference(ip, port);
    }

    public void setReferencePear(Pear pear){
        this.pearController.setReference(pear);
    }

    public void initializeSynchronization(){
        Client reference = this.connectionController.getClient();
        this.synchronizerController.setReference(reference);
        this.synchronizerController.startExecutor();
        this.synchronizerController.startSynchronization();
    }
    
    public void connectWith(Pear pear) throws IOException{
        this.connectionController.startClient(pear.getIP(), pear.getPort());
    }
    
    public void setKnownPears(Queue<Pear> knownPears){
        this.pearController.setKnownPears(knownPears);
    }
    
    public Collection<Pear> getKnownPears(){
        return this.pearController.getKnownPears();
    }
    
}
