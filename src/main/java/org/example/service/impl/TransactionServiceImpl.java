package org.example.service.impl;

import org.example.entity.Transactions;
import org.example.repository.TransactionsRepository;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                transaction.setBalance(transaction.getAmount());
                break;
            case Withdrawal:
                transaction.setAmount(-transaction.getAmount());
                transaction.setBalance(transaction.getAmount());
                break;
            case Purchase_With_Installments:
                transaction.setAmount(-transaction.getAmount());
                transaction.setBalance(transaction.getAmount());
                break;
            case Credit_Voucher:
                transaction.setBalance(transaction.getAmount());
                Float initalCreditAmount=transaction.getAmount();;
                Float reaminingCreditAmt= initalCreditAmount;
                Float amountUsed=0.0f;
                List<Transactions> allTnxs= transactionsRepository.findAllTransactionByAccId(transaction.getAccountID());
                for(Transactions txn: allTnxs){
                 if(txn.getBalance()<0 && reaminingCreditAmt>0){
                     reaminingCreditAmt=reaminingCreditAmt-Math.abs(txn.getBalance());
                     if(reaminingCreditAmt>0){
                         amountUsed=amountUsed+Math.abs(txn.getBalance());
                         txn.setBalance(0.0f);
                         transactionsRepository.saveAndFlush(txn);
                         continue;
                     }
                     txn.setBalance(reaminingCreditAmt);
                     transactionsRepository.saveAndFlush(txn);
                 }else if(reaminingCreditAmt<=0){
                     amountUsed=0.0f;
                     transaction.setBalance(amountUsed);
                     break;
                 }
               }
                if (amountUsed>0){
                    transaction.setBalance(initalCreditAmount-amountUsed);
                }


        }

        return transactionsRepository.saveAndFlush(transaction);
    }
}
