package org.example.domain.repository;

import org.example.domain.model.Card;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository {
    Card save(Card card);
    void update(Card card);
    Optional<Card> findById(UUID id);
    List<Card> findAllByUserId(UUID userId);
}
