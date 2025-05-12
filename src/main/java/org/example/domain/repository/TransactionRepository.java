package org.example.domain.repository;

import org.example.domain.model.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
}
