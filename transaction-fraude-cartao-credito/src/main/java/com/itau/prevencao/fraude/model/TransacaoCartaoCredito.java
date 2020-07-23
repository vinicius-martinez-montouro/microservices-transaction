package com.itau.prevencao.fraude.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransacaoCartaoCredito implements AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    private Cliente rementente;

    @ManyToOne(optional = false)
    private Cliente destinatario;

    private Double valor;

    private boolean fraude;
}
