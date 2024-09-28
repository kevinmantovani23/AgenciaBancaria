package trab1.Banco.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import trab1.Banco.model.Cliente;
import trab1.Banco.model.Conta;
import trab1.Banco.model.ContaCorrente;
import trab1.Banco.servico.IClienteRepository;
import trab1.Banco.servico.IContaRepository;

@Controller
public class ContaController {
	
	@Autowired
	IClienteRepository clRep;
	
	@Autowired
	IContaRepository contRep;
	
	@GetMapping("/registrar")
	public ModelAndView registrarConta() {
		ModelAndView reg = new ModelAndView("/registro_form");
		reg.addObject("cliente", new Cliente());
		return reg;
	}
	
	@PostMapping("/registrar")
	public ModelAndView confirmarRegistro(@ModelAttribute("cliente") Cliente cliente, @RequestParam("tipo") String tipo) {
		if (clRep.sp_verificaCPF(cliente.getCpf()) != null) {
			String msg = clRep.sp_verificaCPF(cliente.getCpf());
			ModelAndView resp = new ModelAndView("redirect:registrar?error=true&mensagem=" + msg);
			
			return resp;
		} else if (clRep.sp_verificaSenhaCliente(cliente.getSenha()) != null){
			String msg = clRep.sp_verificaSenhaCliente(cliente.getCpf());
			ModelAndView resp = new ModelAndView("redirect:registrar?error=true&mensagem=" + msg);
			
			return resp;
		} else {
			contRep.sp_insertClienteConta(cliente.getNome(), cliente.getCpf(), cliente.getSenha(), tipo);
			return new ModelAndView("redirect:login");
		}
		
	}
	
	@GetMapping("/paginaInicial")
	public ModelAndView telaInicial(HttpSession session) {
		
		String cpfCliente = (String) session.getAttribute("cpfCliente");
		String tipo = contRep.sp_verificarContaCorrenteOuPoupanca(cpfCliente);
		Optional<Cliente> cliOpt = clRep.findById(cpfCliente);
		Cliente cli = cliOpt.get();
		if(tipo.equals("corrente")) {
			ContaCorrente cont = new ContaCorrente();
		}
		return null;
	}
}
