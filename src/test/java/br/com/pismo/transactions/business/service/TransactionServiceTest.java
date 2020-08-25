package br.com.pismo.transactions.business.service;

import br.com.pismo.transactions.business.dto.AccountDTO;
import br.com.pismo.transactions.business.dto.TransactionDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.entity.Transaction;
import br.com.pismo.transactions.business.enumeration.OperationType;
import br.com.pismo.transactions.business.exception.AccountNotFoundException;
import br.com.pismo.transactions.business.exception.InvalidDocumentNumber;
import br.com.pismo.transactions.business.exception.OperationTypeNotFoundException;
import br.com.pismo.transactions.business.repository.TransactionRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

  @InjectMocks
  private TransactionService transactionService;

  @Mock
  private AccountService accountService;

  @Mock
  private TransactionRepository transactionRepository;

  private static final Long ACCOUNT_ID = 1l;
  private static final String DOCUMENT_NUMBER = "12345678900";

  @Captor
  private ArgumentCaptor<Transaction> transactionArgumentCaptor;

  private TransactionDTO transactionDtoToSave;
  private Transaction transaction;
  private Account account;

  @BeforeEach
  public void init() {
    transactionDtoToSave =
        TransactionDTO
            .builder()
            .id(ACCOUNT_ID)
            .operationTypeId(OperationType.COMPRA_A_VISTA.getId())
            .accountId(ACCOUNT_ID)
            .amount(BigDecimal.ZERO)
            .build();

    account = Account.builder().id(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).build();

    transaction =
        Transaction
            .builder()
            .id(ACCOUNT_ID)
            .operationType(OperationType.COMPRA_A_VISTA)
            .account(account)
            .eventDate(LocalDateTime.now())
            .amount(BigDecimal.ZERO)
            .build();
  }

  @Test
  void save() {

    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

    TransactionDTO retTrDTO = transactionService.save(transactionDtoToSave);
    verify(transactionRepository).save(any(Transaction.class));

    assertEquals(retTrDTO.getAccountId(), transactionDtoToSave.getAccountId());
    assertEquals(retTrDTO.getOperationTypeId(),
        transactionDtoToSave.getOperationTypeId());
    assertNotNull(retTrDTO.getEventDate());
  }

  @Test
  void saveThrowsAccountNotFoundException() {
    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

  @Test
  void saveThrowsOperationTypeNotFoundException() {
    transactionDtoToSave.setOperationTypeId(5);
    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    Assertions.assertThrows(OperationTypeNotFoundException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

}