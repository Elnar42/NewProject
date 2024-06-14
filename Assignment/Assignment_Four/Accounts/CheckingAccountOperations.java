package Accounts;

import java.io.IOException;

import CustomExceptions.IllegalWitdrawlException;
import CustomExceptions.InsufficentBalanceException;

public interface CheckingAccountOperations{
    boolean withdrawMoney(double amount) throws InsufficentBalanceException, IOException, IllegalWitdrawlException, Exception; // Customer not allowed to deposit money to Checking, only withdraw
    
} 