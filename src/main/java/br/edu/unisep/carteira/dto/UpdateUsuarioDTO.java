package br.edu.unisep.carteira.dto;

import br.edu.unisep.carteira.model.Carteira;
import lombok.Data;

@Data
public class UpdateUsuarioDTO {

    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private Carteira carteira;
    private String atualizadoPor;

}
