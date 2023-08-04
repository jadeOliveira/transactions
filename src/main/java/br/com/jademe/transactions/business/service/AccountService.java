package br.com.jademe.transactions.business.service;

import br.com.jademe.transactions.business.exception.CreditLimitInvalidException;
import br.com.jademe.transactions.business.exception.InvalidDocumentNumber;
import br.com.jademe.transactions.business.repository.AccountRepository;
import br.com.jademe.transactions.business.dto.AccountDTO;
import br.com.jademe.transactions.business.entity.Account;
import br.com.jademe.transactions.business.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  @NonNull
  private final AccountRepository accountRepository;

  public AccountDTO findById(Long id) {
    return findOptionalById(id).map(this::entityToDTO)
        .orElseThrow(AccountNotFoundException::new);
  }

  public AccountDTO save(AccountDTO accountDTO) {
    return entityToDTO(accountRepository.save(dtoToEntity(accountDTO)));
  }

  public void updateAvailableCreditLimit(Account account, BigDecimal availableCreditLimit) {
    account.setAvailableCreditLimit(availableCreditLimit);
    accountRepository.save(account);
  }

  public Optional<Account> findOptionalById(Long id) {
    return accountRepository.findById(id);
  }

  private Account dtoToEntity(final AccountDTO accountDTO) {

    if (StringUtils.isEmpty(accountDTO.getDocumentNumber()) || !StringUtils
        .isNumeric(accountDTO.getDocumentNumber())) {
      throw new InvalidDocumentNumber();
    }

    BigDecimal availableLimiteCredit = new BigDecimal(0);
    if (accountDTO.getId() != null) {
        availableLimiteCredit = Optional.ofNullable(accountDTO.getAvailableCreditLimit())
              .orElseThrow(
                      CreditLimitInvalidException::new);
    }

    return Account.builder().id(accountDTO.getId()).documentNumber(accountDTO.getDocumentNumber())
        .availableCreditLimit(availableLimiteCredit)
        .build();
  }

  private AccountDTO entityToDTO(final Account account) {
    return AccountDTO.builder().id(account.getId()).documentNumber(account.getDocumentNumber())
        .availableCreditLimit(account.getAvailableCreditLimit())
        .build();
  }

}