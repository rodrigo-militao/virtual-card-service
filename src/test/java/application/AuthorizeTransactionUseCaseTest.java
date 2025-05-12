package application;

import org.example.application.usecase.AuthorizeTransactionCommand;
import org.example.application.usecase.AuthorizeTransactionUseCase;
import org.example.domain.exception.LimitExceedException;
import org.example.domain.exception.NotFoundException;
import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.example.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizeTransactionUseCaseTest {

    private AuthorizeTransactionUseCase useCase;
    private InMemoryCardRepository cardRepository;
    private InMemoryTransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        cardRepository = new InMemoryCardRepository();
        transactionRepository = new InMemoryTransactionRepository();

        useCase = new AuthorizeTransactionUseCase(
                cardRepository,
                transactionRepository
        );
    }

    @Test
    void shouldAuthorizeTransactionSuccessfully() {
        // Given
        Card card = cardRepository.save(new Card(UUID.randomUUID(), CardType.VIRTUAL));
        BigDecimal initialLimit = card.getLimit();
        UUID cardId = card.getId();
        BigDecimal amount = new BigDecimal(900);
        AuthorizeTransactionCommand command = new AuthorizeTransactionCommand(cardId, amount);

        // When
        Transaction transaction = useCase.execute(command);

        // Then
        assertNotNull(transaction);
        assertEquals(amount, transaction.getAmount());
        assertEquals(cardId, transaction.getCardId());
        assertEquals(card.getLimit(), initialLimit.subtract(amount));
    }

    @Test
    void shouldThrowAnExceptionIfCardWasNotFound() {
        // Given
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(900);
        AuthorizeTransactionCommand command = new AuthorizeTransactionCommand(cardId, amount);

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Then
        assertEquals("Card not found", exception.getMessage());
    }

    @Test
    void shouldThrowAnExceptionIfCardHasNotEnoughLimit() {
        // Given
        Card card = cardRepository.save(new Card(UUID.randomUUID(), CardType.VIRTUAL));
        BigDecimal initialLimit = card.getLimit();
        UUID cardId = card.getId();
        BigDecimal amount = initialLimit.add(BigDecimal.ONE);
        AuthorizeTransactionCommand command = new AuthorizeTransactionCommand(cardId, amount);

        // When
        LimitExceedException exception = assertThrows(LimitExceedException.class, () -> useCase.execute(command));

        // Then
        assertEquals("Card limit exceeded", exception.getMessage());
    }
}
