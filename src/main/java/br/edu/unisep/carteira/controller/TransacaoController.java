package br.edu.unisep.carteira.controller;

import br.edu.unisep.carteira.publisher.DepositoEventPublisher;
import br.edu.unisep.carteira.publisher.SaqueEventPublisher;
import br.edu.unisep.carteira.exception.ResourceNotFoundException;
import br.edu.unisep.carteira.model.Transacao;
import br.edu.unisep.carteira.model.Usuario;
import br.edu.unisep.carteira.repository.TransacaoRepository;
import br.edu.unisep.carteira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransacaoController {

    //TODO Atualizado em
    //TODO pegar usuario da sessão

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DepositoEventPublisher depositoEventPublisher;

    @Autowired
    private SaqueEventPublisher saqueEventPublisher;

    //TODO Get by data / intervalo
    //TODO Apenas transações do usuário
    //TODO Código repetido
    //TODO DTOs ?
    //TODO Tratamento de erro autenticação
    @GetMapping("/transacoes")
    public List<Transacao> getAllTransacoes()
        throws ResourceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername());

            return transacaoRepository.findByUsuario(usuario.getId());
        } else {
            throw new ResourceNotFoundException("Usuário não autenticado");
        }
    }

    @PostMapping("/transacoes/deposito")
    public Transacao deposito(@RequestBody Transacao transacao)
        throws ResourceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername());

        transacao.setUsuario(usuario);
        transacao.setData(new Date());

        if (transacao.getDescricao() == null) {
            transacao.setDescricao("Depósito de R$" + transacao.getValor() + " para " + usuario.getNome());
        }

        transacao.setUsuario(usuario);

        Transacao newTransacao = transacaoRepository.save(transacao);

        //TODO Evento não vai se transação falhar
        depositoEventPublisher.publishEvent(newTransacao);

        return newTransacao;
    }

    @PostMapping("/transacoes/saque")
    public Transacao saque(@RequestBody Transacao transacao)
        throws ResourceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername());

        transacao.setUsuario(usuario);
        transacao.setData(new Date());

        if (transacao.getDescricao() == null) {
            transacao.setDescricao("Saque de R$" + transacao.getValor() + " para " + usuario.getNome());
        }

        transacao.setUsuario(usuario);

        if (usuario.getCarteira().getSaldo() - transacao.getValor() < 0) {
            throw new ResourceNotFoundException("Saldo insuficiente");
        }

        Transacao newTransacao = transacaoRepository.save(transacao);

        //TODO Evento não vai se transação falhar
        saqueEventPublisher.publishEvent(newTransacao);

        return newTransacao;
    }

    @PostMapping("/transacoes/transferencia")
    public Transacao transferencia(@RequestBody Transacao transacao)
        throws ResourceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Usuario remetente = usuarioRepository.findByEmail(userDetails.getUsername());

        Usuario destinatario = usuarioRepository.findById(transacao.getUsuario().getId()).orElseThrow(() ->
            new ResourceNotFoundException("Usuário não encontrado :: " + transacao.getUsuario().getId()));

        transacao.setData(new Date());

        if (transacao.getDescricao() == null) {
            transacao.setDescricao("Transferência de R$" +
                                    transacao.getValor() + " para " +
                                    destinatario.getNome() + " de " +
                                    remetente.getNome());
        }

        transacao.setUsuario(destinatario);

        Transacao newTransacao = transacaoRepository.save(transacao);
        depositoEventPublisher.publishEvent(newTransacao);

        Transacao desTransacao = new Transacao();

        desTransacao.setValor(transacao.getValor());
        desTransacao.setUsuario(remetente);
        desTransacao.setData(new Date());
        desTransacao.setDescricao(transacao.getDescricao());

        //TODO Evento não vai se transação falhar
        saqueEventPublisher.publishEvent(desTransacao);

        return newTransacao;
    }
}
