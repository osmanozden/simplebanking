package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.dto.CreditDto;
import com.eteration.simplebanking.dto.DebitDto;
import com.eteration.simplebanking.exeption.InsufficientBalanceException;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.dto.TransactionStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatusDto> creditAccount(
            @PathVariable String accountNumber,
            @RequestBody CreditDto creditDto) {
        String transactionStatus = accountService.creditAccount(accountNumber, creditDto);
        return null;
    }


    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatusDto> debitAccount(
            @PathVariable String accountNumber,
            @RequestBody DebitDto debitDto) throws InsufficientBalanceException {
        TransactionStatusDto transactionStatusDto = accountService.debitAccount(accountNumber, debitDto);
        return ResponseEntity.ok(transactionStatusDto);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto>getBankAccountDetails(@PathVariable String accountNumber) {
        AccountDto bankAccountResponse = accountService.getBankAccountDetails(accountNumber);
        return ResponseEntity.ok(bankAccountResponse);
    }
}
