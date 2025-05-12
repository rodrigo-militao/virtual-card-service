package org.example.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    UUID id;
    UUID cardId;
    BigDecimal amount;
    LocalDateTime timestamp;

    public Transaction(UUID cardId, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.cardId = cardId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getCardId() {
        return cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
