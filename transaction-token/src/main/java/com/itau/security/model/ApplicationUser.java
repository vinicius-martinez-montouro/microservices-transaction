package com.itau.security.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author vinicius.montouro
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationUser implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull(message = "O campo nome é obrigatório")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "O campo senha é obrigatório")
    @Column(nullable = false)
    @ToString.Exclude
    private String password;
    @NotNull(message = "O campo role é obrigatório")
    @Column(nullable = false)
    private String role = "USER";

    public ApplicationUser(@NotNull ApplicationUser applicationUser){
        this.id = applicationUser.getId();
        this.name = applicationUser.getName();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
    }
}
