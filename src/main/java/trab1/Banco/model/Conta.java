package trab1.Banco.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "conta")
@Inheritance(strategy = InheritanceType.JOINED)
public class Conta {


	 @Id
	 @Column(name = "codigo", length = 30, nullable = true )
	 protected String codigo;
	 
	 @ManyToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
	 @JoinColumn(name = "cpfCliente1", nullable = true )
	 protected Cliente cpfCliente1;
	 
	 @ManyToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
	 @JoinColumn(name = "cpfCliente2", nullable = true )
	 protected Cliente cpfCliente2;
	 
	 @ManyToOne(fetch = FetchType.LAZY, targetEntity = Agencia.class)
	 @JoinColumn(name = "codigoAgencia", nullable = true )
	 protected Agencia codigoAgencia;
	 
	 @Column(name = "dataAbertura", nullable = true )
	 protected LocalDate dataAbertura;
	 
	 @Column(name = "saldo", precision=10, scale=2, columnDefinition = "DECIMAL(10,2)", nullable = true)
	 
	 protected BigDecimal saldo;
}
