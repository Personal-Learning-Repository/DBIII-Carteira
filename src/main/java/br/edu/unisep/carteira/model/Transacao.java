package br.edu.unisep.carteira.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "transacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "cod_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "descricao", nullable = false)
    private String descricao;
}
