//package br.edu.unisep.carteira.publisher;
//
//import br.edu.unisep.carteira.event.TransferenciaEvent;
//import br.edu.unisep.carteira.model.Transacao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransferenciaEventPublisher {
//
//    @Autowired
//    private final ApplicationEventPublisher eventPublisher;
//
//    public TransferenciaEventPublisher(ApplicationEventPublisher eventPublisher) {
//        this.eventPublisher = eventPublisher;
//    }
//
//    public void publishEvent(Transacao transacao) {
//        TransferenciaEvent transferenciaEvent = new TransferenciaEvent(transacao, transacao);
//        eventPublisher.publishEvent(transferenciaEvent);
//    }
//}
