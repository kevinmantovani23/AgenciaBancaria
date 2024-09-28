package trab1.Banco.servico;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import trab1.Banco.model.ContaPoupanca;

public interface IContaPoupancaRepository extends JpaRepository<ContaPoupanca, String>{

	@Procedure(name = "ContaPoupanca.sp_updateContaPerPoupanca") 
	public void sp_updateContaPerPoupanca(@Param("codigo") String codigo, @Param("percentual") BigDecimal percentual);
}
