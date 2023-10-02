package br.edu.unisep.carteira.helpers;

import br.edu.unisep.carteira.exception.ResourceNotFoundException;
import br.edu.unisep.carteira.model.Usuario;
import br.edu.unisep.carteira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetUserByToken {

    //TODO Conferir boa pratica
    //TODO Melhorar nome

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario getUserByToken() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername());

        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não autenticado");
        }

        return usuario;
    }
}
