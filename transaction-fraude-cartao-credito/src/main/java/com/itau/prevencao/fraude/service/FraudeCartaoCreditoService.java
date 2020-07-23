package com.itau.prevencao.fraude.service;

import com.itau.prevencao.fraude.model.TransacaoCartaoCredito;
import com.itau.prevencao.fraude.repository.FraudeCartaoCreditoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author vinicius.montouro
 */
@Log4j2
@Service
public class FraudeCartaoCreditoService {

    @Autowired
    private FraudeCartaoCreditoRepository fraudeCartaoCreditoRepository;

    @Transactional
    public TransacaoCartaoCredito saveTransacaoCartaoCredito(TransacaoCartaoCredito transacaoCartaoCredito){
        log.info("Salvando na data base transação cartão de crédito.");
        transacaoCartaoCredito.setFraude(isFraude(transacaoCartaoCredito));
        return fraudeCartaoCreditoRepository.save(transacaoCartaoCredito);
    }

    public Iterable<TransacaoCartaoCredito> findAll(){
        log.info("Buscando na data base todas as transações cartão de crédito");
        return fraudeCartaoCreditoRepository.findAll();
    }

    /**
     * realiza regras e modelos estatísticos e
     * verifica se pode ou não ser uma transação fraudulenta
     */
    private boolean isFraude(TransacaoCartaoCredito transacaoCartaoCredito){
        log.info("Calculando se transação é fraudulenta. (Regra de negócio).");
        return true;
    }

}
