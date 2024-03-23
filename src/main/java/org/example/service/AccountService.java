package org.example.service;


import org.example.entity.Accounts;


public interface AccountService {
    Accounts getAccountInfo(Integer accountId);
    Integer createAccount(Accounts account);
    Accounts findAccountByDocumentId(Integer docId);

}
