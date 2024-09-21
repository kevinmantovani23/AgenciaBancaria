package trab1.Banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trab1.Banco.model.Agencia;

public interface AgenciaRepository extends JpaRepository<Agencia, String> {
	
}
