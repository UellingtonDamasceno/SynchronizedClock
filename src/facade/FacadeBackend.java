package facade;

import controller.backend.ConnectionController;
import controller.backend.PearController;
import controller.backend.SecurityController;
import java.io.IOException;
import java.util.Collection;
import java.util.Queue;
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
    
    private FacadeBackend(){
        this.pearController = new PearController();
        this.connectionController = new ConnectionController();
        this.securityController = new SecurityController();
    }
    
    private static synchronized FacadeBackend getInstance(){
        return (facade == null) ? facade = new FacadeBackend() : facade;
    }
    
    public void initialize(String clientIP, int clientPort, int serverPort) throws IOException{
        this.connectionController.startAll(clientIP, clientPort,  serverPort);
    }
    
    public void setKnownPears(Queue<Pear> knownPears){
        this.pearController.setKnownPears(knownPears);
    }
    
    public Collection<Pear> getKnownPears(){
        return this.pearController.getKnownPears();
    }
    
}
