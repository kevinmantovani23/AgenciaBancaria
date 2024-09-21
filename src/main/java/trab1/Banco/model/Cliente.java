package trab1.Banco.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "cliente")
public class Cliente {
    
	
	
	@Id
	@Column(name = "cpf", length = 14, nullable = true )
	private String cpf;
	
	@Column(name = "nome", length = 100, nullable = true )
	private String nome;
	
	@Column(name = "dataPrimeiraConta", nullable = true )
	private LocalDate dataPrimeiraConta;
	
	@Column(name = "senha", length = 25, nullable = true )
	private String senha;
	
	
	
}    
     