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
import trab1.Banco.servico.IClienteRepository;

@Controller
public class ClienteController {

	@Autowired
	IClienteRepository clRep;

	@GetMapping("/login")
	public ModelAndView telaLogin(HttpSession session) {
		ModelAndView log = new ModelAndView("/telaInicial_form");
		log.addObject("cliente", new Cliente());
		return log;
	}

	@PostMapping("/login")
	public ModelAndView trataLogin(HttpSession session, @ModelAttribute("cliente") Cliente cliente,
			@RequestParam("acao") String acao) {
		if (acao.equals("login")) {

			if (clRep.sp_verificaLogin(cliente.getCpf(), cliente.getSenha())) {
				session.setAttribute("cpfCliente", cliente.getCpf());
				return new ModelAndView("redirect:paginaInicial");
			} else {
				return new ModelAndView("redirect:login?error");
			}
		} else if (acao.equals("registrar")) {
			return new ModelAndView("redirect:registrar");
		}

		return new ModelAndView("redirect:login");

	}

	@GetMapping("/alteraSenha")
	public ModelAndView alteraSenha(HttpSession session) {
		ModelAndView log = new ModelAndView("/alteraSenha_form");
		log.addObject("cliente", new Cliente());
		return log;
	}

	@PostMapping("/alteraSenha")
	public ModelAndView postSenha(HttpSession session, @ModelAttribute("cliente") Cliente cliente) {
		String clienteCPF = (String) session.getAttribute("cpfCliente");
		if (clRep.sp_verificaSenhaCliente(cliente.getSenha()) != null) {
			String msg = clRep.sp_verificaSenhaCliente(cliente.getSenha());
			ModelAndView resp = new ModelAndView("redirect:alteraSenha?error=true&mensagem=" + msg);

			return resp;
		} else {
			clRep.sp_updateClienteSenha(clienteCPF, cliente.getSenha());
			return new ModelAndView("redirect:paginaInicial");
		}
	}
}
