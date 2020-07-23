package com.itau.prevencao.fraude.endpoint.controller;

import com.itau.prevencao.fraude.model.TransacaoCartaoCredito;
import com.itau.prevencao.fraude.service.FraudeCartaoCreditoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author vinicius.montouro
 */
@RestController
@RequestMapping("v1/fraude/cartao-credito")
@Log4j2
@Api(value = "Endpoints to 'Fraude Cartão de Crédito' ")
public class FraudeCartaoCreditoController {
    @Autowired
    private FraudeCartaoCreditoService fraudeCartaoCreditoService;

    @PostMapping
    @ApiOperation(value = "Save transaction to credit card", response = TransacaoCartaoCredito.class)
    ResponseEntity<TransacaoCartaoCredito> saveTransacaoCartaoCredito(@RequestBody TransacaoCartaoCredito transacaoCartaoCredito){
        log.info("Salvando transação cartão de crédito");
        return new ResponseEntity<>(fraudeCartaoCreditoService.saveTransacaoCartaoCredito(transacaoCartaoCredito),
                HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "List transaction to credit card", response = TransacaoCartaoCredito[].class)
    public ResponseEntity<Iterable<TransacaoCartaoCredito>> findAll(){
        log.info("Buscando transações cartão de crédito");
        return new ResponseEntity<Iterable<TransacaoCartaoCredito>>(fraudeCartaoCreditoService.findAll(), HttpStatus.OK);
    }

}
