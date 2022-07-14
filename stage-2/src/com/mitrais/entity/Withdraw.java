package com.mitrais.entity;

import java.time.LocalDateTime;

public class Withdraw {
    private String id;
    private Account account;
    private int amount;
    private LocalDateTime date;

    public Withdraw() {
    }

    public Withdraw(String id, Account account, int amount, LocalDateTime date) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Withdraw{" +
                "id='" + id + '\'' +
                ", account=" + account.getName() +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
