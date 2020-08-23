package br.com.pismo.transactions.business.repository;

import br.com.pismo.transactions.business.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}