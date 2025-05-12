package application;

import org.example.application.usecase.CreateCardCommand;
import org.example.application.usecase.CreateCardUseCase;
import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateCardUseCaseTest {

    private CreateCardUseCase useCase;
    private InMemoryCardRepository cardRepository;

    @BeforeEach
    void setUp() {
        cardRepository = new InMemoryCardRepository();
        useCase = new CreateCardUseCase(cardRepository);
    }

    @Test
    void shouldCreateCardSuccessfully() {
        // Given
        UUID userId = UUID.randomUUID();
        CardType cardType = CardType.VIRTUAL;

        CreateCardCommand command = new CreateCardCommand(userId, cardType);

        // When I create a card
        Card card = useCase.execute(command);

        // Then should return the new card
        assertNotNull(card.getId());
        assertEquals(userId, card.getUserId());
        assertEquals(cardType, card.getType());
        assertTrue(cardRepository.exists(card.getId()));
    }
}
