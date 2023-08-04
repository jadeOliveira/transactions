package br.com.jademe.transactions.business.repository;

import br.com.jademe.transactions.business.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
