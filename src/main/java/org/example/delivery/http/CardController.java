package org.example.delivery.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.usecase.CreateCardCommand;
import org.example.application.usecase.CreateCardUseCase;
import org.example.application.usecase.GetCardsByUserIdUseCase;
import org.example.domain.model.Card;

import java.util.List;
import java.util.UUID;

import static spark.Spark.*;



public class CardController {
    private final CreateCardUseCase createCardUseCase;
    private final GetCardsByUserIdUseCase getCardsByUserIdUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CardController(CreateCardUseCase createCardUseCase,
                          GetCardsByUserIdUseCase getCardsByUserIdUseCase) {
        this.createCardUseCase = createCardUseCase;
        this.getCardsByUserIdUseCase = getCardsByUserIdUseCase;
    }

    public void registerRoutes() {
        post("/cards", (req, res) -> {
            try {
                CreateCardCommand command = objectMapper.readValue(req.body(), CreateCardCommand.class);
                Card card = createCardUseCase.execute(command);

                res.status(201);
                res.type("application/json");

                return objectMapper.writeValueAsString(card);
            } catch (Exception e) {
                res.status(500);
                return "{\"message\": \"" + e.getMessage() + "\"}";
            }
        });

        get("/cards/:userId", (req, res) -> {
            UUID userId = UUID.fromString(req.params(":userId"));
            List<Card> cards = getCardsByUserIdUseCase.execute(userId);

            res.type("application/json");

            return objectMapper.writeValueAsString(cards);
        });
    }
}
