package org.example.service.impl;


import org.example.entity.Accounts;
import org.example.repository.AccountsRepository;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountsRepository accountsRepository;
    @Override
    public Accounts getAccountInfo(Integer accountId) {
        return accountsRepository.findOne(accountId);
    }
    @Override
    public Integer createAccount(Accounts accountInfo) {
        return accountsRepository.saveAndFlush(accountInfo).getAccountId();
    }


    @Override
    public Accounts  findAccountByDocumentId(Integer documentId) {
        return accountsRepository.findAccountByDocumentId(documentId);
    }


}
