package trab1.Banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import jakarta.persistence.PersistenceException;
import trab1.Banco.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, String> {

	@Procedure(name = "Conta.sp_insertClienteConta") 
	public String sp_insertClienteConta(String nome, String cpf,String senha, String tipo, String agencia);
	
	@Procedure(name = "Conta.sp_inserirClienteContaConj")
	public String sp_inserirClienteContaConj(String codigo, String cpf, String cpf2);
}
