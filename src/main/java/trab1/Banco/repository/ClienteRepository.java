package trab1.Banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import trab1.Banco.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{
	
	@Procedure(name = "Cliente.sp_validarContaParaConj") 
	public boolean sp_validarContaParaConj(@Param("codigoConta") String codigoConta);

	@Procedure(name = "Cliente.sp_verificaLogin") 
	public boolean sp_verificaLogin(@Param("cpf") String cpf, @Param("senha") String senha);
	
	@Procedure(name = "Cliente.sp_verificaSenhaCliente") 
	public String sp_verificaSenhaCliente(@Param("senha") String senha);
	
	@Procedure(name = "Cliente.sp_verificaCPF") 
	public String sp_verificaCPF(@Param("cpf") String cpf);
	
}
