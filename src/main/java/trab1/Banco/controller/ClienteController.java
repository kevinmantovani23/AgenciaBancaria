package trab1.Banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import trab1.Banco.model.Cliente;
import trab1.Banco.servico.IClienteRepository;

@Controller
public class ClienteController {

	@Autowired
	IClienteRepository clRep;
	
	@GetMapping("/login")
	public ModelAndView telaLogin() {
		ModelAndView log = new ModelAndView("/telaInicial_form");
		log.addObject("cliente", new Cliente());
		return log;
	}
	
	@PostMapping("/login")
	public ModelAndView trataLogin(@ModelAttribute("cliente") Cliente cliente, @RequestParam("acao") String acao) {
		if (acao.equals("login")) {
			
		if(clRep.sp_verificaLogin(cliente.getCpf(), cliente.getSenha())) {
				
			} else {
				return new ModelAndView("redirect:login?error");
			}
		} else if (acao.equals("registrar")) {
			return new ModelAndView("redirect:registrar");
		}
		
		return new ModelAndView("redirect:login");
		
		
	}
}
