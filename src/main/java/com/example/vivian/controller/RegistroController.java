package com.example.vivian.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//Controller de todas las vistas html
@Controller
@RequestMapping("/")
public class RegistroController {
	

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		return "registro";
	}
	
	@GetMapping("/index")
	public String index(Model model) {
		//TODO: cambiar index segun sea ADMIN o CLIENTE
		//nombre de la vista html
		//por ahora index es el index de administrador
		return "index";
	}
	
	
	
}
