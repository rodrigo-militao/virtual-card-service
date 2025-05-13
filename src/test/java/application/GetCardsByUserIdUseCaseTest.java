package application;

import org.example.application.usecase.GetCardsByUserIdUseCase;
import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.example.domain.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GetCardsByUserIdUseCaseTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private GetCardsByUserIdUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllCardsByUserId() {
        // Given
        UUID userId = UUID.randomUUID();
        List<Card> cards = List.of(
                new Card(userId, CardType.VIRTUAL),
                new Card(userId, CardType.PHYSICAL),
                new Card(userId, CardType.PHYSICAL)
        );

        when(cardRepository.findAllByUserId(userId)).thenReturn(cards);

        // When
        List<Card> result = useCase.execute(userId);

        // Then
        assertNotNull(result);
        assertEquals(cards.size(), result.size());
        result.forEach(card -> assertEquals(card.getUserId(), userId));
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
