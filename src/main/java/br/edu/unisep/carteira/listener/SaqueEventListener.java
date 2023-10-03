package br.edu.unisep.carteira.listener;

import br.edu.unisep.carteira.event.SaqueEvent;
import br.edu.unisep.carteira.helpers.GetUserByToken;
import br.edu.unisep.carteira.model.Carteira;
import br.edu.unisep.carteira.model.Transacao;
import br.edu.unisep.carteira.model.Usuario;
import br.edu.unisep.carteira.repository.CarteiraRepository;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

@Component
public class SaqueEventListener implements ApplicationListener<SaqueEvent> {

    private final CarteiraRepository carteiraRepository;

    private final GetUserByToken getUserByToken;

    public SaqueEventListener(CarteiraRepository carteiraRepository, GetUserByToken getUserByToken) {
        this.carteiraRepository = carteiraRepository;
        this.getUserByToken = getUserByToken;
    }

    //TODO Boa prática

    @SneakyThrows
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApplicationEvent(SaqueEvent event) {
        Transacao transacao = event.getTransacao();
        Carteira carteira = transacao.getUsuario().getCarteira();

        Usuario currentUsuario = getUserByToken.execute();

        carteira.setSaldo(carteira.getSaldo() - transacao.getValor());

        if (currentUsuario.getNome() != null) {
            carteira.setAtualizadoPor(currentUsuario.getNome());
        } else {
            carteira.setAtualizadoPor(currentUsuario.getEmail());
        }

        //TODO Date Util
        carteira.setAtualizadoEm(new Date());

        carteiraRepository.save(carteira);
    }
}
