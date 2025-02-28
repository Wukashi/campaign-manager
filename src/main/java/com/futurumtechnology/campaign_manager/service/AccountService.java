package com.futurumtechnology.campaign_manager.service;

import com.futurumtechnology.campaign_manager.entities.Account;
import com.futurumtechnology.campaign_manager.repository.AccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void initializeAccount() {
        if (accountRepository.findById(1L).isEmpty()) {
            Account account = new Account(1L, BigDecimal.valueOf(1000));
            accountRepository.save(account);
        }
    }

    public Account getAccount() {
        return accountRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public void deductFunds(BigDecimal amount) {
        Account account = getAccount();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Not enough funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }
}
