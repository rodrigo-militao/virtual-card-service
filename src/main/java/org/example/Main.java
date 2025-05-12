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
        //TODO: Improve DI and Controllers orchestration

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