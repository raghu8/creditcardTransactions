package com.pismo.creditcard.service;

import static org.junit.jupiter.api.Assertions.*;

import com.pismo.creditcard.exception.DuplicateObjectException;
import com.pismo.creditcard.exception.InvalidObjectException;
import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.repository.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private AccountService accountService;

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
    void testGetAccount_Success() {
        when(accountRepo.getAccountById(1L)).thenReturn(sampleAccount);

        Account account = accountService.getAccount(1L);

        assertNotNull(account);
        assertEquals(1L, account.getAccountId());
        verify(accountRepo, times(1)).getAccountById(1L);
    }

    @Test
    void testGetAccount_NotFound() {
        when(accountRepo.getAccountById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccount(1L));
        verify(accountRepo, times(1)).getAccountById(1L);
    }

    @Test
    void testCreateAccount_Success() {
        when(accountRepo.getAccountByDocumentNumber("123456789")).thenReturn(null);
        doNothing().when(accountRepo).insertAccount(anyString(), anyString(), any(LocalDateTime.class));

        accountService.createAccount(sampleAccount);

        verify(accountRepo, times(1)).getAccountByDocumentNumber("123456789");
        verify(accountRepo, times(1)).insertAccount(anyString(), anyString(), any(LocalDateTime.class));
    }

    @Test
    void testCreateAccount_DuplicateDocumentNumber() {
        when(accountRepo.getAccountByDocumentNumber("123456789")).thenReturn(sampleAccount);

        assertThrows(DuplicateObjectException.class, () -> accountService.createAccount(sampleAccount));
        verify(accountRepo, times(1)).getAccountByDocumentNumber("123456789");
    }

    @Test
    void testVerifyDocumentNumber_Invalid() {
        Account invalidAccountDocumentNumber = Account.builder().documentNumber("12345a").build();
        assertThrows(InvalidObjectException.class, () -> accountService.verifyDocumentNumber(invalidAccountDocumentNumber));
    }

    @Test
    void testCreateAccount_NullDocumentNumber() {
        sampleAccount.setDocumentNumber(null);
        assertThrows(InvalidObjectException.class, () -> accountService.createAccount(sampleAccount));
        verify(accountRepo, never()).getAccountByDocumentNumber(anyString());
        verify(accountRepo, never()).insertAccount(anyString(), anyString(), any(LocalDateTime.class));
    }

    @Test
    void testCreateAccount_EmptyDocumentNumber() {
        sampleAccount.setDocumentNumber("");
        assertThrows(InvalidObjectException.class, () -> accountService.createAccount(sampleAccount));
        verify(accountRepo, never()).getAccountByDocumentNumber(anyString());
        verify(accountRepo, never()).insertAccount(anyString(), anyString(), any(LocalDateTime.class));
    }

}