package org.example.entity;

import lombok.Data;
import org.example.utils.OperationType;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
public class Transactions {

    @Column(name="Transaction_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer  txnID;
    @Column(name="Account_ID")
    private Integer accountID;
    @Column(name="Amount")
    private  Float amount;
    @Column(name="EventDate")
    private Date eventDate;
    @Column(name="OperationType_ID")
    private Integer operationTypeID;
    @Column(name="Balance")
    private Float balance;
    @Transient
    private OperationType operationType;

    public Transactions(Integer accountID, Float amount, OperationType operationTypeData ,Date eventDate) {
        this.accountID=accountID;
        this.amount=amount;
        this.eventDate=eventDate;
        this.operationTypeID=operationTypeData.getCode();
        this.operationType=operationTypeData;
    }
    public Transactions(){

    }

}
