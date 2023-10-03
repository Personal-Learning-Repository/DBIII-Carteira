package br.edu.unisep.carteira.dto;

import br.edu.unisep.carteira.model.Carteira;
import lombok.Data;

import java.util.Date;

@Data
public class UsuarioDTO {

    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private Carteira carteira;
    private Date criadoEm;
    private String criadoPor;
    private Date atualizadoEm;
    private String atualizadoPor;

}
