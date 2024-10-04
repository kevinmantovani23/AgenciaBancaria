package trab1.Banco;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import trab1.Banco.model.Agencia;
import trab1.Banco.model.Conta;
import trab1.Banco.model.ContaCorrente;
import trab1.Banco.servico.IAgenciaRepository;
import trab1.Banco.servico.IClienteRepository;
import trab1.Banco.servico.IContaCorrenteRepository;
import trab1.Banco.servico.IContaPoupancaRepository;
import trab1.Banco.servico.IContaRepository;

@SpringBootTest
class BancoApplicationTests {

	@Autowired
	private IAgenciaRepository agcRep;
	
	@Autowired
	private IContaRepository contRep;
	
	@Autowired
	private IClienteRepository cliRep;
	
	@Autowired
	private IContaCorrenteRepository corrRep;
	
	@Autowired 
	private IContaPoupancaRepository poupRep;
	

	@Test
	void test() {
		Conta corr = contRep.findById("301111").get();
		System.out.println(corr.getDataAbertura());
		}
	
	@Test
	void testexcluiConta() {
		contRep.sp_excluiconta("92212675231");
	}
	
	@Test
	void testverifContaConj() {
		boolean resu = contRep.sp_verifcontaconj("52851481111");
		
		System.out.println(resu);
	}
	
	@Test 
	void testAtualizaPercentual() {
		poupRep.sp_updateContaPerPoupanca("300612", new BigDecimal(9.3));
	}
	
	@Test
	void testAtualizaCredito() {
		corrRep.sp_updateContaLimCredito("302311", new BigDecimal(123.32));
	}
	
	@Test
	void testAtualizarSaldo() {
		contRep.sp_updateContaSaldo("300612", new BigDecimal(-231.32));
	}
	
	@Test
	//transactional esta dando erro
	void testAtualizaSenha() {
		cliRep.sp_updateClienteSenha("52851481061", "joaob");
	}
	
	@Test
	void testInsertContaConj() {
		contRep.sp_inserirClienteContaConj("300612", "");
	}
	
	@Test
	@Transactional
	void testContaConj() {
		boolean resu = cliRep.sp_validarContaParaConj("300612");
		System.out.println(resu);
	}
	
	@Test
	@Transactional
	void verificaCadastro() {
		boolean resu = cliRep.sp_verificaLogin("52851481061", "joaoz123");
		
		System.out.println(resu);
	}
	
	@Test
	@Transactional
	void verificaCpf() {
		String resu = cliRep.sp_verificaCPF("aaaaaaaaaaa");
		System.out.println(resu);
	}
	
	
	@Test
	void verificaSenha() {
		String resu = cliRep.sp_verificaSenhaCliente("aaaaaaaa");
		
		System.out.println(resu);
	}
	
	@Test
	//@Transactional
	void criarConta() {
			
			contRep.sp_insertClienteConta("Jonas", "92212675231" , "joaoz123",  "corrente");
		
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
