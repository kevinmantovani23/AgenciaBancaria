package trab1.Banco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import trab1.Banco.model.Cliente;
import trab1.Banco.servico.IClienteRepository;
import trab1.Banco.servico.IContaRepository;

@Controller
public class ContaConjuntaController {

	@Autowired
	IClienteRepository clRep;
	
	@Autowired 
	IContaRepository contRep;
	
	@GetMapping("/contaConj")
	public ModelAndView adicionaConjunta(HttpSession session) {
		ModelAndView add = new ModelAndView("/adicionaConj_form");
		add.addObject("cliente", new Cliente());
		return add;
	}

	@PostMapping("/contaConj")
	public ModelAndView postAdiciona(HttpSession session, @ModelAttribute("cliente") Cliente cliente) {
		String clienteOrig = (String) session.getAttribute("cpfCliente");
		if (clRep.sp_verificaCPF(cliente.getCpf()) != null) {
			String msg = clRep.sp_verificaCPF(cliente.getCpf());
			ModelAndView resp = new ModelAndView("redirect:contaConj?error=true&mensagem=" + msg);
			
			return resp;
		
		} else if (clRep.sp_verificaSenhaCliente(cliente.getSenha()) != null){
			String msg = clRep.sp_verificaSenhaCliente(cliente.getSenha());
			ModelAndView resp = new ModelAndView("redirect:contaConjr?error=true&mensagem=" + msg);
			
			return resp;
		} else {
			
			clRep.sp_insertCliente(cliente.getCpf(), cliente.getNome(), cliente.getSenha());
			contRep.sp_inserirClienteContaConj(contRep.pegarCodigo(clienteOrig), cliente.getCpf());
			return new ModelAndView("redirect:paginaInicial");
		}
		
	}
}
