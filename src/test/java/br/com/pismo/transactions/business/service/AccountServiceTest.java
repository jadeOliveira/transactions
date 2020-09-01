package br.com.pismo.transactions.business.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.pismo.transactions.business.dto.AccountDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.exception.AccountNotFoundException;
import br.com.pismo.transactions.business.exception.CreditLimitInvalidException;
import br.com.pismo.transactions.business.exception.InvalidDocumentNumber;
import br.com.pismo.transactions.business.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

  @InjectMocks
  private AccountService accountService;

  @Mock
  private AccountRepository accountRepository;

  private Account account;
  private static final Long ACCOUNT_ID = 1l;
  private static final String DOCUMENT_NUMBER = "12345678900";

  @Captor
  private ArgumentCaptor<Account> accountArgumentCaptor;

  @BeforeEach
  public void init() {
    account = Account.builder().id(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).availableCreditLimit(BigDecimal.TEN).build();
  }

  @Test
  void findById() {
    when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    AccountDTO accountDTO = accountService.findById(account.getId());
    assertEquals(accountDTO.getId(), account.getId());
    assertEquals(accountDTO.getDocumentNumber(), account.getDocumentNumber());
  }

  @Test
  void findByIdReturnThrowsAccountNotFoundException() {
    when(accountRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () -> {
      accountService.findById(account.getId());
    });
  }

  @Test
  void save() {
    AccountDTO accountDtoToSave = AccountDTO.builder().documentNumber(DOCUMENT_NUMBER)
        .availableCreditLimit(
            BigDecimal.TEN).build();
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    AccountDTO newAccountDTO = accountService.save(accountDtoToSave);

    verify(accountRepository).save(accountArgumentCaptor.capture());
    assertEquals(newAccountDTO.getDocumentNumber(), accountDtoToSave.getDocumentNumber());
    assertEquals(accountDtoToSave.getDocumentNumber(),
        accountArgumentCaptor.getValue().getDocumentNumber());
    assertNotNull(newAccountDTO.getId());
  }

  @Test
  void saveThrowsInvalidDocumentNumber() {
    AccountDTO accountDtoToSave = AccountDTO.builder().build();

    Assertions.assertThrows(InvalidDocumentNumber.class, () -> {
      accountService.save(accountDtoToSave);
    });
  }

  @Test
  void saveThrowsCreditLimitInvalidException() {
    AccountDTO accountDtoToSave = AccountDTO.builder().documentNumber(DOCUMENT_NUMBER).build();

    Assertions.assertThrows(CreditLimitInvalidException.class, () -> {
      accountService.save(accountDtoToSave);
    });
  }

  @Test
  void updateAvailableCreditLimitCallSave(){

    accountService.updateAvailableCreditLimit(account, BigDecimal.ONE);
    verify(accountRepository).save(any(Account.class));

  }

}