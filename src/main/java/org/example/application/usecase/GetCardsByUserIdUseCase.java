package org.example.application.usecase;

import org.example.domain.model.Card;
import org.example.domain.repository.CardRepository;

import java.util.List;
import java.util.UUID;

public class GetCardsByUserIdUseCase {
    private final CardRepository cardRepository;

    public GetCardsByUserIdUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> execute(UUID userId) {
        return cardRepository.findAllByUserId(userId);
    }
}
