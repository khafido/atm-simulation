package com.mitrais.entity;

import java.time.LocalDateTime;

public class Transfer {
    private String id;
    private Account origin;
    private Account destination;
    private int amount;
    private LocalDateTime date;

    public Transfer(){}

    public Transfer(String id, Account origin, Account destination, int amount, LocalDateTime date) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
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
}
