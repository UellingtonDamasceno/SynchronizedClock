package controller.backend;

import org.json.JSONObject;

/**
 *
 * @author Uellington Conceição
 */
public class ProtocolController {
    
    public String getAllPearsMessage(){
        JSONObject message = new JSONObject();
        message.accumulate("command", "GET/ALL/PEARS");
        return message.toString();
    }

    public String getUrgentMessage(String messageType, String pear) {
        JSONObject message = new JSONObject();
        message.accumulate("command", messageType);
        message.accumulate("pear", pear);
        return message.toString();
    }
}
