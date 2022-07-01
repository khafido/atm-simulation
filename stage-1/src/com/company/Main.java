package com.company;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("John Doe", "012108", 100, "112233"));
        accounts.add(new Account("Jane Doe", "932012", 30, "112244"));

        Account account = new Account();
        String accountNumber;
        String pin;

        String withdrawAmount = "0";

        boolean authenticated = false;
        boolean exit = false;

        while (!authenticated) {
            System.out.print("Enter Account Number: ");
            accountNumber = scanner.nextLine();
            if (!(accountNumber.length() == 6)) {
                System.out.println("\nAccount Number should have 6 digits length");
                continue;
            }
            if (!accountNumber.matches("[0-9]+")) {
                System.out.println("\nAccount Number should only contain numbers");
                continue;
            }

            System.out.print("Enter PIN: ");
            pin = scanner.nextLine();
            if (!(pin.length() == 6)) {
                System.out.println("\nPIN should have 6 digits length");
                continue;
            }

            if (!pin.matches("[0-9]+")) {
                System.out.println("\nPIN should only contain numbers");
                continue;
            }

            String finalAccountNumber = accountNumber;
            String finalPin = pin;
            account = accounts.stream()
                    .filter(c -> c.getAccountNumber().equals(finalAccountNumber))
                    .filter(c -> c.getPin().equals(finalPin))
                    .findFirst()
                    .orElse(null);

            if (account != null) {
                System.out.println("\nWelcome " + account.getName());
                authenticated = true;
            } else {
                System.out.println("\nInvalid Account Number or PIN");
            }
        }

        String choice = "0";
        while (!exit) {
            System.out.println("1. Withdraw");
            System.out.println("2. Fund Transfer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            boolean back = false;

            switch (choice) {
                case "1":
                    System.out.println("\n1. $10");
                    System.out.println("2. $50");
                    System.out.println("3. $100");
                    System.out.println("4. Other");
                    System.out.println("5. Back");
                    System.out.print("Enter your choice: ");
                    String withdrawChoice = scanner.nextLine();

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
                            back = true;
                            System.out.print("\n");
                            break;
                        default:
                            back = true;
                            System.out.println("\nInvalid choice");
                            break;
                    }

                    if (!withdrawAmount.matches("[0-9]+")) {
                        System.out.println("\nInvalid amount, only number!");
                        continue;
                    }

                    if (Integer.parseInt(withdrawAmount) % 10 != 0) {
                        System.out.println("\nInvalid amount, only multiple of 10!");
                        continue;
                    }

                    if (!back) {
                        if (Integer.parseInt(withdrawAmount) >= 10) {
                            if (Integer.parseInt(withdrawAmount) <= 1000) {
                                if (account.getBalance() >= Integer.parseInt(withdrawAmount)) {
                                    account.setBalance(account.getBalance() - Integer.parseInt(withdrawAmount));
                                    System.out.println("\nSummary");
                                    System.out.println("Date: " + new SimpleDateFormat("E, dd MMM yyyy HH:mm aaa").format(new Date()));
                                    System.out.println("Withdraw: $" + withdrawAmount);
                                    System.out.println("Balance: $" + account.getBalance() + "\n");

                                    System.out.println("1. Transaction");
                                    System.out.println("2. Exit");
                                    System.out.print("Choose option: ");
                                    String afterWithdraw = scanner.nextLine();

                                    if (afterWithdraw.equalsIgnoreCase("2")) {
                                        exit = true;
                                    } else {
                                        System.out.print("\n");
                                    }
                                } else {
                                    System.out.println("\nInsufficient balance $" + withdrawAmount);
                                    System.out.println("Your balance: $" + account.getBalance() + "\n");
                                }
                            } else {
                                System.out.println("\nMaximum amount to withdraw is $1000");
                            }
                        } else {
                            System.out.println("\nMinimum amount to withdraw is $10");
                        }
                    }
                    break;
                case "2":
                    System.out.print("Enter destination account number: ");
                    String destinationAccount = scanner.nextLine();

                    int number = new Random().nextInt(999999);
                    String referencedNumber = String.format("%06d", number);

                    Account destinationCustomer = accounts.stream()
                            .filter(c -> c.getAccountNumber().equals(destinationAccount))
                            .findFirst()
                            .orElse(null);

                    if (destinationAccount.matches("[0-9]+") && destinationCustomer != null) {
                        System.out.print("Enter transfer amount: $");
                        String transferAmount = scanner.next();
                        if (!transferAmount.matches("[0-9]+")) {
                            System.out.println("\nInvalid amount");
                            scanner.nextLine();
                            continue;
                        }
                        scanner.nextLine();

                        if (Integer.parseInt(transferAmount) > 0) {
                            if (Integer.parseInt(transferAmount) <= 1000) {
                                if (account.getBalance() >= Integer.parseInt(transferAmount)) {
                                    System.out.println("\nTransfer Confirmation");
                                    System.out.println("Destination Account: " + destinationAccount);
                                    System.out.println("Transfer Amount: $" + transferAmount);
                                    System.out.println("Referenced Number: " + referencedNumber);

                                    System.out.println("\n1. Confirm");
                                    System.out.println("2. Cancel");
                                    System.out.print("Choose option: ");
                                    String confirmOpt = scanner.nextLine();
                                    if (confirmOpt.equalsIgnoreCase("1")) {
                                        account.setBalance(account.getBalance() - Integer.parseInt(transferAmount));
                                        destinationCustomer.setBalance(destinationCustomer.getBalance() + Integer.parseInt(transferAmount));

                                        System.out.println("\nFund Transfer Summary");
                                        System.out.println("Destination Account: " + destinationAccount);
                                        System.out.println("Transfer Amount: $" + transferAmount);
                                        System.out.println("Referenced Number: " + referencedNumber);
                                        System.out.println("Balance: $" + account.getBalance());

                                        System.out.println("\n1. Transaction");
                                        System.out.println("2. Exit");
                                        System.out.print("Choose option: ");
                                        String afterWithdraw = scanner.nextLine();

                                        if (afterWithdraw.equalsIgnoreCase("2")) {
                                            exit = true;
                                        } else {
                                            System.out.print("\n");
                                        }
                                    }
                                } else {
                                    System.out.println("\nInsufficient balance " + transferAmount);
                                    System.out.println("Your balance: $" + account.getBalance() + "\n");
                                }
                            } else {
                                System.out.println("\nMaximum amount to transfer is $1000");
                            }
                        } else {
                            System.out.println("\nMinimum amount to transfer is $1");
                        }
                    } else {
                        System.out.println("\nInvalid destination account number");
                    }
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    System.out.println("\nInvalid choice");
                    break;
            }
        }
    }
}

class Account {
    private String name;
    private String pin;
    private Integer balance;
    private String accountNumber;

    public Account() {
    }

    public Account(String name, String pin, Integer balance, String accountNumber) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String acccountNumber) {
        this.accountNumber = acccountNumber;
    }
}
