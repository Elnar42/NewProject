package Accounts;
public interface SavingAccountOperations{
    boolean depositMoney(double amount) throws Exception;  // Customer not allowed to withdraw money from SavingAccount, only deposit
}
