package Banks;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;

import Accounts.BankAccount;

import java.util.LinkedList;
import CustomExceptions.IllegalAccountNumberException;
import Customers.Customer;
import Enums.AccountType;

public abstract class Bank {
    
     private String bankName;
     private List<Customer> customers;


    public Bank(String bankName) {
        this.bankName = bankName;
        customers = new LinkedList<>();
    }


    public abstract void openAccount(Customer customer, AccountType accountType, String accountNumber) throws IllegalAccountNumberException, IOException, Exception;
    public abstract void openAccount(Customer customer, BankAccount account) throws IllegalAccountNumberException, IOException, Exception;
    public abstract void closeAccount(Customer customer, String accountNumber) throws InvalidObjectException, Exception;
    public abstract void updateCustomerInfo(Customer customer, String address, String phoneNumber, String email) throws Exception;
    public abstract Customer findById(String customerID) throws Exception;

    public String getBankName() {
        return bankName;
    }


    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public void addCustomer(Customer customer) throws Exception{
        if(customers.contains(customer)) throw new Exception("BANK HAS THIS CUSTOMER ALREADY!");
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) throws Exception{
        if(!customers.contains(customer)) throw new Exception("BANK DOES NOT CONTAIN SUCH CUSTOMER!");
        customers.remove(customer);
    }


    public List<Customer> getCustomers() {
        return customers;
    }

    private String getCustomersString() {

    
        StringBuffer sb = new StringBuffer();
        if(customers.isEmpty()) sb.append("NO CUSTUMER YET!");
        else{
        for (Customer customer : customers) {
            sb.append(customer.toStringFormal());
            sb.append(System.lineSeparator());
        }
        }
        return sb.toString();
    }


    




    @Override
    public String toString() {
        return "BANK NAME: " + bankName + "\nCUSTOMERS: \n" + getCustomersString();
    }

    
    
     
}
