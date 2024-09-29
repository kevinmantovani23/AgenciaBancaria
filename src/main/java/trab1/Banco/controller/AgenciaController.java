package trab1.Banco.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import trab1.Banco.model.Agencia;
import trab1.Banco.servico.IAgenciaRepository;

@Controller
public class AgenciaController {

	@Autowired
	IAgenciaRepository agRep;

	private List<Agencia> lista = new ArrayList<>();

	@GetMapping("/agencias")
	public ModelAndView agencias() {
		ModelAndView mv = new ModelAndView("agencia_form");
		mv.addObject("lista", lista);
		mv.addObject("agencia", new Agencia());
		return mv;
	}

	@GetMapping("/agencias/editar/{id}")
	public ModelAndView agenciaEdit(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("redirect:agencias?erro");
		String strId = "";

		strId = id;
		Optional<Agencia> cOpt = agRep.findById(strId);
		if (cOpt.isPresent()) {
			Agencia a = cOpt.get();
			mv = new ModelAndView("agencia_form");
			mv.addObject("lista", lista);
			mv.addObject("agencia", a);

		}

		return mv;
	}

	@GetMapping("/agencias/delete/{id}")
	public ModelAndView agenciaDelete(@PathVariable("id") String id) {

		String strId = "";

		strId = id;
		agRep.deleteById(strId);
		lista.clear();
		lista.addAll(agRep.findByCodigo(""));

		ModelAndView mv = new ModelAndView("agencia_form");
		mv.addObject("lista", lista);
		mv.addObject("agencia", new Agencia());
		return mv;
	}

	@PostMapping("/agencias")
	public ModelAndView agenciaCreate(@ModelAttribute("agencia") Agencia agencia, @RequestParam("acao") String acao) {
		if ("gravar".equals(acao)) {
			agRep.save(agencia);
		} else if ("pesquisar".equals(acao)) {
			lista.clear();
			lista.addAll(agRep.findByCodigo(agencia.getCodigo()));
		}
		ModelAndView mv = new ModelAndView("agencia_form");
		mv.addObject("lista", lista);
		mv.addObject("agencia", new Agencia());
		return mv;
	}

}
