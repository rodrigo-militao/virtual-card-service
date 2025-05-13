package application;

import org.example.application.usecase.AuthorizeTransactionCommand;
import org.example.application.usecase.AuthorizeTransactionUseCase;
import org.example.domain.exception.LimitExceedException;
import org.example.domain.exception.NotFoundException;
import org.example.domain.model.Card;
import org.example.domain.model.CardType;
import org.example.domain.model.Transaction;
import org.example.domain.repository.CardRepository;
import org.example.domain.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthorizeTransactionUseCaseTest {
    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AuthorizeTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAuthorizeTransactionSuccessfully() {
        // Given
        UUID cardId = UUID.randomUUID();
        Card card = new Card(cardId, CardType.VIRTUAL);
        BigDecimal initialLimit = card.getLimit();
        BigDecimal amount = initialLimit.subtract(BigDecimal.ONE);
        AuthorizeTransactionCommand command = new AuthorizeTransactionCommand(cardId, amount);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

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
        UUID cardId = UUID.randomUUID();
        Card card = new Card(cardId, CardType.VIRTUAL);
        BigDecimal initialLimit = card.getLimit();
        BigDecimal amount = initialLimit.add(BigDecimal.ONE);
        AuthorizeTransactionCommand command = new AuthorizeTransactionCommand(cardId, amount);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // When
        LimitExceedException exception = assertThrows(LimitExceedException.class, () -> useCase.execute(command));

        // Then
        assertEquals("Card limit exceeded", exception.getMessage());
    }
}
