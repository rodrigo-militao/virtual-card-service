package org.example.application.usecase;

import org.example.domain.model.CardType;

import java.util.UUID;

public record CreateCardCommand(UUID userId, CardType type) {}
