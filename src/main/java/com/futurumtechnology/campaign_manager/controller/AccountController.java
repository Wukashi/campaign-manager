package com.futurumtechnology.campaign_manager.controller;

import com.futurumtechnology.campaign_manager.entities.Account;
import com.futurumtechnology.campaign_manager.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Account> getAccount() {
        return ResponseEntity.ok(accountService.getAccount());
    }
}
