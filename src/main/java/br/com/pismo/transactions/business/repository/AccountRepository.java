package br.com.pismo.transactions.business.repository;

import br.com.pismo.transactions.business.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
