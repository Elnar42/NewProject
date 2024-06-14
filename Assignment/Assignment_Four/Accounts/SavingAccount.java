package Accounts;

import java.io.IOException;
import java.math.BigDecimal;

import CustomExceptions.IllegalAccountNumberException;
import Enums.AccountType;

// READY!
public class SavingAccount extends BankAccount implements SavingAccountOperations{

    private BigDecimal depositAmount;

    public SavingAccount(String accountNumber) throws IllegalAccountNumberException, IOException {
        super(accountNumber, AccountType.SAVINGS);
        this.depositAmount = new BigDecimal("0.0");
    }


    @Override
    public boolean depositMoney(double amount) throws Exception {
        if(!isTaken()) throw new Exception("THIS ACCOUNT HAS NOT BEEN TAKEN. SO DEPOSIT IS NOT POSSIBLE!");
    depositAmount =  depositAmount.add(new BigDecimal(amount));
        return true;
    }





   
    
    
}
