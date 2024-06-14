package Enums;
public enum AccountType{
    SAVINGS(true, false), CHECKINGS(true, true), BLOCKED(false, false);
    
    private final boolean transactionAllowed;
    private final boolean withdrawAllowed;

    private AccountType(boolean transactionAllowed, boolean withdrawAllowed) {
        this.transactionAllowed = transactionAllowed;
        this.withdrawAllowed = withdrawAllowed;
       
    }

    public boolean isTransactionAllowed() {
        return transactionAllowed;
    }

    public boolean isWithdrawAllowed() {
        return withdrawAllowed;
    }

    public String status(){
        return "[Transaction Allowed: " + isTransactionAllowed() + " && Withdrawal Allowed: " + isWithdrawAllowed() + "]";
    }

}
