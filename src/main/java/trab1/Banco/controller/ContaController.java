package trab1.Banco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import trab1.Banco.model.Cliente;
import trab1.Banco.model.Conta;
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
			String msg = clRep.sp_verificaSenhaCliente(cliente.getSenha());
			ModelAndView resp = new ModelAndView("redirect:registrar?error=true&mensagem=" + msg);
			
			return resp;
		} else {
			contRep.sp_insertClienteConta(cliente.getNome(), cliente.getCpf(), cliente.getSenha(), tipo);
			return new ModelAndView("redirect:login");
		}
		
	}
	
	@GetMapping("/paginaInicial")
	public ModelAndView telaInicial(HttpSession session) {
		ModelAndView mv = new ModelAndView("/contaInfo_form");
		
		String cpfCliente = (String) session.getAttribute("cpfCliente");
		String tipo = contRep.sp_verificarContaCorrenteOuPoupanca(cpfCliente);
		Cliente cli = clRep.findById(cpfCliente).get();
		
		String id = contRep.pegarCodigo(cpfCliente);
		Conta corr = contRep.findById(id).get();
		
		mv.addObject("conta", corr);
		mv.addObject("tipo", tipo);
		mv.addObject("cliente", cli);
		
		
		return mv;
	}
	
	@PostMapping("/paginaInicial")
	public ModelAndView postTelaInicial(HttpSession session, @RequestParam("acao") String acao) {
		
		String cpfCliente = (String) session.getAttribute("cpfCliente");
		
		if ("senha".equals(acao)) {
			return new ModelAndView("redirect:alteraSenha");
		} else if ("adicionar".equals(acao)) {
			if (clRep.sp_validarContaParaConj(contRep.pegarCodigo(cpfCliente))) {
				return new ModelAndView("redirect:contaConj");
			} else {
				return new ModelAndView("redirect:paginaInicial?error");
			}
		}
		return new ModelAndView("redirect:paginaInicial");
	}
}
	


