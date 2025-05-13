package org.example.infrastructure.repository;

import org.example.domain.model.Transaction;
import org.example.domain.repository.TransactionRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<UUID, Transaction> storage = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        if (exists(transaction.getId())) {
            throw new IllegalArgumentException("Card already exists");
        }
        storage.put(transaction.getId(), transaction);
        return transaction;
    }

    public Boolean exists(UUID id) {
        return storage.containsKey(id);
    }
}
