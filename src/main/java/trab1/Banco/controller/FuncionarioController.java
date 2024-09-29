package trab1.Banco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FuncionarioController {

	@GetMapping("/funcionario")
	public ModelAndView telaFuncionario() {
		ModelAndView mv = new ModelAndView("telaFuncionario_form");
		return mv;
	}
}
