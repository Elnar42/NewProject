package Customers;

import java.io.InvalidObjectException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import Accounts.BankAccount;
import CustomExceptions.InvalidEmailException;
import CustomExceptions.InvalidPhoneNumberException;
import Enums.CustomerType;

public class Customer implements CustomerOperations {
    private String customerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private CustomerType customerType;
    private final String password;
    private List<BankAccount> accounts;

     public static Set<String> customerIDs = new HashSet<>();

    public Customer(String customerID, String name, CustomerType customerType, String password) throws Exception {
        if(customerIDs.contains(customerID)) throw new Exception("PROVIDED CUSTOMER ID HAS ALREADY BEEN TAKEN!");
        if (!customerID.matches("[a-zA-Z0-9]+") || !customerID.matches(".*[a-zA-Z].*") || !customerID.matches(".*\\d.*") || customerID.length() < 6 || customerID.contains(" ")) throw new IllegalArgumentException("CUSTOMER ID IS NOT SET PROPERLY (SPECIFICATIONS: ID SHOULD NOT START WITH DIGIT, AT LEAST ONE DIGIT, AT LEAST ONE LETTER,  LENGTH  MORE THAN OR EQAUL 6, NO SPACE AT ALL)");
        if(!password.matches("\\d{6,}")) throw new Exception("LENGTH OF PASSWORD MUST BE 6 OR MORE DIGITS!");
        this.customerID = customerID;
        this.name = name;
        this.address = "NOT-PROVIDED";
        this.phoneNumber = "NOT-PROVIDED";
        this.email = "NOT-PROVIDED";
        this.customerType = customerType;
        accounts = new LinkedList<>();
        this.password = password;
        customerIDs.add(customerID);
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

 

    public boolean isAdmin() {
        return customerType == CustomerType.ADMIN;
    }

    public boolean isUser() {
        return customerType == CustomerType.USER;
    }

    @Override
    public void addAccount(BankAccount account) throws Exception {
       if(accounts.contains(account)) throw new InvalidObjectException("BANK ACCOUNT HAS ALREADY BEEN TAKEN BY THIS CUSTOMER!");
       if(account.isTaken() == true) throw new Exception("ACCOUNT HAS ALREADY BEEN TAKEN BY ANOTHER CUSTOMER!");
       if(isUser()) throw new Exception("ONLE PREVILAGED USERS CAN ADD ACCOUNT, ASK FOR BANK TO ADD ACCCOUNT!");
       accounts.add(account);
       account.setTaken(true);
    }

    @Override
    public void removeAccount(BankAccount account) throws Exception {
       if(!accounts.contains(account)) throw new InvalidObjectException("CUSTOMER DOES NOT HAVE SUCH ACCOUNT!");
       if(isUser()) throw new Exception("ONLE PREVILAGED USERS CAN REMOVE ACCOUNT, ASK FOR BANK TO REMOVE ACCCOUNT!");
       accounts.remove(account);
       account.resetAccount();
    }

    public void removeAccount(String accountNumber) throws Exception {
        if(isUser()) throw new Exception("ONLE PREVILAGED USERS CAN REMOVE ACCOUNT, ASK FOR BANK TO REMOVE ACCCOUNT!");
         Iterator<BankAccount> iterator = accounts.iterator();
    while (iterator.hasNext()) {
        BankAccount account = iterator.next();
        if (account.getAccountNumber().equals(accountNumber)) {
            iterator.remove();
            account.resetAccount();
            return;
        }
    }
    throw new InvalidObjectException("CUSTOMER DOES NOT HAVE SUCH ACCOUNT!");
    }

    @Override
    public void updateCustomerInfo(String address, String phoneNumber, String email) throws Exception{
    if(isUser()) throw new Exception("ONLE PREVILAGED USERS CAN UPDATE INFORMATION, ASK FOR BANK TO UPDATE ANY INFORMATION!");
    this.address = address;
    String patternPhone= "^[0-9]+$";

    if(!phoneNumber.matches(patternPhone)) throw new InvalidPhoneNumberException("PHONE NUMBER IS INVALID!");
    this.phoneNumber = phoneNumber;

    String patternEmail = "^[^0-9][a-z0-9._]+@gmail\\.com$";
    if(!email.matches(patternEmail)) throw new InvalidEmailException("EMAIL IS INVALID!");
    this.email = email;    
    }

    
    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public String getAccountString() {
       StringBuffer sb = new StringBuffer();
       if(accounts.isEmpty()) sb.append("NO ACCOUNT YET!");
       else{
       for(BankAccount account: accounts){
        sb.append(account.toString());
        sb.append(System.lineSeparator());
       }
    }
       return sb.toString();
    }
    

    public String toStringFormal() {
        return "Customer [customerID=" + customerID + ", name=" + name + ", address=" + address + ", phoneNumber="
                + phoneNumber + ", email=" + email + "]";
    }

    
    @Override
    public String toString() {
        return "CUSTOMER (ID: " + getCustomerID() + ")" + "\nACCOUNTS: \n" + getAccountString();
                
    }



   
    



}
