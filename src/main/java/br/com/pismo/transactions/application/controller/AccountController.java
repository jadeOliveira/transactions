package br.com.pismo.transactions.application.controller;

import br.com.pismo.transactions.business.dto.AccountDTO;
import br.com.pismo.transactions.business.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

  @NonNull
  private final AccountService accountService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public AccountDTO find(@RequestParam(name = "accountId") final Long accountId) {
    return accountService.findById(accountId);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public AccountDTO save(@RequestBody final AccountDTO accountDTO) {
    return accountService.save(accountDTO);
  }

}