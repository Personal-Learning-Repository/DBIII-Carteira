package br.edu.unisep.carteira.listener;

import br.edu.unisep.carteira.event.DepositoEvent;
import br.edu.unisep.carteira.model.Carteira;
import br.edu.unisep.carteira.model.Transacao;
import br.edu.unisep.carteira.repository.CarteiraRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DepositoEventListener implements ApplicationListener<DepositoEvent> {

    private final CarteiraRepository carteiraRepository;

    public DepositoEventListener(CarteiraRepository carteiraRepository) {
        this.carteiraRepository = carteiraRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApplicationEvent(DepositoEvent event) {
        Transacao transacao = event.getTransacao();
        Carteira carteira = transacao.getUsuario().getCarteira();

        carteira.setSaldo(carteira.getSaldo() + transacao.getValor());

        carteiraRepository.save(carteira);
    }

}
