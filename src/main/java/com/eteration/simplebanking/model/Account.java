package com.eteration.simplebanking.model;

import com.eteration.simplebanking.base.Transaction;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;

    private String accountNumber;

    private double balance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Date getCreateDate() {
        return this.createDate;
    }
}

