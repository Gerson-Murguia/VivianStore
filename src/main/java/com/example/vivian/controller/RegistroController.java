package com.example.vivian.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//Controller de todas las vistas html
@Controller
public class RegistroController {
	

	@GetMapping(path = {"/login","/"})
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		return "registro";
	}
	
}
