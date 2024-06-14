package Accounts;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import CustomExceptions.IllegalAccountNumberException;
import CustomExceptions.IllegalTransactionException;
import Enums.AccountType;
import Enums.TransactionStatus;

public abstract class BankAccount {
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private boolean isTaken;
    private TransactionStatus transactionStatus;
   

    private static Set<String> usedAccountNumbers = new HashSet<>();
    private static Set<BankAccount> usedBankAccounts = new HashSet<>();

    public BankAccount(String accountNumber, AccountType accountType) throws IllegalAccountNumberException, IOException {
        this.accountNumber = validateAccountNumber(accountNumber);
        this.balance = new BigDecimal("0.0");
        this.accountType = accountType;
        isTaken = false;
        usedBankAccounts.add(this);
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public static Set<String> getUsedAccountNumbers() {
        return usedAccountNumbers;
    }


    
    public boolean isTaken() {
        return isTaken;
    }


    public void setTaken(boolean isTaken) {
        this.isTaken = isTaken;
    }


    


    @Override
    public String toString() {
        return "BankAccount [accountNumber=" + accountNumber + ", balance=" + balance + ", accountType=" + accountType
                + ", isTaken=" + isTaken + "]";
    }


    public boolean transferMoney(BankAccount destinationAccount, double amount) throws Exception {
        if(!isTaken()) throw new Exception("THIS ACCOUNT HAS NOT BEEN TAKEN. SO TRANSACTION IS NOT POSSIBLE!");
        if(!getAccountType().isTransactionAllowed()) throw new IllegalTransactionException("TRANSACTION IS NOT ALLOWED WITH THIS ACCOUNT!");
        if(destinationAccount.getAccountType().equals(AccountType.BLOCKED)) throw new IllegalTransactionException("DESTINATION ACCOUNT IS BLOCKED!");
        if(getBalance().compareTo(new BigDecimal(amount)) < 0) throw new IllegalTransactionException("BALANCE IS NOT ENOUGH FOR THIS TRANSACTION!");
        if(amount < 1.0) throw new IllegalTransactionException("TRANSFERABLE MONEY MUST BE AT LEAST 1.0");
        if(!destinationAccount.isTaken()) throw new Exception("DESTINATION ACCOUNT HAS NOT BEEN TAKEN. SO TRANSACTION IS NOT POSSIBLE!");
        setBalance(getBalance().subtract(new BigDecimal(amount)));
        destinationAccount.setBalance(destinationAccount.getBalance().add(new BigDecimal(amount)));

        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();

        String tranactionHistory = "FROM: " + getAccountNumber() + " TO: " + destinationAccount.getAccountNumber() + " MONEY AMOUNT: " + amount + " DATE: " + date.toString() + " TIME: " + time.toString();

        File file = new File("TransactionHistory.txt");
        
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(tranactionHistory);
        bw.newLine();
        bw.close();
        
        return TransactionStatus.SUCCESS.getStatus();
    }

    public void blockAccount() {
      this.accountType = AccountType.BLOCKED;
    }


    public boolean resetAccount(){
      setBalance(BigDecimal.valueOf(0));
      setTaken(false);
      return true;
    }


    private String validateAccountNumber(String accountNumber) throws IllegalAccountNumberException, IOException {
        String trimmedAccountNumber = accountNumber.trim();
        if (usedAccountNumbers.contains(trimmedAccountNumber)) throw new IllegalAccountNumberException("ACCOUNT NUMBER ALREADY EXISTS");
        if(!trimmedAccountNumber.matches("\\d+")) throw new IllegalAccountNumberException("INVALID ACCOUNT NUMBER!");
        if(trimmedAccountNumber.length() != 16) throw new IllegalAccountNumberException("LENGTH OF ACCOUNT NUMBER MUST BE 16!");
        usedAccountNumbers.add(trimmedAccountNumber);
        return trimmedAccountNumber;
    }


    public static Set<BankAccount> getUsedBankAccounts() {
        return usedBankAccounts;
    }
}
