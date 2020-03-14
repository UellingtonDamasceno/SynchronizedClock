package model.exceptions;

/**
 *
 * @author Uellington Conceição
 */
public class UnitializedObjectException extends Exception {

    public UnitializedObjectException() {
        super();
    }

    public UnitializedObjectException(String message){
        super(message);
    }
    
}
