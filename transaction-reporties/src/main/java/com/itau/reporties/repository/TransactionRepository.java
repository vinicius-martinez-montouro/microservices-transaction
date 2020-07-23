package com.itau.reporties.repository;

import com.itau.reporties.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vinicius.montouro
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
}
