package trab1.Banco.servico;

import org.springframework.data.jpa.repository.JpaRepository;

import trab1.Banco.model.Agencia;

public interface IAgenciaRepository extends JpaRepository<Agencia, String> {
	
}
