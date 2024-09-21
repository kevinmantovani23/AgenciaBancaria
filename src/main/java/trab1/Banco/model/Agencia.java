package trab1.Banco.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "agencia")
@Data
public class Agencia {

	@Id
	@Column(name = "codigo", length = 20, nullable = true )
	private String codigo;
	
	@Column(name = "nome", length = 100, nullable = true )
	private String nome;
	
	@Column(name = "cep", length = 10, nullable = true )
	private String cep;
	
	@Column(name = "cidade", length = 30, nullable = true )
	private String cidade;
	
}
