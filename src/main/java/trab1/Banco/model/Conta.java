package trab1.Banco.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "conta")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Conta {

	 @Id
	 @Column(name = "codigo", length = 30, nullable = true )
	 private String codigo;
	 
	 @ManyToOne(targetEntity = Cliente.class)
	 @JoinColumn(name = "cpfCliente1", nullable = true )
	 private Cliente cpfCliente1;
	 
	 @ManyToOne(targetEntity = Cliente.class)
	 @JoinColumn(name = "cpfCliente2", nullable = true )
	 private Cliente cpfCliente2;
	 
	 @ManyToOne(targetEntity = Agencia.class)
	 @JoinColumn(name = "codigoAgencia", nullable = true )
	 private Agencia codigoAgencia;
	 
	 @Column(name = "dataAbertura", nullable = true )
	 private LocalDate dataAbertura;
	 
	 @Column(name = "saldo", precision=10, scale=2, columnDefinition = "DECIMAL(10,2)", nullable = true)
	 
	 private BigDecimal saldo;
}
