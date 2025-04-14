package com.pismo.creditcard.controller;

import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@Log4j2
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/details/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody Account account){
        log.info("Creating account...");
        accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("account creation complete");
    }

}