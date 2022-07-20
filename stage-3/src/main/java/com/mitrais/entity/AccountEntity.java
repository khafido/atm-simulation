package com.mitrais.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

import static javax.persistence.CascadeType.REMOVE;

@Entity(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    private String name;
    private String pin;
    private Integer balance;

    public AccountEntity() {
    }

    public AccountEntity(String accountNumber, String name, String pin, Integer balance) {
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

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}