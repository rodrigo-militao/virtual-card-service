package org.example.application.usecase;

import org.example.domain.model.Card;
import org.example.domain.repository.CardRepository;

public class CreateCardUseCase {

    private final CardRepository cardRepository;

    public CreateCardUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card execute(CreateCardCommand cardCommand) {
        Card card = new Card(cardCommand.userId(), cardCommand.type());
        return cardRepository.save(card);
    }
}
