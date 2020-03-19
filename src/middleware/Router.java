package middleware;

import model.exceptions.NotFoundException;
import facade.FacadeBackend;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Pear;
import model.exceptions.ReferenceNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe responsável por receber as solicitações e: - Adcionar novo cliente na
 * lista de espera (buffer). - Direcionar cada solicitação para devido
 * processamento.
 *
 * - Precisa-se do facade da aplicação e um buffer para funcionar como espera. -
 * Talvez seja interessante criar um controller de protocolo ... Para padronizar
 * as solicitações.
 *
 * @author Uellington Damasceno
 */
public class Router implements Observer {

    private static Router router;

    private Router() {
    }

    public static synchronized Router getInstance() {
        return (router == null) ? router = new Router() : router;
    }

    public synchronized void process(Client client, JSONObject request) {
        String command = request.getString("command");
        switch (command) {
            case "GET/ALL/PEARS": {
                Collection<Pear> knownPears = FacadeBackend.getInstance().getKnownPears();
                JSONArray pears = new JSONArray(knownPears);
                JSONObject message = new JSONObject();
                message.accumulate("command", "UPDATE/LIST/KNOWN/PEARS");
                message.accumulate("pears", pears.toString());
                try {
                    client.send(message.toString());
                } catch (IOException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "UPDATE/LIST/KNOWN/PEARS": {
                try {
                    JSONArray pears = request.getJSONArray("pears");
                    LinkedList<Pear> knownPears = new LinkedList(pears.toList());
                    FacadeBackend.getInstance().setKnownPears(knownPears);
                    break;
                } catch (ReferenceNotFoundException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    //Iniciar uma nova eleição
                }
            }
            case "NEW/PEAR/CONNECTED": {
                JSONObject pear = new JSONObject(request.getString("pear"));
                String ip = pear.getString("ip");
                int port = pear.getInt("port");
                boolean reference = pear.getBoolean("isReference");
                boolean status = pear.getBoolean("status");
                try {
                    FacadeBackend.getInstance().addNewKnownPear(ip, port, status, reference);
                    FacadeBackend.getInstance().notifyAllNewPearConnected(ip + port);
                } catch (NotFoundException ex) {
                    System.out.println("O pear com id: " + ip + port + " Não foi encontrado!");
                } catch (IOException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "SYNCHRONIZE": {
                try {
                    JSONObject message = new JSONObject();
                    int time = FacadeBackend.getInstance().getCurrentTime();
                    message.accumulate("a", request.getInt("a"));
                    message.accumulate("x", time);
                    time = FacadeBackend.getInstance().getCurrentTime();
                    message.accumulate("y", time);
                    client.send(message.toString());
                } catch (IOException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "RECONNECT": {
                String id = request.getString("id");
                FacadeBackend.getInstance().reconnectPear(id);
                break;
            }
            case "DISCONNECT": {
                String id = request.getString("id");
                FacadeBackend.getInstance().disconnectPear(id);
                break;
            }
            case "FINALIZE": {
               
                break;
            }
            default:
                System.out.println("Comando invalido: " + command);
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        Client client = (Client) o;
        String request = (String) o1;
        JSONObject message = new JSONObject(request);
        this.process(client, message);
    }

}
