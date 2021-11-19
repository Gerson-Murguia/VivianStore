package com.example.vivian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/dashboard")
	public String index(Model model) {
		return "admin";
	}	
	
	@GetMapping("/marca")
	public String marca(Model model) {
		return "marca";
	}
	
	@GetMapping("/categoria")
	public String categoria(Model model) {
		return "categoria";
	}
	
	@GetMapping("/producto")
	public String producto(Model model) {
		return "producto";
	}
	
	@GetMapping("/usuario")
	public String usuario(Model model) {
		return "usuario";
	}
}
