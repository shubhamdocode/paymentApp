package org.example.controller;

import org.example.entity.Accounts;
import org.example.entity.Transactions;
import org.example.model.RequestParam;
import org.example.service.AccountService;
import org.example.service.TransactionService;
import org.example.utils.Constants;
import org.example.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RequestMapping("/txn")
@RestController
public class TransactionController {
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @RequestMapping(value = Constants.GET_ACOOUNTINFO_BY_ID,method = RequestMethod.GET)
    public ResponseEntity<Object> getAccountInfoByAccountId(@PathVariable Integer accountId) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (accountId !=null && accountId<=0){
            responseMap.put("status", "Bad Request");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        Accounts accDetails;

        try{
             accDetails= accountService.getAccountInfo(accountId);
             if (accDetails==null){
                 responseMap.put("status", "No Record Found");
                  return  ResponseEntity.status(HttpStatus.OK).body(responseMap);
             }
            responseMap.put("accountId:", accDetails.getAccountId());
            responseMap.put("document_number:", accDetails.getDocumentNumber());
            responseMap.put("status", "Record Found");
        }
        catch (Exception e){
            responseMap.put("status", "UNKNOWN ERROR");
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @RequestMapping(value=Constants.DO_TXN, method= RequestMethod.POST)
    public ResponseEntity<String> doTransaction(@RequestBody RequestParam param) {
        Transactions resp=null;
       try{
           if ((param.getAccountId()!=null && param.getAccountId()<=0) || (param.getAmount()!=null && param.getAmount()<=0)|| !OperationType.checkValidOperationType(param.getOperationType())
           ){
               return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
           }
           // check for valid Account Id
           Accounts  accDetails= accountService.getAccountInfo(param.getAccountId());
           if (accDetails==null){
               return  ResponseEntity.status(HttpStatus.OK).body(" Account Id Not Exist");
           }
           Date date = new Date();
           Timestamp timestamp = new Timestamp(date.getTime());
           Transactions transactionsObj=new Transactions(param.getAccountId(), param.getAmount(), Objects.requireNonNull(OperationType.getValidOperationTypeCode(param.getOperationType())),timestamp);
           resp=transactionService.createTransaction(transactionsObj);
           if (resp==null){
               return  ResponseEntity.status(HttpStatus.OK).body("ERROR");
           }
       }
       catch (ConversionFailedException e){
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
       }
       catch (Exception e){
           return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UNKNOWN ERROR");
       }

        return  ResponseEntity.status(HttpStatus.OK).body("Txn Recorded :"+ resp);
    }

    @RequestMapping(value= Constants.CREATE_ACCOUNT, method= RequestMethod.POST)
    public ResponseEntity<String> createAccount(@RequestBody RequestParam param) {
        if (param.getDocumentNumber()==null||param.getDocumentNumber()<=0){
             return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }
        Integer accountId;
        //CHECK FOR ANY DUPLICATE DOCUMENT NUMBER
        try{
           Accounts accountInfo= accountService.findAccountByDocumentId(param.getDocumentNumber());
            Accounts accountData=new Accounts(param.getDocumentNumber());
           if (accountInfo!=null && accountInfo.getDocumentNumber().equals(param.getDocumentNumber())){
               return  ResponseEntity.status(HttpStatus.OK).body("Duplicate DocumentId exist:"+param.getDocumentNumber());
           }
            accountId=accountService.createAccount(accountData);
           if (accountId==null){
               return  ResponseEntity.status(HttpStatus.OK).body("ERROR");
           }
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UNKNOWN ERROR");
        }
        return  ResponseEntity.status(HttpStatus.OK).body("accountId is:"+accountId);
    }
}
