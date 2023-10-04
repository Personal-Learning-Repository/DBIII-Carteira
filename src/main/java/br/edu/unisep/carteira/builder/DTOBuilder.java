package br.edu.unisep.carteira.builder;

import br.edu.unisep.carteira.dto.DisplayUsuarioDTO;
import br.edu.unisep.carteira.model.Usuario;

public class DTOBuilder {

    public DisplayUsuarioDTO build(Usuario usuario) {
        DisplayUsuarioDTO displayDTO = new DisplayUsuarioDTO();
        displayDTO.setCpf(usuario.getCpf());
        displayDTO.setNome(usuario.getNome());
        displayDTO.setEmail(usuario.getEmail());
        displayDTO.setSaldo(usuario.getCarteira().getSaldo());
        displayDTO.setCriadoEm(usuario.getCriadoEm());
        displayDTO.setCriadoPor(usuario.getCriadoPor());
        displayDTO.setAtualizadoEm(usuario.getAtualizadoEm());
        displayDTO.setAtualizadoPor(usuario.getAtualizadoPor());

        return displayDTO;
    }
}
