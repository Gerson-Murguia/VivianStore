package com.example.vivian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vivian")
public class TiendaController {
	
	@GetMapping("/")
	public String index(Model model) {
		return "tienda";
	}
	
	@GetMapping("/producto")
	public String producto(Model model) {
		return "producto";
	}
	
	@GetMapping("/carrito")
	public String carrito(Model model) {
		return "carrito";
	}
	
	@GetMapping("/compras")
	public String compras(Model model) {
		return "compras";
	}
}
