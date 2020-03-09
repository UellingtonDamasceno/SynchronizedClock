package middleware;

import java.util.Observable;
import java.util.Observer;
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
    }

    @Override
    public void update(Observable o, Object o1) {
        Client client = (Client) o;
        String request = (String) o1;
        JSONObject message = new JSONObject(request);
        this.process(client, message);
    }

}
