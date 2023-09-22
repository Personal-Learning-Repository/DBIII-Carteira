package br.edu.unisep.carteira.event;

import br.edu.unisep.carteira.model.Transacao;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DepositoEvent extends ApplicationEvent {

    private final Transacao transacao;

    public DepositoEvent(Object source, Transacao transacao) {
        super(source);
        this.transacao = transacao;
    }

}
