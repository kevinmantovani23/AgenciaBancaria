package trab1.Banco;

import java.util.List;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import trab1.Banco.model.Agencia;
import trab1.Banco.repository.AgenciaRepository;
import trab1.Banco.repository.ClienteRepository;
import trab1.Banco.repository.ContaRepository;

@SpringBootTest
class BancoApplicationTests {

	@Autowired
	private AgenciaRepository agcRep;
	
	@Autowired
	private ContaRepository contRep;
	
	@Autowired
	private ClienteRepository cliRep;
	
	
	@Test
	@Transactional
	void verificaCpf() {
		String resu = cliRep.sp_verificaCPF("aaaaaaaaaaa");
		System.out.println(resu);
	}
	
	
	@Test
	@Transactional
	void verificaSenha() {
		String resu = cliRep.sp_verificaSenhaCliente("joazzzz4");
		
		System.out.println(resu);
	}
	
	@Test
	@Transactional
	void criarConta() {
			
			contRep.sp_insertClienteConta("Kevin", "64923495012" , "joaoz123",  "poupanca", "30");
		
			//System.out.println(saida);
	}
	
	
	
	@Test 
	void deleteAgencia(){
		
		agcRep.deleteById("33333");
		List<Agencia> lista = agcRep.findAll();
		for(Agencia agc : lista) {
			System.out.println(agc.toString());
	}
		}
	
	
	@Test 
	void crudAgencia(){
		Agencia ag = new Agencia();
		ag.setCodigo("30");
		ag.setNome("nubank");
		ag.setCep("023");
		ag.setCidade("sp");
		
		agcRep.save(ag);
		List<Agencia> lista = agcRep.findAll();
		for(Agencia agc : lista) {
			System.out.println(agc.toString());
	}
		
	}
	@Test
	void contextLoads() {
	}

}
