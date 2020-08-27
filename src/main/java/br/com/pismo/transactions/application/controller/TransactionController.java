package br.com.pismo.transactions.application.controller;

import br.com.pismo.transactions.business.dto.TransactionDTO;
import br.com.pismo.transactions.business.service.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

  @NonNull
  private final TransactionService transactionService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public TransactionDTO save(@RequestBody final TransactionDTO transactionDTO) {
    return transactionService.save(transactionDTO);
  }
}
