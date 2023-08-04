package br.com.jademe.transactions.business.repository;

import br.com.jademe.transactions.business.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}