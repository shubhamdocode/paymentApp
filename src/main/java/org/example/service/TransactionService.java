package org.example.service;

import org.example.entity.Accounts;
import org.example.entity.Transactions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface TransactionService {
    Transactions createTransaction(Transactions transaction);
}
