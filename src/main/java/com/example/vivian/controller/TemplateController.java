package com.example.vivian.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//Controller de todas las vistas html
@Controller
@RequestMapping("/")
public class TemplateController {
	

	@GetMapping("/login")
	public String getLoginView() {
		
		//nombre de la vista html
		return "login";
	}
	
	@GetMapping("/registro")
	public String getRegistroView() {
		
		//nombre de la vista html
		return "registroUsuario";
	}
	
	@GetMapping("/index")
	public String getIndexView() {
		//TODO: cambiar index segun sea ADMIN o CLIENTE
		//nombre de la vista html
		//por ahora index te redirige a index admin
		return "index";
	}
	
	
	
}
