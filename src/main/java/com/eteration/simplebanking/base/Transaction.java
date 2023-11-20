package com.eteration.simplebanking.base;


import javax.persistence.Entity;
import java.util.Date;

import com.eteration.simplebanking.model.Account;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Date date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private Account account;


    public abstract String getApprovalCode();
}

