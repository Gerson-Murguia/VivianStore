package com.example.vivian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/index")
	public String getIndexView() {
		return "index_admin";
	}
	
	@GetMapping("/marca")
	public String getMarcaView() {
		return "marca";
	}
	
	@GetMapping("/categoria")
	public String getCategoriaView() {
		return "categoria";
	}
	
	@GetMapping("/producto")
	public String getProductoView() {
		return "producto";
	}
}
