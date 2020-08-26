package br.com.pismo.transactions.business.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.pismo.transactions.business.dto.TransactionDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.entity.Transaction;
import br.com.pismo.transactions.business.enumeration.OperationType;
import br.com.pismo.transactions.business.exception.AccountNotFoundException;
import br.com.pismo.transactions.business.exception.OperationTypeNotFoundException;
import br.com.pismo.transactions.business.exception.TransactionAmountInvalidException;
import br.com.pismo.transactions.business.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
  private Transaction fakeSavedTransaction;
  private Account account;

  @BeforeEach
  public void init() {
    account = Account.builder().id(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).build();

    transactionDtoToSave =
        TransactionDTO
            .builder()
            .id(ACCOUNT_ID)
            .operationTypeId(OperationType.COMPRA_A_VISTA.getId())
            .accountId(ACCOUNT_ID)
            .amount(BigDecimal.TEN)
            .build();

    fakeSavedTransaction =
        Transaction
            .builder()
            .id(ACCOUNT_ID)
            .operationType(OperationType.COMPRA_A_VISTA)
            .account(account)
            .eventDate(LocalDateTime.now())
            .amount(BigDecimal.TEN)
            .build();
  }

  @Test
  void saveAndValidateAmountWithNegativeValue() {

    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(fakeSavedTransaction);

    TransactionDTO returnedDTO = transactionService.save(transactionDtoToSave);
    verify(transactionRepository).save(transactionArgumentCaptor.capture());

    assertEquals(returnedDTO.getAccountId(), transactionDtoToSave.getAccountId());
    assertEquals(returnedDTO.getOperationTypeId(),
        transactionDtoToSave.getOperationTypeId());
    assertNotNull(returnedDTO.getEventDate());
    assertTrue(transactionArgumentCaptor.getValue().getAmount().signum() == -1);
  }

  @Test
  void saveAndValidateAmountWithPositiveValue() {

    transactionDtoToSave.setOperationTypeId(OperationType.PAGAMENTO.getId());

    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(fakeSavedTransaction);

    TransactionDTO returnedDTO = transactionService.save(transactionDtoToSave);
    verify(transactionRepository).save(transactionArgumentCaptor.capture());

    assertTrue(transactionArgumentCaptor.getValue().getAmount().signum() == 1);
  }

  @Test
  void saveThrowsAccountNotFoundExceptionWhenAccountNotFound() {
    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

  @Test
  void saveThrowsAccountNotFoundExceptionWhenAccountIdNull() {
    transactionDtoToSave.setAccountId(null);
    Assertions.assertThrows(AccountNotFoundException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

  @Test
  void saveThrowsOperationTypeNotFoundExceptionWhenOperationTypeInvalid() {
    transactionDtoToSave.setOperationTypeId(5);
    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    Assertions.assertThrows(OperationTypeNotFoundException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

  @Test
  void saveThrowsOperationTypeNotFoundExceptionWhenOperationTypeNull() {
    transactionDtoToSave.setOperationTypeId(null);
    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    Assertions.assertThrows(OperationTypeNotFoundException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

  @Test
  void saveThrowsTransactionAmountInvalidException() {
    transactionDtoToSave.setAmount(null);
    when(accountService.findOptionalById(ACCOUNT_ID)).thenReturn(Optional.of(account));
    Assertions.assertThrows(TransactionAmountInvalidException.class, () -> {
      transactionService.save(transactionDtoToSave);
    });
  }

}