package br.edu.unisep.carteira.controller;

import br.edu.unisep.carteira.exception.ResourceNotFoundException;
import br.edu.unisep.carteira.model.Carteira;
import br.edu.unisep.carteira.model.Usuario;
import br.edu.unisep.carteira.repository.CarteiraRepository;
import br.edu.unisep.carteira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable(value = "id") Long usuarioId)
        throws ResourceNotFoundException {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() ->
                new ResourceNotFoundException("User not found for this id :: " + usuarioId));
        return ResponseEntity.ok().body(usuario);
    }

    //TODO Melhorar Output ?
    @GetMapping("/usuarios/saldo/{id}")
    public ResponseEntity<Double> getSaldo(@PathVariable(value = "id") Long usuarioId)
        throws ResourceNotFoundException {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() ->
                new ResourceNotFoundException("User not found for this id :: " + usuarioId));

            Double saldo = usuario.getCarteira().getSaldo();
            return ResponseEntity.ok().body(saldo);
    }

    @GetMapping("/usuarios/test")

    @PostMapping("/usuarios")
    @Transactional
    public Usuario createUser(@Validated @RequestBody Usuario usuario) {

        usuario.setCriadoEm(new Date());

        //TODO Usuário autenticado
        if (usuario.getNome() != null) {
            usuario.setCriadoPor(usuario.getNome());
        } else {
            usuario.setCriadoPor(usuario.getEmail());
        }

        Carteira carteira = new Carteira();

        usuario.setCarteira(carteira);

        carteiraRepository.save(carteira);

        return usuarioRepository.save(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable(value = "id") Long usuarioId,
                                              @Validated @RequestBody Usuario detalhes)
        throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + usuarioId));

        if (detalhes.getCpf() != null) {
            usuario.setCpf(detalhes.getCpf());
        }

        if (detalhes.getNome() != null) {
            usuario.setNome(detalhes.getNome());
        }

        if (detalhes.getEmail() != null) {
            usuario.setEmail(detalhes.getEmail());
        }

        if (detalhes.getSenha() != null) {
            usuario.setSenha(detalhes.getSenha());
        }

        //TODO Usuário autenticado
        if (detalhes.getNome() != null) {
            usuario.setAtualizadoPor(detalhes.getNome());
        } else {
            usuario.setAtualizadoPor(usuario.getEmail());
        }

        usuario.setAtualizadoEm(new Date());

        final Usuario updatedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public Map<String, Boolean> deleteUsuario(
            @PathVariable(value = "id") Long usuarioId
    ) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado ::" + usuarioId));
        usuarioRepository.delete(usuario);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
