package org.example.infrastructure.repository;

import org.example.domain.model.Card;
import org.example.domain.repository.CardRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryCardRepository implements CardRepository {

    private final Map<UUID, Card> storage = new HashMap<>();

    @Override
    public Card save(Card card) {
        if (exists(card.getId())) {
            throw new IllegalArgumentException("Card already exists");
        }
        storage.put(card.getId(), card);
        return card;
    }

    @Override
    public void update(Card card) {
        storage.put(card.getId(), card);
    }

    @Override
    public Optional<Card> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Card> findAllByUserId(UUID userId) {
        return storage.values().stream()
                .filter(card -> card.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Boolean exists(UUID id) {
        return storage.containsKey(id);
    }
}
