package com.mitrais.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "transfer")
public class TransferEntity {
    @Id
    private UUID id;
    private UUID origin;
    private UUID destination;
    private int amount;
    private LocalDateTime date;

    public TransferEntity(){}

    public TransferEntity(UUID origin, UUID destination, int amount, LocalDateTime date) {
        this.id = UUID.randomUUID();
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrigin() {
        return origin;
    }

    public void setOrigin(UUID origin) {
        this.origin = origin;
    }

    public UUID getDestination() {
        return destination;
    }

    public void setDestination(UUID destination) {
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
