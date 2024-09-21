package trab1.Banco.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contapoupanca")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContaPoupanca extends Conta {

	
	@Column(name = "percentualRendimento", precision=3 , scale=2 , nullable = true)
	private BigDecimal percentualRendimento;
	
	@Column(name = "diaAniversario", nullable = true )
	private int diaAniversario;
}
