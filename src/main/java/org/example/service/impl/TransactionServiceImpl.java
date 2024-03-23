package org.example.service.impl;

import org.example.entity.Transactions;
import org.example.repository.TransactionsRepository;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionsRepository transactionsRepository;
    @Override
    public Transactions createTransaction(Transactions transaction) {
        //check for account id exist
        switch(transaction.getOperationType())
        {
            case Normal_Purchase:
                transaction.setAmount(-transaction.getAmount());
            case Withdrawal:
                transaction.setAmount(-transaction.getAmount());
            case Purchase_With_Installments:
                transaction.setAmount(-transaction.getAmount());
        }
        System.out.println("tran:"+transaction);
        return transactionsRepository.saveAndFlush(transaction);
    }
}
