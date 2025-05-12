package org.example.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Card {
    UUID id;
    UUID userId;
    BigDecimal limit;
    CardType type;

    public Card(UUID userId, CardType type) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.type = type;

        this.limit = new BigDecimal(1000);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public CardType getType() {
        return type;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public Boolean hasAvailableLimit(BigDecimal amount) {
        return amount.compareTo(limit) <= 0;
    }

    public void decreaseLimit(BigDecimal amount) {
        limit = limit.subtract(amount);
    }
}
