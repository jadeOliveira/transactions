package br.com.pismo.transactions.business.service;

import br.com.pismo.transactions.business.repository.TransactionRepository;
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

}
