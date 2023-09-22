package br.edu.unisep.carteira.controller;

import br.edu.unisep.carteira.model.JwtRequest;
import br.edu.unisep.carteira.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisep.carteira.config.JwtTokenUtil;
import br.edu.unisep.carteira.model.Usuario;
import br.edu.unisep.carteira.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private void authenticate(String email, String senha) throws Exception {
        try {
            authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(email, senha));
        } catch (DisabledException e) {
            throw new Exception("USUÁRIO_INATIVO", e);
        } catch (BadCredentialsException e) {
            throw new Exception("CREDENCIAIS_INVALIDAS", e);
        } catch (Exception e) {
            throw new Exception("ERRO_DE_AUTENTICAÇÃO", e);
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request)
            throws Exception {
        authenticate(request.getEmail(), request.getSenha());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Usuario usuario) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(usuario));
    }
}
