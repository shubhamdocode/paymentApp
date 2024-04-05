package org.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Accounts {
    @Column(name="Account_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer accountID;
    @Column(name="Document_Number")
    private Integer documentNumber;

    public Integer getDocumentNumber() {
        return documentNumber;
    }

    public Integer getAccountId() {
        return accountID;
    }


    public Accounts() {
    }

    public Accounts(Integer DocumentID) {
        this.documentNumber = DocumentID;
    }
}

