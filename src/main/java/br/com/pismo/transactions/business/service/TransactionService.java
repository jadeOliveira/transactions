package br.com.pismo.transactions.business.service;

import br.com.pismo.transactions.business.dto.TransactionDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.entity.Transaction;
import br.com.pismo.transactions.business.enumeration.OperationType;
import br.com.pismo.transactions.business.exception.AccountNotFoundException;
import br.com.pismo.transactions.business.exception.AvailableCreditLimitException;
import br.com.pismo.transactions.business.exception.OperationTypeNotFoundException;
import br.com.pismo.transactions.business.exception.TransactionAmountInvalidException;
import br.com.pismo.transactions.business.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
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

    Long accountId = Optional.ofNullable(transactionDTO.getAccountId())
        .orElseThrow(AccountNotFoundException::new);
    Account account = accountService.findOptionalById(transactionDTO.getAccountId())
        .orElseThrow(AccountNotFoundException::new);

    int operationTypeId = Optional.ofNullable(transactionDTO.getOperationTypeId()).orElseThrow(
        OperationTypeNotFoundException::new);
    OperationType operationType = OperationType.findById(operationTypeId);

    BigDecimal amount = Optional.ofNullable(transactionDTO.getAmount())
        .map(o -> calculateAmount(operationType, transactionDTO.getAmount()))
        .orElseThrow(TransactionAmountInvalidException::new);

    BigDecimal newCreditLimit = calculateAvailableCreditLimit(account, amount);

    accountService.updateAvailableCreditLimit(account, newCreditLimit);

    Transaction transaction = Transaction
        .builder()
        .account(account)
        .operationType(operationType)
        .eventDate(LocalDateTime.now())
        .amount(amount)
        .build();

    return transaction;

  }

  public BigDecimal calculateAvailableCreditLimit(Account account, BigDecimal transactionAmount) {

    BigDecimal newAvailableCreditLimit = account.getAvailableCreditLimit().add(transactionAmount);
    if (newAvailableCreditLimit.compareTo(BigDecimal.ZERO) < 0) {
      throw new AvailableCreditLimitException();
    }

    return newAvailableCreditLimit;
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
