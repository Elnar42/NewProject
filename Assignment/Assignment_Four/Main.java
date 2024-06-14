import Accounts.BankAccount;
import Accounts.CheckingAccount;
import Accounts.SavingAccount;
import Banks.MainBank;
import Customers.Customer;
import Enums.AccountType;
import Enums.CustomerType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        // All the Account Numbers must be UNIQUE below, and 16 digits long!
        // Difference between CHECKING and SAVING acounts is that Withdrawl is not allowed with SavingAccount and Deposit is not allowed with CheckingAccount

        CheckingAccount ch = new CheckingAccount("4169738810217970");
        ch.setBalance(BigDecimal.valueOf(100000000));
        CheckingAccount ch1 = new CheckingAccount("4149738810217434");
        ch1.setBalance(BigDecimal.valueOf(100000000));
        CheckingAccount ch2 = new CheckingAccount("4169738812142314");
        ch2.setBalance(BigDecimal.valueOf(100000000));
        CheckingAccount ch3 = new CheckingAccount("1234567891234321");
        ch3.setBalance(BigDecimal.valueOf(100000000));

        SavingAccount cv = new SavingAccount("9876543213214563");
        cv.setBalance(BigDecimal.valueOf(100000000));
        SavingAccount cv1 = new SavingAccount("1234554321678901");
        cv1.setBalance(BigDecimal.valueOf(100000000));
        SavingAccount cv2 = new SavingAccount("5432167890123450");
        cv2.setBalance(BigDecimal.valueOf(100000000));
        SavingAccount cv3 = new SavingAccount("6789009876543213");
        cv3.setBalance(BigDecimal.valueOf(100000000));


        // Customer IDs are UNIQUE! MUST contain at least one digit, contain only letter and digits, length must be >= 6
        // Difference between USER and ADMIN is that User can not add, remove, update anything (must require from bank), ADMIN has full previlage
        Customer customerOne = new Customer("7RH04KM", "ELMIN", CustomerType.USER, "988989");
        Customer customerTwo = new Customer("7O04VM", "EMIN", CustomerType.ADMIN, "981239");
        Customer customerThree = new Customer("7PO12K", "ELCHIN", CustomerType.USER, "876525");
        Customer customerFour = new Customer("OOP121", "FUAD", CustomerType.ADMIN, "786442");


        // customerOne.addAccount(cv3);  -> Exception, customer is USER type, can not change anything
        // customerTwo.addAccount(cv3); -> Fine, it works since the customerTwo is ADMIN type

        MainBank bank = new MainBank("PASHA BANK");
        bank.addCustomer(customerOne);
        bank.addCustomer(customerTwo);
        bank.addCustomer(customerThree);
        bank.addCustomer(customerFour);

        //  bank.removeCustomer(customerFour); -> Remeves customer, if there is an account assigned to this customer, all the accounts are reset
        //  bank.removeCustomer(customerFour);  -> Exception, the customer has not in the bank!
        bank.openAccount(customerTwo, cv2);
        bank.openAccount(customerThree, cv3);
        bank.openAccount(customerFour, cv);
        bank.openAccount(customerFour, cv1);

        //bank.openAccount(customerFour, cv3); -> Exception, Account has already taken by customerThree


        bank.openAccount(customerTwo, ch);
        bank.openAccount(customerThree, ch3);
        bank.openAccount(customerFour, ch2);
        bank.openAccount(customerFour, ch1);


        System.out.println("--------------------------WELCOME TO " + bank.getBankName() + "--------------------------");

        while (true) {
            System.out.println("PROVIDE CUSTOMER ID: ");
            String customerId = scan.nextLine();
            Customer customer = bank.findById(customerId);


            System.out.println("PROVIDE PASSWORD: ");
            String password = scan.nextLine();

            boolean validated = false;

            for (int i = 0; i < 3; i++) {
                if (!password.equals(customer.getPassword())) {
                    if (i < 2) {
                        System.out.println("WRONG PASSWORD, YOU HAVE " + (2 - i) + " CHANCES LEFT!");
                        System.out.println("PROVIDE PASSWORD: ");
                        password = scan.nextLine();
                    } else {
                        System.out.println("YOU HAVE NO MORE CHANCES LEFT!");
                        break;
                    }
                } else {
                    validated = true;
                    break;
                }
            }

            if (validated) {
                System.out.println("-----------------NICE TO SEE YOU AGAIN, " + customer.getName() + "-----------------");
                if (customer.getAccounts().isEmpty()) {
                    System.out.println("YOU HAVE NO ACTIVE ACCOUNT!");
                    System.out.println("DO YOU WANT TO ASK FOR ACCOUNT FROM THE BANK? \nYES/NO");
                    String answer = scan.nextLine();
                    if (answer.equals("NO")) {
                        System.out.println("HAVE A NICE DAY! SEE YOU NEXT TIME!");
                        continue;
                    } else if (answer.equals("YES")) {

                        System.out.println("SELECT ACCOUNT TYEP: SAVING (1) / CHECKING (2)");
                        int response = scan.nextInt();

                        AccountType type = response == 1 ? AccountType.SAVINGS : AccountType.CHECKINGS;
                        System.out.println("THANK YOU FOR YOUR RESPONSE, YOU WILL BE CONTACTED BY BANK ONCE YOUR ACCOUNT IS CREATED!");

                        LocalTime time = LocalTime.now();
                        LocalDate date = LocalDate.now();

                        String inquiry = "CUSTOMER: " + customer.getCustomerID() + " ACCOUNT TYPE: " + type.toString() + " DATE: " + date + " TIME: " + time;


                        File file = new File("AccountInquiry.txt");

                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                        bw.write(inquiry);
                        bw.newLine();
                        bw.close();

                        break;
                    } else {
                        System.out.println("INVALID CHOICE!");
                        System.out.println("SYSTEM IS CLOSED!");
                        break;
                    }

                } else {

                    LinkedList<String> checkOperations = new LinkedList<>(List.of("TRANSFER MONEY (T)", "WITHDRAW MONEY (W)"));
                    LinkedList<String> saveOperations = new LinkedList<>(List.of("TRANSFER MONEY (T)", "DEPOSIT MONEY (D)"));


                    System.out.println("HERE ARE YOUR ACCOUNTS: ");
                    System.out.println(customer.getAccountString());

                    System.out.println("SELECT ACCOUNT BY ACCOUNT NUMBER: ");
                    String accountNumber = scan.nextLine();

                    Iterator<BankAccount> it = customer.getAccounts().iterator();

                    BankAccount account = null;
                    BankAccount acc;
                    while (it.hasNext()) {
                        if ((acc = it.next()).getAccountNumber().equals(accountNumber)) {
                            account = acc;
                            break;
                        }
                    }

                    if (account == null) {
                        System.out.println("WRONG ACCOUNT NUMBER! SYSTEM IS CLOSED!");
                        break;
                    } else {

                        if (account.getAccountType().equals(AccountType.CHECKINGS)) {
                            System.out.println("SELECT OPERATION TO PERFORM ON THESE ACCOUNT: ");
                            System.out.print("SEE BALANCE (B) ");
                            checkOperations.forEach(t -> System.out.print(t + " "));
                            String operation = scan.nextLine();

                            if(operation.equals("B")){
                                System.out.println("Your balance is " + account.getBalance());
                            }if (operation.equals("T")) {
                                System.out.println("ADD ACCOUNT NUMBER TO TRANSFORM MONEY: ");
                                String transferDestination = scan.nextLine();

                                Iterator<BankAccount> accounts = BankAccount.getUsedBankAccounts().iterator();
                                BankAccount accountt = null;
                                while (accounts.hasNext()) {
                                    if (accounts.next().getAccountNumber().equals(transferDestination))
                                        accountt = accounts.next();
                                }

                                if (accountt == null) {
                                    System.out.println("TRANSER ACCOUNT IS NOT FOUND! SYSTEM IS CLOSED!");
                                    break;
                                } else {
                                    System.out.println("HOW MUCH MONEY YOU WANT TO TRANSFORM? (BALANCE = " + account.getBalance() + ")");
                                    double tranformAmount = scan.nextDouble();
                                    account.transferMoney(accountt, tranformAmount);
                                    System.out.println("OPERATION IS SUCCESSFULL! SYSTEM IS CLOSED! SEE YOU NEXT TIME!");
                                    break;
                                }

                            } else if (operation.equals("W")) {
                                System.out.println("HOW MUCH MONEY YOU WANT TO WITHDRAW? (BALANCE = " + account.getBalance() + ")");
                                double withdrawAmount = scan.nextDouble();
                                ((CheckingAccount) account).withdrawMoney(withdrawAmount);
                                System.out.println("OPERATION IS SUCCESSFULL!");
                                System.out.println("SEE YOU NEXT TIME!");
                                break;
                            } else {
                                System.out.println("SYSTEM IS CLOSED!");
                                break;
                            }
                        } else if (account.getAccountType().equals(AccountType.SAVINGS)) {
                            System.out.println("SELECT OPERATION TO PERFORM ON THESE ACCOUNT: ");
                            System.out.print("SEE BALANCE (B) ");
                            saveOperations.forEach(t -> System.out.print(t + " "));
                            String operation = scan.nextLine();


                            if(operation.equals("B")){
                                System.out.println("Your balance is " + account.getBalance());
                            }if (operation.equals("T")) {
                                System.out.println("ADD ACCOUNT NUMBER TO TRANSFORM MONEY: ");
                                String transferDestination = scan.nextLine();

                                Iterator<BankAccount> accounts = BankAccount.getUsedBankAccounts().iterator();
                                BankAccount accountt = null;
                                ;
                                while (accounts.hasNext()) {
                                    if (accounts.next().getAccountNumber().equals(transferDestination))
                                        accountt = accounts.next();
                                }

                                if (accountt == null) {
                                    System.out.println("TRANSER ACCOUNT IS NOT FOUND! SYSTEM IS CLOSED!");
                                    break;
                                } else {
                                    System.out.println("HOW MUCH MONEY YOU WANT TO TRANSFORM? (BALANCE = " + account.getBalance() + ")");
                                    double tranformAmount = scan.nextDouble();
                                    account.transferMoney(accountt, tranformAmount);

                                    System.out.println("OPERATION IS SUCCESSFULL! SYSTEM IS CLOSED! SEE YOU NEXT TIME!");
                                    break;
                                }
                            } else if (operation.equals("D")) {
                                System.out.println("HOW MUCH MONEY YOU WANT TO DEPOSIT? ");
                                double depositAmount = scan.nextDouble();
                                ((SavingAccount) account).depositMoney(depositAmount);

                                LocalTime time = LocalTime.now();
                                LocalDate date = LocalDate.now();

                                String inquiry = "CUSTOMER: " + customer.getCustomerID() + " DEPOSIT AMOUNT: " + depositAmount + " ACCOUNT: " + account.getAccountNumber() + " DATE: " + date + " TIME: " + time;

                                File file = new File("Deposit.txt");

                                if (!file.exists()) {
                                    file.createNewFile();
                                }

                                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                                bw.write(inquiry);
                                bw.newLine();
                                bw.close();


                                System.out.println("OPERATION IS SUCCESFULL! SEE YOU NEXT TIME!");
                                break;
                            } else {
                                System.out.println("SYSTEM IS CLOSED!");
                                break;
                            }


                        }

                    }
                }

            } else {
                System.out.println("UNFORTUNATELY, YOU PROVIDED A WRONG PASSWORD!");
                System.out.println("SYSTEM IS CLOSED!");
                break;
            }

        }


        scan.close();
    }


}
