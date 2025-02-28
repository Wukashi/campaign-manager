package com.futurumtechnology.campaign_manager;

import com.futurumtechnology.campaign_manager.entities.Account;
import com.futurumtechnology.campaign_manager.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository) {
        return args -> {
            if (accountRepository.findById(1L).isEmpty()) {
                Account defaultAccount = new Account();
                defaultAccount.setId(1L);
                defaultAccount.setBalance(new BigDecimal("1000.00"));
                accountRepository.save(defaultAccount);
            }
        };
    }
}
