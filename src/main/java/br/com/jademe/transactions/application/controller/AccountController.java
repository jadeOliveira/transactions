package br.com.jademe.transactions.application.controller;

import br.com.jademe.transactions.business.dto.AccountDTO;
import br.com.jademe.transactions.business.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  @NonNull
  private final AccountService accountService;

  @GetMapping(path = "/{accountId}")
  public AccountDTO find(@PathVariable Long accountId) {
    return accountService.findById(accountId);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public AccountDTO save(@RequestBody final AccountDTO accountDTO) {
    return accountService.save(accountDTO);
  }

}