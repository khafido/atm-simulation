package com.mitrais;

import com.mitrais.entity.Account;
import com.mitrais.repository.AccountRepository;
import com.mitrais.repository.TransferRepository;
import com.mitrais.repository.WithdrawRepository;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final int CREDENTIAL_MAX_LENGTH = 6;
    private static final int MIN_WITHDRAW = 10;
    private static final int MAX_WITHDRAW = 1000;
    private static final int MAX_TRANSFER = 1000;

    private static final String WITHDRAW_MENU = "1";
    private static final String TRANSFER_MENU = "2";
    private static final String LOGOUT = "3";

    public static final String ONLY_NUMBER_REGEX = "[0-9]+";

    private static List<Account> accounts = new ArrayList<>();
    private static Account loggedAccount = new Account();
    private static String accountNumber;
    private static String pin;
    private static boolean authenticated = false;

    private static String choice = "";
    private static boolean exit = false;
    private static String withdrawAmount = "0";

    private static boolean accountNumberInvalid = true;
    private static boolean pinInvalid = true;

    static Random randomize = new Random();

    public static void main(String[] args) {
        WithdrawRepository.reset();
        TransferRepository.reset();

        accounts = AccountRepository.readFile();

        while (!authenticated) {
            if (login()) {
                while (!exit) {
                    System.out.println("1. Withdraw");
                    System.out.println("2. Fund Transfer");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextLine();

                    transactionMenu();
                }
            }
        }
    }

    private static boolean login() {
        while(accountNumberInvalid){
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();
            if(!validateAccountNumber(accountNumber)){
                return false;
            }
            accountNumberInvalid = false;
        }

        while (pinInvalid){
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            if(!validatePinNumber(pin)){
                return false;
            }
            pinInvalid = false;
        }

        loggedAccount = findAccount();

        if (loggedAccount!=null) {
//            if (loggedAccount.getName() != null) {
                System.out.println("\nWelcome " + loggedAccount.getName());
                exit = false;
//            } else {
//                resetAuthenticatedAccount();
//                return false;
//            }
        } else {
            resetAuthenticatedAccount();
            return false;
        }

        return true;
    }

    private static boolean validateInputOnlyNumber(String number, String message){
        if (!number.matches(ONLY_NUMBER_REGEX)) {
            System.out.println("\n"+message+" should only contain numbers");
            return false;
        }
        return true;
    }

    private static boolean validateAccountNumber(String number) {
        if (number.length() != CREDENTIAL_MAX_LENGTH) {
            System.out.println("\nAccount Number should have "+CREDENTIAL_MAX_LENGTH+" digits length");
            return false;
        }
        if (!validateInputOnlyNumber(number, "Account Number")) { return false;}
        accountNumber = number;
        return true;
    }

    private static boolean validatePinNumber(String number) {
        if (number.length() != CREDENTIAL_MAX_LENGTH) {
            System.out.println("\nPIN should have "+CREDENTIAL_MAX_LENGTH+" digits length");
            return false;
        }

        if(!validateInputOnlyNumber(number, "Pin")){ return false;}
        pin = number;
        return true;
    }

    public static void resetAuthenticatedAccount(){
        authenticated = false;
        accountNumberInvalid = true;
        pinInvalid = true;
    }

    private static Account findAccount() {
        String finalAccountNumber = accountNumber;
        String finalPin = pin;
        Account result = accounts.stream()
                .filter(c -> c.getAccountNumber().equals(finalAccountNumber))
                .filter(c -> c.getPin().equals(finalPin))
                .findFirst()
                .orElse(null);

        if (result != null) {
            authenticated = true;
        } else {
            System.out.println("\nInvalid Account Number or PIN");
        }
        return result;
    }

    private static void transactionMenu() {
        switch (choice) {
            case WITHDRAW_MENU:
                withdrawMenu();
                break;
            case TRANSFER_MENU:
                transferMenu();
                break;
            case LOGOUT:
                authenticated = false;
                break;
            default:
                System.out.println("\nInvalid choice");
                break;
        }
    }

    private static String withdrawChoice(String withdrawChoice) {
        switch (withdrawChoice) {
            case "1":
                withdrawAmount = "10";
                break;
            case "2":
                withdrawAmount = "50";
                break;
            case "3":
                withdrawAmount = "100";
                break;
            case "4":
                System.out.println("\nOther Withdraw");
                System.out.print("Enter amount: $");
                withdrawAmount = scanner.nextLine();
                break;
            case "5":
                System.out.print("\n");
                break;
            default:
                System.out.println("\nInvalid choice");
                break;
        }
        if (!withdrawAmount.matches(ONLY_NUMBER_REGEX)) {
            System.out.println("\nInvalid amount, only number!");
        }

        return withdrawChoice;
    }

    private static void withdrawMenu() {
        System.out.println("\n1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Enter your choice: ");
        String withdrawChoice = scanner.nextLine();

        if(withdrawChoice(withdrawChoice).equals("5")){
            choice = "1";
        } else {
            doWithdraw(withdrawAmount);
        }
    }

    private static void doWithdraw(String withdrawAmount) {
        if (Integer.parseInt(withdrawAmount) >= MIN_WITHDRAW && isMultiplyOfTen(withdrawAmount)) {
            if (Integer.parseInt(withdrawAmount) <= MAX_WITHDRAW) {
                if (loggedAccount.getBalance() >= Integer.parseInt(withdrawAmount)) {
                    loggedAccount.setBalance(loggedAccount.getBalance() - Integer.parseInt(withdrawAmount));
                    System.out.println("\nSummary");
                    System.out.println("Date: " + new SimpleDateFormat("E, dd MMM yyyy HH:mm aaa").format(new Date()));
                    System.out.println("Withdraw: $" + withdrawAmount);
                    System.out.println("Balance: $" + loggedAccount.getBalance() + "\n");

                    System.out.println("1. Transaction");
                    System.out.println("2. Exit");
                    System.out.print("Choose options: ");
                    String afterWithdraw = scanner.nextLine();

                    if (afterWithdraw.equalsIgnoreCase("2")) {
                        exit = true;
                        resetAuthenticatedAccount();
                    }
                    System.out.print("\n");
                } else {
                    System.out.println("\nInsufficient balance $" + withdrawAmount);
                    System.out.println("Your balance: $" + loggedAccount.getBalance() + "\n");
                }
            } else {
                System.out.println("\nMaximum amount to withdraw is $1000");
            }
        } else {
            System.out.println("\nInvalid amount");
        }
    }

    private static void transferMenu() {
        System.out.print("Enter destination account number: ");
        String destinationAccount = scanner.nextLine();

        int randomNumber = randomize.nextInt(999999);
        String referencedNumber = String.format("%06d", randomNumber);

        Account destinationTransfer = accounts.stream()
                .filter(c -> c.getAccountNumber().equals(destinationAccount))
                .findFirst()
                .orElse(null);

        if (destinationAccount.matches(ONLY_NUMBER_REGEX) && destinationTransfer != null) {
            System.out.print("Enter transfer amount: $");
            String transferAmount = scanner.next();
            if (!validateInputOnlyNumber(transferAmount, "Transaction Amount")) {
                scanner.nextLine();
            }
            scanner.nextLine();

            if (Integer.parseInt(transferAmount) > 0) {
                if (Integer.parseInt(transferAmount) <= MAX_TRANSFER) {
                    checkBalance(transferAmount, destinationTransfer, referencedNumber);
                } else {
                    System.out.println("\nMaximum amount to transfer is $1000");
                }
            } else {
                System.out.println("\nMinimum amount to transfer is $1");
            }
        } else {
            System.out.println("\nInvalid destination account number");
        }
    }

    private static void checkBalance(String transferAmount, Account destinationAccount, String referencedNumber) {
        if (loggedAccount.getBalance() >= Integer.parseInt(transferAmount)) {
            System.out.println("\nTransfer Confirmation");
            System.out.println("Destination Account: " + destinationAccount.getName());
            System.out.println("Transfer Amount: $" + transferAmount);
            System.out.println("Referenced Number: " + referencedNumber);

            System.out.println("\n1. Confirm");
            System.out.println("2. Cancel");
            System.out.print("Choose option: ");
            String confirmOpt = scanner.nextLine();
            confirmationTransaction(confirmOpt, transferAmount, destinationAccount, referencedNumber);
        } else {
            System.out.println("\nInsufficient balance " + transferAmount);
            System.out.println("Your balance: $" + loggedAccount.getBalance() + "\n");
        }
    }

    private static void confirmationTransaction(String confirmOpt, String transferAmount, Account destinationAccount, String referencedNumber) {
        if (confirmOpt.equalsIgnoreCase("1")) {
            loggedAccount.setBalance(loggedAccount.getBalance() - Integer.parseInt(transferAmount));
            destinationAccount.setBalance(destinationAccount.getBalance() + Integer.parseInt(transferAmount));

            System.out.println("\nFund Transfer Summary");
            System.out.println("Destination Account: " + destinationAccount.getAccountNumber());
            System.out.println("Transfer Amount: $" + transferAmount);
            System.out.println("Referenced Number: " + referencedNumber);
            System.out.println("Balance: $" + loggedAccount.getBalance());

            confirmationAfterTransaction();
        }
    }

    private static void confirmationAfterTransaction() {
        System.out.println("\n1. Transaction");
        System.out.println("2. Exit");
        System.out.print("Choose option: ");
        String afterWithdraw = scanner.nextLine();

        if (afterWithdraw.equalsIgnoreCase("2")) {
            exit = true;
            resetAuthenticatedAccount();
        }
        System.out.print("\n");
    }

    private static boolean isMultiplyOfTen(String withdrawAmount) {
        return Integer.parseInt(withdrawAmount) % 10 == 0;
    }
}
