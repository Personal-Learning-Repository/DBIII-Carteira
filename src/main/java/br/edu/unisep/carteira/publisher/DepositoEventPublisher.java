package br.edu.unisep.carteira.publisher;

import br.edu.unisep.carteira.event.DepositoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.edu.unisep.carteira.model.Transacao;

@Component
public class DepositoEventPublisher {
    
    @Autowired
    private final ApplicationEventPublisher eventPublisher;

    public DepositoEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(Transacao transacao) {
        DepositoEvent depositoEvent = new DepositoEvent(transacao, transacao);
        eventPublisher.publishEvent(depositoEvent);
    }
}
