package org.example.controller;

import org.example.entity.Accounts;
import org.example.entity.Transactions;
import org.example.model.RequestParam;
import org.example.service.AccountService;
import org.example.service.TransactionService;
import org.example.utils.OperationType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    TransactionController controllerTest;

    @Before
    public void setup() {
        controllerTest= new TransactionController();
        MockitoAnnotations.initMocks(this);
        controllerTest.accountService=mock(AccountService.class);
        controllerTest.transactionService=mock(TransactionService.class);
    }
    @Test
     public void getAccountInfoByAccountIdTest() throws Exception {
        ResponseEntity<Object> actualResult;
        ResponseEntity<Object> expectedResult;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("accountId:", 1);
        responseMap.put("document_number:", 123);
        responseMap.put("status", "Record Found");
        expectedResult= ResponseEntity.status(HttpStatus.OK).body(responseMap);
        Accounts expectedAccountsRes= new Accounts();
        expectedAccountsRes.setAccountID(1);
        expectedAccountsRes.setDocumentNumber(123);
        when(controllerTest.accountService.getAccountInfo(1)).thenReturn(expectedAccountsRes);
        actualResult=controllerTest.getAccountInfoByAccountId(1);

        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void getAccountInfoByAccountIdBadRequestTest() throws Exception {
        ResponseEntity<Object> actualResult;
        ResponseEntity<Object> expectedResult;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("status", "Bad Request");
        expectedResult= ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        Accounts expectedAccountsRes= new Accounts();
        expectedAccountsRes.setAccountID(-123);
        expectedAccountsRes.setDocumentNumber(123);
        actualResult=controllerTest.getAccountInfoByAccountId(-123);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    public void getAccountInfoByAccountIdNoRecordFoundTest() throws Exception {
        ResponseEntity<Object> actualResult;
        ResponseEntity<Object> expectedResult;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("status", "No Record Found");
        expectedResult= ResponseEntity.status(HttpStatus.OK).body(responseMap);
        when(controllerTest.accountService.getAccountInfo(1)).thenReturn(null);
        actualResult=controllerTest.getAccountInfoByAccountId(1);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void createAccountBadRequestTest() throws Exception {
        RequestParam param= new RequestParam();
        ResponseEntity<String> actualResult;
        ResponseEntity<String> expectedResult;
        expectedResult= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        actualResult=controllerTest.createAccount(param);
        assertEquals(expectedResult,actualResult);
    }


    @Test
    public void createAccountDocumentIdAlreadyExistTest() throws Exception {
        RequestParam param= new RequestParam();
        param.setDocumentNumber(12345);
        ResponseEntity<String> actualResult;
        ResponseEntity<String> expectedResult;
        expectedResult= ResponseEntity.status(HttpStatus.OK).body("Duplicate DocumentId exist:"+param.getDocumentNumber());
        Accounts accountInfo=new Accounts(param.getDocumentNumber());
        accountInfo.setAccountID(1);
        when(controllerTest.accountService.findAccountByDocumentId(param.getDocumentNumber())).thenReturn(accountInfo);
        actualResult=controllerTest.createAccount(param);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    public void createAccountSuccessTest() throws Exception {
        RequestParam param= new RequestParam();
        param.setDocumentNumber(12345);
        ResponseEntity<String> actualResult;
        ResponseEntity<String> expectedResult;
        expectedResult= ResponseEntity.status(HttpStatus.OK).body("accountId is:"+1);
        Accounts accountInfo=new Accounts(param.getDocumentNumber());
        when(controllerTest.accountService.findAccountByDocumentId(param.getDocumentNumber())).thenReturn(null);
        when(controllerTest.accountService.createAccount(accountInfo)).thenReturn(1);
        actualResult=controllerTest.createAccount(param);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void createTransactionSuccessTest() throws Exception {
        RequestParam param= new RequestParam();
        param.setAccountId(1);
        param.setAmount(123.0f);
        param.setOperationType((short) 2);
        Accounts accountResultInfo=new Accounts(param.getDocumentNumber());
        accountResultInfo.setAccountID(1);
        ResponseEntity<String> actualResult;
        ResponseEntity<String> expectedResult;
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Transactions transactionsResultObj=new Transactions(param.getAccountId(), -param.getAmount(), Objects.requireNonNull(OperationType.getValidOperationTypeCode(param.getOperationType())),timestamp);
        transactionsResultObj.setTxnID(12343444);
        when(controllerTest.accountService.getAccountInfo(param.getAccountId())).thenReturn(accountResultInfo);
        when(controllerTest.transactionService.createTransaction(anyObject())).thenReturn(transactionsResultObj);
        actualResult=controllerTest.doTransaction(param);
        expectedResult= ResponseEntity.status(HttpStatus.OK).body("Txn Recorded :"+ transactionsResultObj);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    public void createTransactionBadOperationTypeTest() throws Exception {
        RequestParam param= new RequestParam();
        param.setAccountId(1);
        param.setAmount(123.0f);
        param.setOperationType((short) 5);
        ResponseEntity<String> actualResult;
        ResponseEntity<String> expectedResult;
        actualResult=controllerTest.doTransaction(param);
        expectedResult=ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        assertEquals(expectedResult,actualResult);
    }


}
