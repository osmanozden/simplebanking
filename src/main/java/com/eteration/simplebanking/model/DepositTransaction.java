package com.eteration.simplebanking.model;


import com.eteration.simplebanking.base.Transaction;
import lombok.Data;
import org.hibernate.annotations.Entity;

// This class is a place holder you can change the complete implementation
@Entity
@Data
public class DepositTransaction extends Transaction {

    private String depositedBy;

    @Override
    public String getApprovalCode() {
        // Implementation specific to WithdrawalTransaction
        return "approval-code-for-deposit";
    }
}
