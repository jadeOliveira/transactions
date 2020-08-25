package br.com.pismo.transactions.business.service;

import br.com.pismo.transactions.business.dto.TransactionDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.entity.Transaction;
import br.com.pismo.transactions.business.enumeration.OperationType;
import br.com.pismo.transactions.business.exception.AccountNotFoundException;
import br.com.pismo.transactions.business.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  @NonNull
  private final TransactionRepository transactionRepository;

  @NonNull
  private final AccountService accountService;

  public TransactionDTO save(TransactionDTO transactionDTO) {
    return entityToDTO(transactionRepository.save(dtoToEntity(transactionDTO)));
  }

  private Transaction dtoToEntity(final TransactionDTO transactionDTO) {

    Account account = accountService.findOptionalById(transactionDTO.getAccountId())
        .orElseThrow(() -> new AccountNotFoundException());
    OperationType operationType = OperationType.findById(transactionDTO.getOperationTypeId());

    Transaction transaction = Transaction
        .builder()
        .account(account)
        .operationType(operationType)
        .eventDate(LocalDateTime.now())
        .amount(calculateAmount(operationType, transactionDTO.getAmount()))
        .build();

    return transaction;

  }

  private TransactionDTO entityToDTO(final Transaction transaction) {
    return TransactionDTO.builder()
        .id(transaction.getId())
        .accountId(transaction.getAccount().getId())
        .operationTypeId(transaction.getOperationType().getId())
        .amount(transaction.getAmount())
        .eventDate(transaction.getEventDate())
        .build();
  }

  private final BigDecimal calculateAmount(OperationType operationType, BigDecimal amount) {
    return operationType.isNegative() ? amount.negate() : amount.abs();
  }

}
