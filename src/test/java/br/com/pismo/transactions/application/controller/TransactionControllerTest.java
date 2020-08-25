package br.com.pismo.transactions.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.pismo.transactions.business.dto.AccountDTO;
import br.com.pismo.transactions.business.dto.TransactionDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.enumeration.OperationType;
import br.com.pismo.transactions.business.service.AccountService;
import br.com.pismo.transactions.business.service.TransactionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

  @InjectMocks
  private TransactionController transactionController;

  @Mock
  private TransactionService transactionService;

  @Captor
  private ArgumentCaptor<TransactionDTO> transactionDTOArgumentCaptor;

  private TransactionDTO transactionDto;
  private Account account;

  private static final Long ACCOUNT_ID = 1l;
  private static final String DOCUMENT_NUMBER = "12345678900";

  @BeforeEach
  public void init() {
    account = Account.builder().id(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).build();

    transactionDto =
        TransactionDTO
            .builder()
            .id(ACCOUNT_ID)
            .operationTypeId(OperationType.COMPRA_A_VISTA.getId())
            .accountId(ACCOUNT_ID)
            .amount(BigDecimal.ZERO)
            .build();
  }

  @Test
  void save() {
    when(transactionService.save(any(TransactionDTO.class))).thenReturn(transactionDto);

    TransactionDTO localTransactionDto = transactionController.save(transactionDto);

    verify(transactionService).save(transactionDTOArgumentCaptor.capture());
    assertEquals(localTransactionDto.getAccountId(),
        transactionDTOArgumentCaptor.getValue().getAccountId());

  }

}