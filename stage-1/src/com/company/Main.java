package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "012108", 100, "112233"));
        customers.add(new Customer("Jane Doe", "932012", 30, "112244"));

        Customer customer = new Customer();
        String accountNumber;
        String pin;

        Integer withdrawAmount = 0;

        boolean authenticated = false;
        boolean exit = false;

        while (!authenticated) {
            System.out.print("Enter Account Number: ");
            accountNumber = scanner.nextLine();
            System.out.print("Enter PIN: ");
            pin = scanner.nextLine();

            if (accountNumber.length() < 6){
                System.out.println("\nAccount Number should have 6 digits length");
                continue;
            }

            if(pin.length() < 6){
                System.out.println("\nPIN should have 6 digits length");
                continue;
            }

            if (!accountNumber.matches("[0-9]+")) {
                System.out.println("\nAccount Number should only contain numbers");
                continue;
            }

            if (!pin.matches("[0-9]+")) {
                System.out.println("\nPIN should only contain numbers");
                continue;
            }

            String finalAccountNumber = accountNumber;
            String finalPin = pin;
            customer = customers.stream()
                    .filter(c -> c.getAccountNumber().equals(finalAccountNumber))
                    .filter(c -> c.getPin().equals(finalPin))
                    .findFirst()
                    .orElse(null);

            if (customer != null) {
                System.out.println("\nWelcome " + customer.getName());
                authenticated = true;
            } else {
                System.out.println("\nInvalid Account Number or PIN");
            }
        }

        while (!exit) {

            System.out.println("1. Withdraw");
            System.out.println("2. Fund Transfer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("1. $10");
                    System.out.println("2. $50");
                    System.out.println("3. $100");
                    System.out.println("4. Other");
                    System.out.println("5. Back");
                    System.out.print("Enter your choice: ");
                    int withdrawChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (withdrawChoice) {
                        case 1:
                            withdrawAmount = 10;
                            break;
                        case 2:
                            withdrawAmount = 50;
                            break;
                        case 3:
                            withdrawAmount = 100;
                            break;
                        case 4:
                            System.out.print("Enter amount: ");
                            withdrawAmount = scanner.nextInt();
                            scanner.nextLine();
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("\nInvalid choice \n");
                            break;
                    }

                    if (withdrawAmount > 0) {
                        if (customer.getBalance() >= withdrawAmount) {
                            customer.setBalance(customer.getBalance() - withdrawAmount);
                            System.out.println("\nWithdrawal successful");
                            System.out.println("New Balance: " + customer.getBalance() + "\n");
                        } else {
                            System.out.println("\nInsufficient balance\n");
                        }
                    } else {
                        System.out.println("\n Invalid amount");
                    }
                    break;
                case 2:
                    System.out.print("Enter amount: ");
                    int transferAmount = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter destination account number: ");
                    String destinationAccount = scanner.nextLine();
                    customer.setBalance(customer.getBalance() - withdrawAmount);

                    Customer destinationCustomer = customers.stream()
                            .filter(c -> c.getAccountNumber().equals(destinationAccount))
                            .findFirst()
                            .orElse(null);

                    if (destinationCustomer != null) {
                        if (customer.getBalance() >= transferAmount) {
                            customer.setBalance(customer.getBalance() - transferAmount);
                            destinationCustomer.setBalance(destinationCustomer.getBalance() + transferAmount);
                            System.out.println("\nTransfer successful");
                            System.out.println("New Balance: " + customer.getBalance() + "\n");
                        } else {
                            System.out.println("\nInsufficient balance\n");
                        }
                    } else {
                        System.out.println("\nInvalid destination account number");
                    }
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}

class Customer {
    private String name;
    private String pin;
    private Integer balance;
    private String accountNumber;

    public Customer() {
    }

    public Customer(String name, String pin, Integer balance, String accountNumber) {
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
