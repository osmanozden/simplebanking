package com.eteration.simplebanking.services;


import com.eteration.simplebanking.base.Transaction;
import com.eteration.simplebanking.dto.*;
import com.eteration.simplebanking.exeption.InsufficientBalanceException;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// This class is a place holder you can change the complete implementation

@Service
@AllArgsConstructor
@RequiredArgsConstructor

public class AccountService {


    @Autowired
    private final AccountRepository accountRepository ;
    @Autowired
    private final TransactionRepository transactionRepository;

    public String creditAccount(String accountNumber, CreditDto creditDto) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account != null) {
            account.setBalance(account.getBalance() + creditDto.getAmount());

            DepositTransaction depositTransaction = new DepositTransaction();
            depositTransaction.setType("Deposit");
            depositTransaction.setDate(new Date());
            depositTransaction.setAmount(creditDto.getAmount());
            depositTransaction.setDepositedBy("Some Source");

            account.getTransactions().add(depositTransaction);
            depositTransaction.setAccount(account);

            accountRepository.save(account);
            transactionRepository.save(depositTransaction);

            return generateApprovalCode();
        } else {
            throw new RuntimeException("Bank account not found");
        }
    }


    public TransactionStatusDto debitAccount(String accountNumber, DebitDto debitDto) throws InsufficientBalanceException {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account != null) {
            if (account.getBalance() >= debitDto.getAmount()) {
                account.setBalance(account.getBalance() - debitDto.getAmount());

                WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
                withdrawalTransaction.setType("Withdrawal");
                withdrawalTransaction.setDate(new Date());
                withdrawalTransaction.setAmount(debitDto.getAmount());
                withdrawalTransaction.setWithdrawnBy("Some Source"); // You may set this based on your needs

                account.getTransactions().add(withdrawalTransaction);
                withdrawalTransaction.setAccount(account);

                accountRepository.save(account);
                transactionRepository.save(withdrawalTransaction);

                return createTransactionStatus("OK");
            } else {
                throw new InsufficientBalanceException("Insufficient balance for debit operation");
            }
        } else {
            throw new RuntimeException("Bank account not found");
        }
    }

    private TransactionStatusDto createTransactionStatus(String status) {
        return new TransactionStatusDto(status, generateApprovalCode());
    }


    public AccountDto getBankAccountDetails(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account != null) {
            AccountDto response = new AccountDto();
            response.setAccountNumber(account.getAccountNumber());
            response.setOwner(account.getOwner());
            response.setBalance(account.getBalance());
            response.setCreateDate(account.getCreateDate());

            List<Transaction> transactions = transactionRepository.findByBankAccount(account);
            List<TransactionDto> transactionResponses = transactions.stream()
                    .map(this::convertToTransactionResponse)
                    .collect(Collectors.toList());

            response.setTransactions(transactionResponses);

            return response;
        } else {
            throw new RuntimeException("Bank account not found");
        }
    }
    private TransactionDto convertToTransactionResponse(Transaction transaction) {
        TransactionDto transactionResponse = new TransactionDto();
    //    transactionResponse.setDate(transaction.getDate());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setType(transaction.getClass().getSimpleName());
        transactionResponse.setApprovalCode(transaction.getApprovalCode());

        return transactionResponse;
    }

    private String generateApprovalCode() {
        return UUID.randomUUID().toString();
    }


}
