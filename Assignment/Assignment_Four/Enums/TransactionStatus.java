package Enums;
public enum TransactionStatus{
    SUCCESS(true), FAILED(false);

    private final boolean status;

    TransactionStatus(boolean status){
        this.status = status;
    }

    public boolean getStatus(){
        return this.status;
    }
}
