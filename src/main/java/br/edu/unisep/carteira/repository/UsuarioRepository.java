package br.edu.unisep.carteira.repository;

import br.edu.unisep.carteira.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select * from usuario where email = :email", nativeQuery = true)
    Usuario findByEmail(@Param("email") String email);
}
