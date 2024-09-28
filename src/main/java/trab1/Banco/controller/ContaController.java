package trab1.Banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import trab1.Banco.model.Cliente;
import trab1.Banco.model.Conta;
import trab1.Banco.servico.IClienteRepository;

@Controller
public class ContaController {
	
	@Autowired
	IClienteRepository clRep;
	
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
		}
		return null;
	}
}
