package com.eteration.simplebanking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// This class is a place holder you can change the complete implementation


@Data
@NoArgsConstructor
public class TransactionStatusDto {

    private String status;

    private String approvalCode;

    public TransactionStatusDto(String status, String approvalCode) {
        this.status = status;
        this.approvalCode = approvalCode;
    }
}

