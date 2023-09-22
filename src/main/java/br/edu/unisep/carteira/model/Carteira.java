package br.edu.unisep.carteira.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carteira")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //TODO DB Default Value
    @Column(name = "saldo", nullable = false)
    private Double saldo = 0.0;

    @JsonIgnore
    @OneToOne(mappedBy = "carteira")
    private Usuario usuario;

    //TODO Atualizado em ?

}
