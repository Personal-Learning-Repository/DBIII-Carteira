package br.edu.unisep.carteira.repository;

import br.edu.unisep.carteira.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
}
