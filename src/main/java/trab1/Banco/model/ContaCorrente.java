package trab1.Banco.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contacorrente")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContaCorrente extends Conta {
	
	
	@Column(name = "limiteCredito", precision=7, scale=2, nullable = true)
	private BigDecimal limiteCredito;
}
