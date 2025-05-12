package domain.model;

import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void shouldCreateCard() {
        // Given
        UUID userId = UUID.randomUUID();
        CardType type = CardType.VIRTUAL;

        // When
        Card card = new Card(userId, type);

        // Then
        assertNotNull(card);
        assertNotNull(card.getId());
        assertNotNull(card.getType());
    }

    @Test
    public void shouldCheckHasAvailableLimit() {
        // Given
        CardType type = CardType.VIRTUAL;
        BigDecimal amount = new BigDecimal(10);

        // When
        Card card = new Card(UUID.randomUUID(), type);

        // Then
        assertTrue(card.hasAvailableLimit(amount));
    }

    @Test
    public void shouldCheckHasNotAvailableLimit() {
        // Given
        CardType type = CardType.VIRTUAL;
        BigDecimal amount = new BigDecimal(Integer.MAX_VALUE);

        // When
        Card card = new Card(UUID.randomUUID(), type);

        // Then
        assertFalse(card.hasAvailableLimit(amount));
    }

    @Test
    public void shouldCheckDecreaseLimit() {
        // Given
        CardType type = CardType.VIRTUAL;
        BigDecimal amount = new BigDecimal(10);

        // When
        Card card = new Card(UUID.randomUUID(), type);
        BigDecimal newLimitExpected = card.getLimit().subtract(amount);

        card.decreaseLimit(amount);

        // Then
        assertTrue(card.hasAvailableLimit(amount));
        assertEquals(card.getLimit(), newLimitExpected);
    }
}
