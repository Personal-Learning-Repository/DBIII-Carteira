package br.edu.unisep.carteira.event;

import br.edu.unisep.carteira.model.Transacao;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SaqueEvent extends ApplicationEvent {

    private final Transacao transacao;

    public SaqueEvent(Object source, Transacao transacao) {
        super(source);
        this.transacao = transacao;
    }

}
