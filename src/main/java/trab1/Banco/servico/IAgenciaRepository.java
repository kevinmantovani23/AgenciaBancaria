package trab1.Banco.servico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import trab1.Banco.model.Agencia;

public interface IAgenciaRepository extends JpaRepository<Agencia, String> {
	@Query("select a from Agencia a where a.codigo like %:codigo%")
    List<Agencia> findByCodigo(@Param("codigo") String codigo);
}
