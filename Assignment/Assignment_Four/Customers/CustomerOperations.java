package Customers;

import java.io.InvalidObjectException;
import Accounts.BankAccount;
import CustomExceptions.InvalidEmailException;
import CustomExceptions.InvalidPhoneNumberException;

public interface CustomerOperations {
    void addAccount(BankAccount account) throws InvalidObjectException, Exception;
    void removeAccount(BankAccount account) throws InvalidObjectException, Exception;
    void updateCustomerInfo(String address, String phoneNumber, String email) throws InvalidEmailException, InvalidPhoneNumberException, Exception;
}
