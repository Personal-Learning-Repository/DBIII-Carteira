package br.edu.unisep.carteira.repository;

import br.edu.unisep.carteira.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
