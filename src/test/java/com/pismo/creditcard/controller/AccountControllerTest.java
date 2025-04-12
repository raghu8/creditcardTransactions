package com.pismo.creditcard.controller;

import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private Account sampleAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleAccount = Account.builder()
                .accountId(1L)
                .documentNumber("123456789")
                .description("Test Account")
                .build();
    }

    @Test
    void testGetAccount() {
        when(accountService.getAccount(1L)).thenReturn(sampleAccount);

        ResponseEntity<Account> response = accountController.getAccount(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleAccount, response.getBody());
        verify(accountService, times(1)).getAccount(1L);
    }

    @Test
    void testCreateAccount() {
        doNothing().when(accountService).createAccount(sampleAccount);

        ResponseEntity<?> response = accountController.createAccount(sampleAccount);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("account creation complete", response.getBody());
        verify(accountService, times(1)).createAccount(sampleAccount);
    }
}