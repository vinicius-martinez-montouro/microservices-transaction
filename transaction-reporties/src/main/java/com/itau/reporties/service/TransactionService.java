package com.itau.reporties.service;

import com.itau.reporties.model.Transaction;
import com.itau.reporties.repository.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vinicius.montouro
 */
@Log4j2
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> findAll(){
        log.info("Buscando na data base todas as transações cartão de crédito");
        return transactionRepository.findAll();
    }
}
