package Banks;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Iterator;

import Accounts.BankAccount;
import Accounts.CheckingAccount;
import Accounts.SavingAccount;
import CustomExceptions.IllegalAccountNumberException;
import Customers.Customer;
import Enums.AccountType;
import Enums.CustomerType;

public class MainBank extends Bank{


    public MainBank(String bankName) {
        super(bankName);
    }

    @Override
    public void openAccount(Customer customer, AccountType accountType, String accountNumber) throws Exception {
     if(!getCustomers().contains(customer)) throw new Exception("BANK DOES NOT CONTAIN THIS CUSTOMER!");
        BankAccount account;
        if(accountType.equals(AccountType.SAVINGS)) account = new SavingAccount(accountNumber);
        else if(accountType.equals(AccountType.CHECKINGS)) account =  new CheckingAccount(accountNumber);
        else throw new Exception("TYPE CAN BE EITHER SAVINGS OR CHECKINGS");

        CustomerType type = customer.getCustomerType();
        if(type.equals(CustomerType.USER)){
            customer.setCustomerType(CustomerType.ADMIN);
            customer.addAccount(account);
        }else{
            customer.addAccount(account);;
        }

        if(type.equals(CustomerType.USER)) customer.setCustomerType(CustomerType.USER);
        else customer.setCustomerType(CustomerType.ADMIN);
    }

    @Override
    public void closeAccount(Customer customer, String accountNumber) throws InvalidObjectException, Exception {
        if(!getCustomers().contains(customer)) throw new Exception("BANK DOES NOT CONTAIN THIS CUSTOMER!");
        CustomerType type = customer.getCustomerType();
        if(type.equals(CustomerType.USER)){
            customer.setCustomerType(CustomerType.ADMIN);
            customer.removeAccount(accountNumber);
        }else{
            customer.removeAccount(accountNumber);
        }

        if(type.equals(CustomerType.USER)) customer.setCustomerType(CustomerType.USER);
        else customer.setCustomerType(CustomerType.ADMIN);
        
    }
       

    @Override
    public void updateCustomerInfo(Customer customer, String address, String phoneNumber, String email) throws Exception {
        CustomerType type = customer.getCustomerType();
        if(type.equals(CustomerType.USER)){
            customer.setCustomerType(CustomerType.ADMIN);
            customer.updateCustomerInfo(address, phoneNumber, email);
        }else{
            customer.updateCustomerInfo(address, phoneNumber, email);
        }

        if(type.equals(CustomerType.USER)) customer.setCustomerType(CustomerType.USER);
        else customer.setCustomerType(CustomerType.ADMIN);
        

    }

    @Override
    public Customer findById(String customerID) throws Exception {
       Iterator<Customer> it =  getCustomers().iterator();
       while(it.hasNext()){
        Customer st =  it.next();
        if(st.getCustomerID().equals(customerID)) return st;
       }

       throw new Exception("SUCH A CUSTOMER DOES NOT EXIST!");
    }

    @Override
    public void openAccount(Customer customer, BankAccount account)
            throws IllegalAccountNumberException, IOException, Exception {
        
                CustomerType type = customer.getCustomerType();
                if(type.equals(CustomerType.USER)){
                    customer.setCustomerType(CustomerType.ADMIN);
                    customer.addAccount(account);
                }else{
                    customer.addAccount(account);
                }
        
                if(type.equals(CustomerType.USER)) customer.setCustomerType(CustomerType.USER);
                else customer.setCustomerType(CustomerType.ADMIN);
                
    }
    
}
