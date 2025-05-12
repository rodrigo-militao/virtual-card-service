package application;

import org.example.application.usecase.GetCardsByUserIdUseCase;
import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetCardsByUserIdUseCaseTest {
    private GetCardsByUserIdUseCase useCase;
    private InMemoryCardRepository cardRepository;

    @BeforeEach
    void setUp() {
        cardRepository = new InMemoryCardRepository();
        useCase = new GetCardsByUserIdUseCase(cardRepository);
    }

    @Test
    void shouldReturnAllCardsByUserId() {
        // Given
        UUID userId = UUID.randomUUID();
        cardRepository.save(new Card(userId, CardType.VIRTUAL));
        cardRepository.save(new Card(UUID.randomUUID(), CardType.PHYSICAL));
        cardRepository.save(new Card(UUID.randomUUID(), CardType.VIRTUAL));

        // When
        List<Card> cards = useCase.execute(userId);

        // Then
        assertNotNull(cards);
        assertEquals(1, cards.size());
        cards.forEach(card -> assertEquals(card.getUserId(), userId));
    }

    @Test
    void shouldReturnEmptyListWhenUserNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        cardRepository.save(new Card(UUID.randomUUID(), CardType.VIRTUAL));

        // When
        List<Card> cards = useCase.execute(userId);

        // Then
        assertNotNull(cards);
        assertTrue(cards.isEmpty());
    }
}
