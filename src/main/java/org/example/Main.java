package org.example;

import org.example.application.usecase.AuthorizeTransactionUseCase;
import org.example.application.usecase.CreateCardUseCase;
import org.example.application.usecase.GetCardsByUserIdUseCase;
import org.example.delivery.http.CardController;
import org.example.delivery.http.TransactionController;
import org.example.infrastructure.repository.InMemoryCardRepository;
import org.example.infrastructure.repository.InMemoryTransactionRepository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*
         * Scenario: Simplified Virtual Card Service
         * You're building a backend service for creating and managing virtual cards.
         * Each card belongs to a user and has a limit.
         * Users can request transactions, and the system must validate and record them.
         *
         * Routes:
         * POST /cards -> create a new card
         * GET /cards/{userId} -> List all cards by user
         *
         * POST /authorize -> authorize a transaction
         * If the card has enough balance, approve the transaction and reduce the balance.
         * Otherwise, reject the transaction.
         *
         * */

        // Repositories
        var cardRepository = new InMemoryCardRepository();
        var transactionRepository = new InMemoryTransactionRepository();

        // UseCases
        var createCardUseCase = new CreateCardUseCase(cardRepository);
        var getCardsByUserIdUseCase = new GetCardsByUserIdUseCase(cardRepository);
        var authorizeTransactionUseCase = new AuthorizeTransactionUseCase(
                cardRepository,
                transactionRepository);

        // Controllers
        var cardController = new CardController(
                createCardUseCase,
                getCardsByUserIdUseCase
        );
        var transactionController = new TransactionController(
                authorizeTransactionUseCase
        );


        // Register routes
        cardController.registerRoutes();
        transactionController.registerRoutes();
    }
}