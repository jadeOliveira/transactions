package br.com.pismo.transactions.application.controller;

import br.com.pismo.transactions.business.dto.AccountDTO;
import br.com.pismo.transactions.business.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountControllerTest {

  @InjectMocks
  private AccountController accountController;

  @Mock
  private AccountService accountService;

  @Captor
  private ArgumentCaptor<AccountDTO> accountDTOArgumentCaptor;

  private AccountDTO accountDTO;
  private static final Long ACCOUNT_ID = 1l;
  private static final String DOCUMENT_NUMBER = "12345678900";

  @BeforeEach
  public void init() {
    accountDTO = AccountDTO.builder().id(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).build();
  }

  @Test
  void find() {
    when(accountService.findById(ACCOUNT_ID)).thenReturn(accountDTO);

    AccountDTO localAccountDTO = accountController.find(ACCOUNT_ID);
    assertEquals(localAccountDTO.getId(), ACCOUNT_ID);
  }

  @Test
  void save() {
    when(accountService.save(any(AccountDTO.class))).thenReturn(accountDTO);

    AccountDTO localAccountDTO = accountController.save(accountDTO);

    verify(accountService).save(accountDTOArgumentCaptor.capture());
    assertEquals(localAccountDTO.getDocumentNumber(),
        accountDTOArgumentCaptor.getValue().getDocumentNumber());
  }

}