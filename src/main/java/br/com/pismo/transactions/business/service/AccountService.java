package br.com.pismo.transactions.business.service;

import br.com.pismo.transactions.business.dto.AccountDTO;
import br.com.pismo.transactions.business.entity.Account;
import br.com.pismo.transactions.business.exception.AccountNotFoundException;
import br.com.pismo.transactions.business.exception.InvalidDocumentNumber;
import br.com.pismo.transactions.business.repository.AccountRepository;
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

  public Optional<Account> findOptionalById(Long id) {
    return accountRepository.findById(id);
  }

  private Account dtoToEntity(final AccountDTO accountDTO) {
    if (StringUtils.isEmpty(accountDTO.getDocumentNumber()) || !StringUtils
        .isNumeric(accountDTO.getDocumentNumber())) {
      throw new InvalidDocumentNumber();
    }
    return Account.builder().id(accountDTO.getId()).documentNumber(accountDTO.getDocumentNumber())
        .build();
  }

  private AccountDTO entityToDTO(final Account account) {
    return AccountDTO.builder().id(account.getId()).documentNumber(account.getDocumentNumber())
        .build();
  }

}