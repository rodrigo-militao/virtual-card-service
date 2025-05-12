package org.example.application.usecase;

import java.math.BigDecimal;
import java.util.UUID;

public record AuthorizeTransactionCommand(UUID cardId, BigDecimal amount) {}
