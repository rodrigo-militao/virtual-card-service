package org.example.application.usecase;

import org.example.domain.exception.NotFoundException;
import org.example.domain.exception.LimitExceedException;
import org.example.domain.model.Card;
import org.example.domain.model.Transaction;
import org.example.domain.repository.CardRepository;
import org.example.domain.repository.TransactionRepository;


public class AuthorizeTransactionUseCase {
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    public AuthorizeTransactionUseCase(
            CardRepository cardRepository,
            TransactionRepository transactionRepository
    ) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction execute(AuthorizeTransactionCommand command) {
        Transaction transaction = new Transaction(command.cardId(), command.amount());
        // Find card
        Card card = cardRepository.findById(command.cardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));;

        // Check if the card has limit
        if (!card.hasAvailableLimit(command.amount())) {
            throw new LimitExceedException("Card limit exceeded");
        }

        // If yes, decrease the limit
        card.decreaseLimit(command.amount());
        cardRepository.update(card);

        // Return transaction
        transactionRepository.save(transaction);
        return transaction;
    }
}
