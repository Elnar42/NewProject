package Accounts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import CustomExceptions.IllegalAccountNumberException;
import CustomExceptions.IllegalWitdrawlException;
import CustomExceptions.InsufficentBalanceException;
import Enums.AccountType;

// READY
public class CheckingAccount extends BankAccount implements CheckingAccountOperations  {

    public CheckingAccount(String accountNumber) throws IllegalAccountNumberException, IOException {
        super(accountNumber, AccountType.CHECKINGS);
    }

 
    @Override
    public boolean withdrawMoney(double amount) throws Exception {
        if(!isTaken()) throw new Exception("THIS ACCOUNT HAS NOT BEEN TAKEN. SO WITHDRAWL IS NOT POSSIBLE!");
       if(this.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) throw new InsufficentBalanceException("INSUFFICIENT BALANCE!");
       if(getAccountType().equals(AccountType.BLOCKED)) throw new IllegalWitdrawlException("ACCOUNT IS BLOCKED!");
       if(!getAccountType().isWithdrawAllowed()) throw new IllegalWitdrawlException("WITHDRAWL IS NOT ALLOWED!");
       
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        String withdrawl = "FROM: " + getAccountNumber() + " MONEY AMOUNT: " + amount + " DATE: " + " MONEY LEFT: " + getBalance().subtract(BigDecimal.valueOf(amount)).toString() + " DATE: " +  date.toString() + " TIME: " + time.toString();
        File file = new File("WithdrawlHistory.txt");
        
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(withdrawl);
        bw.newLine();
        bw.close();
       
        setBalance(getBalance().subtract(BigDecimal.valueOf(amount)));

        return true;
    }
    
}
