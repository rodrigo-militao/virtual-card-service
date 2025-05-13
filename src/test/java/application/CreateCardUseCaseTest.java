package application;

import org.example.application.usecase.CreateCardCommand;
import org.example.application.usecase.CreateCardUseCase;
import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.example.domain.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateCardUseCaseTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CreateCardUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCardSuccessfully() {
        // Given
        UUID userId = UUID.randomUUID();
        CardType cardType = CardType.VIRTUAL;
        Card card = new Card(userId, cardType);

        CreateCardCommand command = new CreateCardCommand(userId, cardType);

        when(cardRepository.save(any(Card.class))).thenReturn(card);

        // When I create a card
        Card result = useCase.execute(command);

        // Then should return the new card
        assertNotNull(result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(cardType, result.getType());
    }
}
