package trab1.Banco.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.PersistenceException;
import trab1.Banco.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, String> {

	@Procedure(name = "Conta.sp_insertClienteConta") 
	public String sp_insertClienteConta(@Param("nome")String nome,@Param("cpf") String cpf,@Param("senha")String senha, @Param("tipo")String tipo,@Param("agencia") String agencia);
	
	@Procedure(name = "Conta.sp_inserirClienteContaConj")
	public void sp_inserirClienteContaConj(@Param("codigo")String codigo, @Param("cpf2") String cpf2);
	
	@Procedure(name = "Conta.sp_updateContaSaldo")
	public void sp_updateContaSaldo(@Param("codigo") String codigo, @Param("saldo") BigDecimal saldo);
	
	@Procedure(name = "Conta.sp_verifcontaconj")
	public boolean sp_verifcontaconj(@Param("cpf") String cpf);
	
	@Procedure(name = "Conta.sp_excluiconta")
	public void sp_excluiconta(@Param("cpf") String cpf);
	
}
