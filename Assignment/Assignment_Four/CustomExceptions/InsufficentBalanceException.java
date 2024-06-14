package CustomExceptions;

public class InsufficentBalanceException extends Exception {

    public InsufficentBalanceException(String message){
        super(message);
    }
    
}
