package trab1.Banco.servico;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import trab1.Banco.model.ContaCorrente;

public interface IContaCorrenteRepository extends JpaRepository<ContaCorrente, String>{

	@Procedure(name = "ContaCorrente.sp_updateContaLimCredito") 
	public void sp_updateContaLimCredito(@Param("codigo") String codigo, @Param("limite") BigDecimal limite);
	
	@Query(value = "SELECT cont.codigo, cont.cpfCliente1, cont.cpfCliente2, cont.codigoAgencia, cont.codigo, cont.dataAbertura, cont.saldo, corr.limiteCredito "
			+ "FROM conta cont, contacorrente corr WHERE cont.codigo = corr.codigo AND cont.codigo = ?", nativeQuery = true)
	public ContaCorrente findByCodigo(String codigo);
}
