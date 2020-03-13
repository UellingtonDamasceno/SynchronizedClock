package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Uellington Conceição
 */
public class Token {
    private Pear responsible;
    private List<Pear> pears;
    private Pair<String, Double> bestPid;
    
    public Token(){
        this.pears = new LinkedList();
        this.bestPid = new Pair("0", 0.0);
    }
    
    public Token(Pear pear, Pair bestPid){
        this();
        this.responsible = pear;
        this.bestPid = bestPid;
    }

    public List<Pear> getListPearsHaveAlreadyReceived(){
        return Collections.unmodifiableList(this.pears);
    }
    
    
}
