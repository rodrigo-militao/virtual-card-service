package org.example.delivery.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.usecase.AuthorizeTransactionCommand;
import org.example.application.usecase.AuthorizeTransactionUseCase;

import static spark.Spark.post;

public class TransactionController {
    private final AuthorizeTransactionUseCase authorizeTransactionUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransactionController(AuthorizeTransactionUseCase authorizeTransactionUseCase) {
        this.authorizeTransactionUseCase = authorizeTransactionUseCase;
    }

    public void registerRoutes() {
        post("/transaction", (req, res) -> {
            try {
                AuthorizeTransactionCommand command = objectMapper.readValue(req.body(), AuthorizeTransactionCommand.class);

                var result = authorizeTransactionUseCase.execute(command);
                res.type("application/json");

                return objectMapper.writeValueAsString(result);

            } catch (Exception e) {
                res.status(500);
                return "{\"message\": \"" + e.getMessage() + "\"}";
            }
        });
    }
}
