//package br.edu.unisep.carteira.listener;
//
//import br.edu.unisep.carteira.publisher.SaqueEventPublisher;
//import br.edu.unisep.carteira.event.TransferenciaEvent;
//import br.edu.unisep.carteira.model.Transacao;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.event.TransactionPhase;
//import org.springframework.transaction.event.TransactionalEventListener;
//
//@Component
//public class TransferenciaEventListener implements ApplicationListener<TransferenciaEvent> {
//
//    private final SaqueEventPublisher saqueEventPublisher;
//
//    public TransferenciaEventListener(SaqueEventPublisher saqueEventPublisher) {
//        this.saqueEventPublisher = saqueEventPublisher;
//    }
//
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void onApplicationEvent(TransferenciaEvent event) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Transacao remetente = event.getTransacao();
//        Transacao destinatario = new Transacao();
//
//        destinatario.setValor(remetente.getValor());
//        destinatario.setUsuario(remetente.getUsuario());
//        destinatario.setData(remetente.getData());
//
//        String descricao = "Transferência de R$" +
//                            remetente.getValor() + " para " +
//                            destinatario.getUsuario().getNome() + " de " +
//                            remetente.getUsuario().getNome();
//
//
//        if (remetente.getDescricao() == null) {
//            remetente.setDescricao(descricao);
//        } else {
//            remetente.setDescricao(remetente.getDescricao() + " - " + descricao);
//        }
//
//        destinatario.setValor(remetente.getValor());
//
//        saqueEventPublisher.publishEvent(destinatario);
//    }
//}
