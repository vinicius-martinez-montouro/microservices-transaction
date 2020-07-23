package com.itau.prevencao.fraude.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author vinicius.montouro
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente implements AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String telefone;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Conta conta;
}
