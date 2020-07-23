package com.itau.prevencao.fraude.repository;

import com.itau.prevencao.fraude.model.TransacaoCartaoCredito;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vinicius.montouro
 */
@Repository
public interface FraudeCartaoCreditoRepository extends CrudRepository<TransacaoCartaoCredito,Long> {
}
