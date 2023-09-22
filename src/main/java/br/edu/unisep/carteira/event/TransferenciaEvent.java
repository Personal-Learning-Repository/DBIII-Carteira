package br.edu.unisep.carteira.event;

import br.edu.unisep.carteira.model.Transacao;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransferenciaEvent extends ApplicationEvent {

    private final Transacao transacao;

    public TransferenciaEvent(Object source, Transacao transacao) {
        super(source);
        this.transacao = transacao;
    }

}
