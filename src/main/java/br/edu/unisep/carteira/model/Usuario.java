package br.edu.unisep.carteira.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //TODO Min length
    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "nome", length = 75)
    private String nome;

    @Column(name = "email", nullable = false, length = 75, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 75)
    private String senha;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cod_carteira")
    private Carteira carteira;

    @Column(name = "criado_em", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date criadoEm;

    @Column(name = "criado_por", nullable = false)
    @CreatedBy
    private String criadoPor;

    @Column(name = "atualizado_em")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date atualizadoEm;

    @Column(name = "atualizado_por")
    @LastModifiedBy
    private String atualizadoPor;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();
}
