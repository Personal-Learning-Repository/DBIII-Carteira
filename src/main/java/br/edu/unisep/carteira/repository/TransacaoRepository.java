package br.edu.unisep.carteira.repository;

import br.edu.unisep.carteira.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query(value = "select * from transacao where cod_usuario = :usuario", nativeQuery = true)
    List<Transacao> findByUsuario(Long usuario);
}
