package com.mitrais.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "withdraw")
public class WithdrawEntity {
    @Id
    private UUID id;
    private String account;
    private int amount;
    private LocalDateTime date;

    public WithdrawEntity() {
    }

    public WithdrawEntity(String id, String account, int amount, LocalDateTime date) {
        this.id = UUID.randomUUID();
        this.account = account;
        this.amount = amount;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
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
                ", account=" + account +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
